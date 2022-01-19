package nicoburniske.web3.task.whaletracker

import java.time.Instant

import caliban.client.CalibanClientError
import monix.eval.Task
import monix.reactive.Observable
import nicoburniske.web3.Resources.{backend, scheduler}
import nicoburniske.web3.exchange.{DEX, SwapDetails}
import nicoburniske.web3.utils.BetterLogger

import scala.concurrent.duration.{FiniteDuration, _}

object WhaleTracker extends BetterLogger {
  val MIN_SWAP = BigInt(300000)
  val INTERVAL = 1.minutes

  def schedule(token: String): Task[Unit] = {
    val bot = WhaleTrackerBot(token)
    for {
      _ <- logTask("Scheduling whale tracker")
      observable = Observable.intervalAtFixedRate(0.seconds, INTERVAL)
      _ <- Task.parZip2(bot.run(), observable.foreachL(_ => whaleTracker(INTERVAL, bot).runAsyncAndForget))
    } yield ()
  }

  def whaleTracker(interval: FiniteDuration, bot: WhaleTrackerBot): Task[Unit] = {
    for {
      _ <- logTask("hunting for whales")
      since = Instant.now.minusMillis(interval.toMillis)
      queryTj = DEX.timeMimSwapsRequest(since, MIN_SWAP)
      querySushi = DEX.wMemoSwapsRequest(since, MIN_SWAP)
      responses <- Task.parZip2(queryTj.send(backend), querySushi.send(backend))
      (timeReq, wmemoReq) = responses
      (timeSwaps, wmemoSwaps) = (timeReq.body, wmemoReq.body)
      _ <- processWhaleSwaps(bot, timeSwaps, wmemoSwaps)
    } yield ()
  }

  def processWhaleSwaps(
                         bot: WhaleTrackerBot,
                         maybeTimeSwaps: Either[CalibanClientError, Seq[SwapDetails]],
                         maybeWmemoSwaps: Either[CalibanClientError, Seq[SwapDetails]]): Task[Unit] = {
    (maybeTimeSwaps, maybeWmemoSwaps) match {
      case (Right(timeSwaps), Right(wmemoSwaps)) =>
        val swaps = timeSwaps ++ wmemoSwaps
        if (swaps.isEmpty) {
          logTask("no whales found")
        } else {
          val messages = swaps.map(_.message)
          for {
            _ <- logTask(s"Found ${swaps.size} whale swap(s)")
            _ <- Task.parTraverse(messages)(m => bot.sendMessage(m)).void
          } yield ()
        }
      case (t, w) =>
        Task.now(logFailures("Failed to retrieve swap information")(t, w))
    }
  }
}
