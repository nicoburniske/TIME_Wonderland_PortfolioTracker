package nicoburniske.web3

import java.time.{Instant, LocalDateTime, LocalTime, ZoneId}
import java.util.concurrent.TimeUnit

import com.typesafe.scalalogging.Logger
import monix.execution.Scheduler
import org.rogach.scallop.ScallopConf

import scala.concurrent.duration.FiniteDuration


object Main {
  val logger = Logger("web3-porfolio")
  val csvPathDefault = "log.csv"

  // Command Line arg configuration.
  case class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {
    val walletAddress = opt[String](required = true, name = "walletAddress", descr = "Wallet address on AVAX C-Chain")
    val csvPath = opt[String](default = Some(csvPathDefault), name = "csvPath", descr = s"Path to csv file for logs. Default is $csvPathDefault in working directory")
    val runAtStart = opt[Boolean](name = "runAtStart", descr = "Will execute first log immediately")
    verify()
  }

  def main(args: Array[String]): Unit = {
    val input = Conf(args)
    startLogScheduler(input.walletAddress(), input.csvPath(), input.runAtStart())
  }

  def startLogScheduler(walletAddress: String, csvPath: String, runAtStart: Boolean): Unit = {
    implicit val scheduler = Scheduler.forkJoin(
      name = "web3-logger",
      parallelism = 1,
      maxThreads = 1,
      daemonic = false
    )

    val timeTillFirstExecution = findTimeUntilFirstRebase()
    val hours = BigDecimal(timeTillFirstExecution.toMinutes / 60.0).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
    val eightHours = FiniteDuration(8, TimeUnit.HOURS)
    logger.info(s"Beginning Scheduler configuration. $hours hours until next rebase log")
    scheduler.scheduleWithFixedDelay(timeTillFirstExecution.toSeconds, eightHours.toSeconds, TimeUnit.SECONDS, executeLogging(walletAddress, csvPath))
    logger.info("Scheduler was configured properly")

    // Bug in scheduler? non-zero initial delay will cause early exit.
    if (runAtStart)
      scheduler.scheduleOnce(0, TimeUnit.SECONDS, executeLogging(walletAddress, csvPath))
    else
      scheduler.scheduleOnce(0, TimeUnit.SECONDS, () => ())
  }

  def executeLogging(walletAddress: String, csvPath: String): Runnable = () => {
    logger.info("Log process starting")
    PriceService.getPrices().foreach { prices =>
      val timeBalance = Web3.getWalletTimeBalance(walletAddress)
      CsvLogger.addLog(csvPath, timeBalance, prices(PriceService.WONDERLAND), prices(PriceService.ETHEREUM))
      logger.info(s"Log successfully written to $csvPath")
    }
  }

  val UTC = ZoneId.of("UTC")
  val REBASE_TIMES = Seq(6, 14, 22).map(LocalTime.of(_, 0))

  /**
   * Finds first rebase time. Rebases happen @ 06:00, 14:00, 22:00 UTC.
   *
   * @return duration until next rebase time + 1 min for contract to execute.
   */
  def findTimeUntilFirstRebase(): FiniteDuration = {
    def findFirstRebaseTime(): Long = {
      val now = LocalDateTime.now(UTC)
      val nowDate = now.toLocalDate
      val todayTimes = REBASE_TIMES.map { time =>
        LocalDateTime.of(nowDate, time)
      }
      val tomorrowTimes = REBASE_TIMES.map { time =>
        LocalDateTime.of(nowDate.plusDays(1), time)
      }
      (todayTimes ++ tomorrowTimes)
        .find(dateTime => dateTime.isAfter(now))
        .get.atZone(UTC).toInstant.toEpochMilli
    }

    val rebaseTime = findFirstRebaseTime()
    val now = Instant.now().toEpochMilli
    val oneMin = FiniteDuration(1, TimeUnit.MINUTES).toMillis
    FiniteDuration(rebaseTime + oneMin - now, TimeUnit.MILLISECONDS)
  }
}

