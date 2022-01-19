package nicoburniske.web3.task

import java.time.Instant

import monix.eval.Task
import nicoburniske.web3.Resources.{backend, scheduler}
import nicoburniske.web3.exchange.{DEX, SwapDetails}

import scala.concurrent.duration.{FiniteDuration, _}

object OrderbookVolume {

  def calculateVolume(swaps: Seq[SwapDetails]): (BigDecimal, BigDecimal) = {
    val grouped = swaps.groupBy(_.token0Received > 0)
    val buys = grouped(true).map(_.amountUSD).sum
    val sells = grouped(false).map(_.amountUSD).sum
    (sells, buys)
  }

  def findVolumeWMEMO(duration: FiniteDuration): Task[(BigDecimal, BigDecimal)] = {
    for {
      since <- Task.eval(Instant.now().minusMillis(duration.toMillis))
      response <- DEX.wMemoSwapsRequest(since, 0).send(backend)
      result <- response.body match {
        case Left(value) => Task.raiseError(value)
        case Right(swaps) => Task.eval(calculateVolume(swaps))
      }
    } yield result
  }

  def main(args: Array[String]): Unit = {
    findVolumeWMEMO(30.minutes)
      .map { case (sells, buys) => (SwapDetails.round(sells), SwapDetails.round(buys)) }
      .foreach { case (sells, buys) => println(s"Sells $sells, Buys $buys")
      }
  }
}
