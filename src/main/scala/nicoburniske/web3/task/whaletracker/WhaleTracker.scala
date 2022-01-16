package nicoburniske.web3.task.whaletracker

import java.time.Instant

import caliban.client.CalibanClientError
import monix.eval.Task
import nicoburniske.web3.Resources.{backend, scheduler}
import nicoburniske.web3.exchange.DEX.{Endpoints, PairAddress, Queries}
import nicoburniske.web3.utils.BetterLogger
import sttp.client3.Request

import scala.concurrent.duration.{FiniteDuration, _}

object WhaleTracker extends BetterLogger {
  val MIN_SWAP = BigInt(100000)
  val INTERVAL = 1.minutes

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
      _ <- Task.now(logger.info("hunting for whales"))
      since = Instant.now.minusMillis(interval.toMillis)
      queryTj = timeMimSwapsRequest(since)
      querySushi = wMemoSwapsRequest(since)
      responses <- Task.parZip2(queryTj.send(backend), querySushi.send(backend))
      (timeReq, wmemoReq) = responses
      (timeSwaps, wmemoSwaps) = (timeReq.body, wmemoReq.body)
      _ <- processWhaleSwaps(bot, timeSwaps, wmemoSwaps)
    } yield ()
  }

  private def timeMimSwapsRequest(since: Instant): Request[Either[CalibanClientError, Seq[SwapDetails]], Any] = {
    Queries
      .pairSwapsSinceInstant(PairAddress.TJ_TIME_MIM, since, MIN_SWAP)(SwapDetails.DETAILS_MAPPED)
      .toRequest(Endpoints.TRADER_JOE)
  }

  private def wMemoSwapsRequest(since: Instant): Request[Either[CalibanClientError, Seq[SwapDetails]], Any] = {
    Queries
      .pairSwapsSinceInstant(PairAddress.SUSHI_WMEMO_MIM, since, MIN_SWAP)(SwapDetails.DETAILS_MAPPED)
      .toRequest(Endpoints.SUSHISWAP)
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
          val messages = swaps.map(_.message)
          for {
            _ <- Task.now(logger.info(s"Found ${swaps.size} whale swap(s)"))
            _ <- Task.parTraverse(messages)(m => bot.sendMessage(m)).void
          } yield ()
        }
      case (t, w) =>
        Task.now(logFailures("Failed to retrieve swap information")(t, w))
    }
  }
}
