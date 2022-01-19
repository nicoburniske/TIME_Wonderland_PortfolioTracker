package nicoburniske.web3.task.orderbook

import java.time.Instant

import monix.eval.Task
import nicoburniske.web3.Resources.{backend, scheduler}
import nicoburniske.web3.exchange.{DEX, SwapDetails}

import scala.concurrent.duration.{FiniteDuration, _}

object OrderbookVolume {

  def findVolumeWMEMO(duration: FiniteDuration): Task[(BigDecimal, BigDecimal)] = {
    for {
      since    <- Task.eval(Instant.now().minusMillis(duration.toMillis))
      response <- DEX.wMemoSwapsRequest(since, 0).send(backend)
      result   <- response.body match {
                    case Left(value)  => Task.raiseError(value)
                    case Right(swaps) => Task.eval(calculateVolume(swaps))
                  }
    } yield result
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
