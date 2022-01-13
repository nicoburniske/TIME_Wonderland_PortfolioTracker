package nicoburniske.web3

import java.time.{Instant, LocalDateTime, LocalTime, ZoneId}
import java.util.concurrent.TimeUnit

import com.typesafe.scalalogging.Logger
import monix.eval.Task
import monix.execution.Scheduler
import monix.execution.schedulers.SchedulerService
import org.rogach.scallop.ScallopConf
import sttp.client3.asynchttpclient.monix.AsyncHttpClientMonixBackend

import scala.concurrent.duration.FiniteDuration

object Main {
  val logger               = Logger("web3-service")
  val CSVPATH              = "log.csv"
  implicit val scheduler   = Scheduler.forkJoin(
    name = "web3-logger",
    parallelism = 10,
    maxThreads = 10,
    daemonic = false
  )
  implicit val backendTask = AsyncHttpClientMonixBackend()

  // Command Line arg configuration.
  case class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {
    val walletAddress = opt[String](required = true, name = "walletAddress", descr = "Wallet address on AVAX C-Chain")
    val csvPath       = opt[String](
      default = Some(CSVPATH),
      name = "csvPath",
      descr = s"Path to csv file for logs. Default is $CSVPATH in working directory"
    )
    val runAtStart    = opt[Boolean](name = "runAtStart", descr = "Will execute first log immediately")
    verify()
  }

  def main(args: Array[String]): Unit = {
    val input = Conf(args)
    startLogScheduler(input.walletAddress(), input.csvPath(), input.runAtStart())
  }

  def scheduleWhaleTracker(): Unit = {
    backendTask.flatMap { b => PriceService.priceTIME().send(b) }
  }

  def startLogScheduler(walletAddress: String, csvPath: String, runAtStart: Boolean): Unit = {
    scheduleRebaseLogEvent(walletAddress, csvPath, scheduler)
    // Bug in scheduler? non-zero initial delay will cause early exit.
    if (runAtStart)
      scheduler.scheduleOnce(0, TimeUnit.SECONDS, executeLogging(walletAddress, csvPath))
    else
      scheduler.scheduleOnce(0, TimeUnit.SECONDS, () => ())
  }

  def scheduleRebaseLogEvent(walletAddress: String, csvPath: String, scheduler: SchedulerService)(
      implicit ec: Scheduler): Unit = {
    val eightHours = FiniteDuration(8, TimeUnit.HOURS)

    val rebaseTime = findTimeUntilNextRebase()
    val hours      = BigDecimal(rebaseTime.toMinutes / 60.0).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
    logger.info(s"Beginning Scheduler configuration. $hours hours until next rebase log")
    scheduler.scheduleAtFixedRate(
      rebaseTime.toSeconds,
      eightHours.toSeconds,
      TimeUnit.SECONDS,
      executeLogging(walletAddress, csvPath)
    )
    logger.info("Scheduler was configured properly")
  }

  def executeLogging(walletAddress: String, csvPath: String)(implicit ec: Scheduler): Runnable = () => {
    val timeBalanceTask = Web3.getWalletTimeBalance(walletAddress)
    val composed        =
      for {
        backend            <- backendTask
        responses    <- Task.parZip2(backend.send(PriceService.getPrices()), timeBalanceTask)
        priceResponse = responses._1.body
        timeBalance   = responses._2
      } yield priceResponse match {
        case Left(error)   => logger.error(s"Failed to retrieve prices: $error")
        case Right(prices) =>
          logger.info("Log process starting")
          CsvLogger.addLog(csvPath, timeBalance, prices(PriceService.WONDERLAND), prices(PriceService.ETHEREUM))
          logger.info(s"Log successfully written to $csvPath")
      }


    composed.runAsyncAndForget
  }

  val UTC          = ZoneId.of("UTC")
  val REBASE_TIMES = Seq(6, 14, 22).map(LocalTime.of(_, 0))

  /**
   * Finds first rebase time. Rebases happen @ 06:00, 14:00, 22:00 UTC.
   *
   * @return
   *   duration until next rebase time + 1 min for contract to execute.
   */
  def findTimeUntilNextRebase(): FiniteDuration = {
    def findFirstRebaseTime(): Long = {
      val now           = LocalDateTime.now(UTC)
      val nowDate       = now.toLocalDate
      val todayTimes    = REBASE_TIMES.map { time => LocalDateTime.of(nowDate, time) }
      val tomorrowTimes = REBASE_TIMES.map { time => LocalDateTime.of(nowDate.plusDays(1), time) }
      (todayTimes ++ tomorrowTimes).find(dateTime => dateTime.isAfter(now)).get.atZone(UTC).toInstant.toEpochMilli
    }

    val rebaseTime = findFirstRebaseTime()
    val now        = Instant.now().toEpochMilli
    val oneMin     = FiniteDuration(1, TimeUnit.MINUTES).toMillis
    FiniteDuration(rebaseTime + oneMin - now, TimeUnit.MILLISECONDS)
  }
}
