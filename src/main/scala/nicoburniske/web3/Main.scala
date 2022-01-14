package nicoburniske.web3

import java.time.Instant

import generated.sushi.exchange.{Pair, Swap}
import monix.eval.Task
import nicoburniske.web3.Resources.{backendTask, scheduler}
import nicoburniske.web3.exchange.DEX.{Endpoints, PairAddress, Queries}
import nicoburniske.web3.task.TimeRebaseLogger
import nicoburniske.web3.utils.BetterLogger
import org.rogach.scallop.ScallopConf

import scala.concurrent.duration._

object Main extends BetterLogger {
  val CSVPATH = "log.csv"

  // Command Line arg configuration.
  case class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {
    val walletAddress = opt[String](
      required = true,
      name = "walletAddress",
      descr = "Wallet address on AVAX C-Chain"
    )
    val csvPath       = opt[String](
      default = Some(CSVPATH),
      name = "csvPath",
      descr = s"Path to csv file for logs. Default is $CSVPATH in working directory"
    )
    val runAtStart    = opt[Boolean](
      name = "runAtStart",
      descr = "Will execute first log immediately"
    )
    verify()
  }

  def main(args: Array[String]): Unit = {
    val input = Conf(args)
    TimeRebaseLogger.startLogScheduler(input.walletAddress(), input.csvPath(), input.runAtStart())
    scheduler.scheduleAtFixedRate(0.seconds, 1.minute)(whaleTracker().runAsyncAndForget)
  }

  case class SwapDetails(
      pair: String,
      id: String,
      timestamp: BigInt,
      amountUSD: BigDecimal,
      amount0In: BigDecimal,
      amount0Out: BigDecimal,
      amount1In: BigDecimal,
      amount1Out: BigDecimal)

  def whaleTracker(): Task[Unit] = {
    val startTime   = Instant.now().minusMillis(10.minutes.toMillis)
    val minSwap     = BigInt(10000)
    val swapDetails =
      Swap.pair(Pair.name) ~ Swap.id ~ Swap.timestamp ~ Swap.amountUSD ~ Swap.amount0In ~ Swap.amount0Out ~ Swap.amount1In ~ Swap.amount1Out
    val query       = swapDetails.mapN(SwapDetails)
    val queryTj     =
      Queries.pairSwapsSinceInstant(PairAddress.TJ_TIME_MIM, startTime, minSwap)(query).toRequest(Endpoints.TRADER_JOE)
    val querySushi  =
      Queries.pairSwapsSinceInstant(PairAddress.SUSHI_WMEMO_MIM, startTime, minSwap)(query).toRequest(Endpoints.SUSHISWAP)
    for {
      backend            <- backendTask
      //  _                  <- Task.eval(logger.info(queryTj.toCurl))
      responses          <- Task.parZip2(queryTj.send(backend), querySushi.send(backend))
      (timeReq, wmemoReq) = responses
    } yield {
      (timeReq.body, wmemoReq.body) match {
        case (Right(timeSwaps), Right(wmemoSwaps)) =>
          (timeSwaps ++ wmemoSwaps).map(_.toString).toList match {
            case list @ ::(_, _) => list.foreach(s => logger.info(s))
            case Nil             => logger.info("No whales found")
          }
        case (t, w)                                =>
          logFailures("Failed to retrieve swap information")(t, w)
      }
    }
  }
}
