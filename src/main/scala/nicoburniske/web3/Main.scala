package nicoburniske.web3

import java.time.{Instant, LocalDateTime, LocalTime, ZoneId}
import java.util.concurrent.TimeUnit

import com.typesafe.scalalogging.Logger
import monix.execution.Scheduler
import org.rogach.scallop.ScallopConf

import scala.concurrent.duration.FiniteDuration


object Main {
  val csvPathDefault = "log.csv"
  val logger = Logger("web3-portfolio")
  // UTC Time.
  val rebaseTimes = Seq(6, 14, 22).map(LocalTime.of(_, 0))
  val UTC = ZoneId.of("UTC")


  // Command Line arg configuration.
  case class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {
    val walletAddress = opt[String](required = true, name = "walletAddress", descr = "Wallet address on AVAX C-Chain")
    val csvPath = opt[String](default = Some(csvPathDefault), descr = "Path to csv file for logs")
    val noRunAtStart = opt[Boolean](name = "noRunAtStart", descr = "Flag to only run after rebases")
    verify()
  }

  def main(args: Array[String]): Unit = {
    val input = Conf(args)
    startLogScheduler(input.walletAddress(), input.csvPath(), input.noRunAtStart())
  }

  def startLogScheduler(walletAddress: String, csvPath: String, noRunAtStart: Boolean): Unit = {
    implicit val scheduler = Scheduler.forkJoin(
      name = "web3-logger",
      parallelism = 1,
      maxThreads = 1,
      daemonic = false
    )

    // Bug in scheduler? non-zero initial delay will cause early exit.
    if (noRunAtStart)
      scheduler.scheduleOnce(0, TimeUnit.SECONDS, () => ())
    else
      scheduler.scheduleOnce(0, TimeUnit.SECONDS, executeLogging(walletAddress, csvPath))

    val timeTillFirstExecution = findTimeUntilFirstRebase().toMillis
    logger.info(s"Beginning Scheduler configuration. Time until next log: $timeTillFirstExecution")
    val eightHours = FiniteDuration(8, TimeUnit.HOURS).toMillis
    scheduler.scheduleWithFixedDelay(timeTillFirstExecution, eightHours, TimeUnit.MILLISECONDS, executeLogging(walletAddress, csvPath))
    logger.info("Scheduler was configured properly")
  }

  def executeLogging(walletAddress: String, csvPath: String): Runnable = () => {
    logger.info("Log process starting")
    PriceService.getPrices().foreach { prices =>
      val timeBalance = Web3.getWalletTimeBalance(walletAddress)
      CsvLogger.addLog(csvPath, timeBalance, prices(PriceService.WONDERLAND), prices(PriceService.ETHEREUM))
      logger.info(s"Log successfully written to $csvPath")
    }
  }

  /**
   * Finds first rebase time. Rebases happen @ 01:00, 09:00, 17:00 EST.
   *
   * @return duration until next rebase time + 1 min for contract to execute.
   */
  def findTimeUntilFirstRebase(): FiniteDuration = {
    def findFirstRebaseTime(): Long = {
      val now = LocalDateTime.now(UTC)
      val nowDate = now.toLocalDate
      val todayTimes = rebaseTimes.map { time =>
        LocalDateTime.of(nowDate, time)
      }
      val tomorrowTimes = rebaseTimes.map { time =>
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

