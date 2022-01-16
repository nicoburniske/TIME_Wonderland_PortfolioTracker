package nicoburniske.web3.task.whaletracker

import java.time.format.DateTimeFormatter
import java.time.{Instant, ZoneId, ZonedDateTime}

import caliban.client.CalibanClientError
import generated.sushi.exchange.{Pair, Swap, Token}
import monix.eval.Task
import nicoburniske.web3.Resources.{backendTask, scheduler}
import nicoburniske.web3.exchange.DEX.{Endpoints, PairAddress, Queries}
import nicoburniske.web3.utils.BetterLogger
import sttp.client3.Request

import scala.concurrent.duration.{FiniteDuration, _}
import scala.math.BigDecimal.RoundingMode

case class SwapDetails(
                        pair: String,
                        price0: BigDecimal,
                        price1: BigDecimal,
                        fullName0: String,
                        fullName1: String,
                        id: String,
                        timestamp: BigInt,
                        amountUSD: BigDecimal,
                        token0Sold: BigDecimal,
                        token0Received: BigDecimal,
                        token1Sold: BigDecimal,
                        token1Received: BigDecimal) {

  def round(d: BigDecimal, scale: Int = 2): BigDecimal = {
    d.setScale(scale, RoundingMode.HALF_UP)
  }

  val (token0, token1) = {
    val split = pair.split("-")
    (split(0), split(1))
  }

  // TODO: review. not true always
  val realPrice = if (price0 > price1) price0 else price1
  // TODO: why is there a dash? Deserialization perhaps?
  val snowtraceLink = s"https://snowtrace.io/tx/${id.split("-").head}"
  val timeFormatted = {
    val instant = Instant.ofEpochMilli(timestamp.toLong.seconds.toMillis)
    val dateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"))
    DateTimeFormatter.ISO_ZONED_DATE_TIME.format(dateTime)
  }
  val swapDetails = {
    if (token0Sold.compare(0) == 0) {
      s"${round(token1Sold)} $token1 swapped for ${round(token0Received)} $token0"
    } else {
      s"${round(token0Sold)} $token0 swapped for ${round(token1Received)} $token1"
    }
  }

  val asMessage =
    s"""
       | $pair Whale Alert 🚨
       |- Transaction Amount: ${round(amountUSD)}
       |- Timestamp: $timeFormatted
       |- Price: ${round(realPrice)}
       |- $swapDetails
       |- $snowtraceLink
       |""".stripMargin.strip

}

object WhaleTracker extends BetterLogger {
  val MIN_SWAP = BigInt(100000)
  val INTERVAL = 1.minutes
  val SWAP_DETAILS =
    Swap.pair(Pair.name) ~
      Swap.pair(Pair.token0Price) ~
      Swap.pair(Pair.token1Price) ~
      Swap.pair(Pair.token0(Token.name)) ~
      Swap.pair(Pair.token1(Token.name)) ~
      Swap.id ~
      Swap.timestamp ~
      Swap.amountUSD ~
      Swap.amount0In ~
      Swap.amount0Out ~
      Swap.amount1In ~
      Swap.amount1Out
  val DETAILS_MAPPED = SWAP_DETAILS.mapN(SwapDetails)

  def schedule(token: String): Task[Unit] = {
    Task.eval {
      val bot = WhaleTrackerBot(token)
      bot.run().runAsyncAndForget
      logger.info("Scheduling whale tracker")
      scheduler.scheduleAtFixedRate(0.seconds, INTERVAL)(whaleTracker(INTERVAL, bot).runAsyncAndForget)
      logger.info("Successfully scheduled whale tracker")
    }
  }

  def whaleTracker(interval: FiniteDuration, bot: WhaleTrackerBot): Task[Unit] = {
    for {
      backend <- backendTask
      _ <- Task.now(logger.info("hunting for whales"))
      since = Instant.now.minusMillis(interval.toMillis)
      queryTj = timeMimSwapsRequest(since)
      querySushi = wMemoSwapsRequest(since)
      responses <- Task.parZip2(queryTj.send(backend), querySushi.send(backend))
      (timeReq, wmemoReq) = responses
      (timeSwaps, wmemoSwaps) = (timeReq.body, wmemoReq.body)
      result <- processWhaleSwaps(bot, timeSwaps, wmemoSwaps)
    } yield result
  }

  private def timeMimSwapsRequest(since: Instant): Request[Either[CalibanClientError, Seq[SwapDetails]], Any] = {
    Queries.pairSwapsSinceInstant(PairAddress.TJ_TIME_MIM, since, MIN_SWAP)(DETAILS_MAPPED).toRequest(Endpoints.TRADER_JOE)
  }

  private def wMemoSwapsRequest(since: Instant): Request[Either[CalibanClientError, Seq[SwapDetails]], Any] = {
    Queries.pairSwapsSinceInstant(PairAddress.SUSHI_WMEMO_MIM, since, MIN_SWAP)(DETAILS_MAPPED).toRequest(Endpoints.SUSHISWAP)
  }

  def processWhaleSwaps(
                         bot: WhaleTrackerBot,
                         maybeTimeSwaps: Either[CalibanClientError, Seq[SwapDetails]],
                         maybeWmemoSwaps: Either[CalibanClientError, Seq[SwapDetails]]): Task[Unit] = {
    (maybeTimeSwaps, maybeWmemoSwaps) match {
      case (Right(timeSwaps), Right(wmemoSwaps)) =>
        val swaps = timeSwaps ++ wmemoSwaps
        if (swaps.isEmpty) {
          Task.now(logger.info("no whales found"))
        } else {
          val messages = swaps.map(_.asMessage)
          for {
            _ <- Task.now(logger.info(s"Found ${swaps.size} whale swap(s)"))
            result <- Task.parTraverse(messages)(m => bot.sendMessage(m)).void
          } yield result
        }
      case (t, w) =>
        Task.now(logFailures("Failed to retrieve swap information")(t, w))
    }
  }
}
