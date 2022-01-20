package nicoburniske.web3.task.whaletracker

import java.time.Instant

import monix.eval.Task
import monix.reactive.Observable
import nicoburniske.web3.Resources.{backend, scheduler}
import nicoburniske.web3.exchange.{DEX, SwapDetails}
import nicoburniske.web3.utils.BetterLogger

import scala.concurrent.duration.{FiniteDuration, _}

object WhaleTracker extends BetterLogger {
  val MIN_SWAP = BigInt(300000)
  val INTERVAL = 1.minutes

  /**
   * Schedules WhaleTracker Bot to receive incoming messages + searches for Whale transactions every minute, sending alert via
   * Telegram for each transaction found.
   *
   * NOTE: Tasks do not terminate, but they can be cancelled.
   *
   * @param token
   *   auth token for Telegram Bot
   * @return
   *   Task
   */
  def schedule(token: String): Task[Unit] = {
    val bot = WhaleTrackerBot(token)
    for {
      _         <- infoTask("Scheduling whale tracker")
      observable = Observable.intervalAtFixedRate(0.seconds, INTERVAL)
      _         <- Task.parZip2(bot.run(), observable.foreachL(_ => whaleTracker(INTERVAL, bot).runAsyncAndForget))
    } yield ()
  }

  def whaleTracker(interval: FiniteDuration, bot: WhaleTrackerBot): Task[Unit] = {
    for {
      _         <- infoTask("hunting for whales")
      swaps     <- findSwaps(interval)
      logMessage = if (swaps.isEmpty) "no whales found" else s"Found ${swaps.size} whale swap(s)"
      _         <- infoTask(logMessage)
      messages   = swaps.map(_.message)
      _         <- Task.parTraverse(messages)(m => bot.sendMessage(m))
    } yield ()
  }

  /**
   * Finds all swaps over MIN_SWAP that occurred between [Now - interval, Now] for TIME-MIM wMEMO-MIM.
   */
  def findSwaps(interval: FiniteDuration): Task[Seq[SwapDetails]] = {
    for {
      since     <- Task.eval(Instant.now.minusMillis(interval.toMillis))
      queryTj    = DEX.timeMimSwapsRequest(since, MIN_SWAP)
      querySushi = DEX.wMemoSwapsRequest(since, MIN_SWAP)
      responses <- Task.parZip2(queryTj.send(backend), querySushi.send(backend))

      (timeReq, wmemoReq) = responses

      res <- (timeReq.body, wmemoReq.body) match {
               case (Right(timeSwaps), Right(wmemoSwaps)) => Task.now(timeSwaps ++ wmemoSwaps)
               case (Left(error), _)                      => Task.raiseError(error)
               case (_, Left(error))                      => Task.raiseError(error)
             }
    } yield res
  }
}
