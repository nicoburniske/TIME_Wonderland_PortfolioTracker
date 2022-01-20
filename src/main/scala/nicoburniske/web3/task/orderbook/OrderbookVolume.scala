package nicoburniske.web3.task.orderbook

import java.time.Instant

import monix.eval.Task
import nicoburniske.web3.Resources.{backend, scheduler}
import nicoburniske.web3.exchange.{DEX, SwapDetails}
import nicoburniske.web3.utils.BetterLogger

import scala.concurrent.duration.{FiniteDuration, _}

// TODO: convert pagination to websocket?
object OrderbookVolume extends BetterLogger {

  def findVolumeWMEMO(duration: FiniteDuration): Task[(BigDecimal, BigDecimal)] = {
    for {
      since    <- Task.eval(Instant.now().minusMillis(duration.toMillis))
      allSwaps <- wMemoSwapsPagination(since)

      _ <- infoTask(s"All sorted? ${
             val sorted = allSwaps.sortWith(_.timestamp > _.timestamp)
             sorted == allSwaps
           } ")
    } yield calculateVolume(allSwaps)
  }

  def wMemoSwapsPagination(since: Instant, swapsAcc: Vector[SwapDetails] = Vector.empty): Task[Seq[SwapDetails]] = {
    val lastTimestamp = swapsAcc.lastOption.map(_.timestamp)
    val lastId        = swapsAcc.lastOption.map(_.id)
    for {
      response  <- DEX.wMemoSwapsRequest(since, 0, lastTimestamp, lastId).send(backend)
      nextSwaps <- response.body match {
                     case Left(value)  => Task.raiseError(value)
                     case Right(swaps) => Task.now(swaps)
                   }
      _         <- infoTask(s"Found ${nextSwaps.size} new swaps")
      allSwaps  <- {
        if (nextSwaps.isEmpty)
          Task.now(swapsAcc)
        else {
          wMemoSwapsPagination(since, swapsAcc ++ nextSwaps)
        }
      }
    } yield allSwaps
  }

  /**
   * Calculates Sell and Buy volume for the given swaps.
   *
   * @param swaps
   *   the swaps
   * @return
   *   (Sells, Buys)
   */
  def calculateVolume(swaps: Seq[SwapDetails]): (BigDecimal, BigDecimal) = {
    val grouped = swaps.groupMapReduce(_.token0Received > 0)(_.amountUSD)(_ + _)
    (grouped(false), grouped(true))
  }

  def main(args: Array[String]): Unit = {
    findVolumeWMEMO(1.hour).map { case (sells, buys) => (SwapDetails.round(sells), SwapDetails.round(buys)) }.foreach {
      case (sells, buys) => println(s"Sells $sells, Buys $buys")
    }
  }
}
