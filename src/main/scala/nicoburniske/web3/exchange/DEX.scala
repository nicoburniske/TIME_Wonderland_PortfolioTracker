package nicoburniske.web3.exchange

import java.time.Instant

import caliban.client.FieldBuilder.{ListOf, Obj, OptionOf}
import caliban.client.Operations.RootQuery
import caliban.client.SelectionBuilder.Field
import caliban.client.__Value.__ObjectValue
import caliban.client.{ArgEncoder, Argument, CalibanClientError, SelectionBuilder}
import generated.sushi.exchange.{Pair, Swap}
import sttp.client3.{Request, _}

import scala.concurrent.duration._
import scala.math.BigDecimal.RoundingMode

/**
 * Supplies query mechanisms for DEXs with `The Graph` API compatibility.
 */
object DEX {
  implicit def encodeMap[V](implicit encodeVal: ArgEncoder[V]): ArgEncoder[Seq[(String, V)]] = (seq: Seq[(String, V)]) =>
    __ObjectValue(seq.toList.map { case (key, value) => key -> encodeVal.encode(value) })

  object Endpoints {
    val TRADER_JOE = uri"https://api.thegraph.com/subgraphs/name/token-terminal/trader-joe-v1-avalanche"
    val SUSHISWAP  = uri"https://api.thegraph.com/subgraphs/name/sushiswap/avalanche-exchange"
  }

  object PairAddress {
    val TJ_TIME_MIM     = "0x113f413371fc4cc4c9d6416cf1de9dfd7bf747df"
    val SUSHI_WMEMO_MIM = "0x4d308c46ea9f234ea515cc51f16fba776451cac8"
  }

  /**
   * Graphql Queries for DEX `The Graph` API.
   */
  object Queries {
    def pairData[A](pairId: String)(innerSelection: SelectionBuilder[Pair, A]): SelectionBuilder[RootQuery, Option[A]] =
      Field("pair", OptionOf(Obj(innerSelection)), arguments = List(Argument("id", pairId, "String!")))

    def pairSwaps[A](pairId: String)(innerSelection: SelectionBuilder[Swap, A]): SelectionBuilder[RootQuery, Seq[A]] =
      Field("pair", Obj(Pair.swaps(innerSelection)), arguments = List(Argument("id", pairId, "String!")))

    def pairSwapsSinceInstant[A](pairId: String, instant: Instant, minTradeAmount: BigInt = BigInt(0))(
        swapSelection: SelectionBuilder[Swap, A]): SelectionBuilder[RootQuery, Seq[A]] = {
      val instantToSec = BigInt(instant.toEpochMilli.millis.toSeconds)
      val filterArg = Argument("where", Seq("timestamp_gte" -> instantToSec, "amountUSD_gte" -> minTradeAmount), "")
      val orderByArg = Argument("orderBy", "timestamp", "")
      val sortDirection = Argument("orderDirection", "desc", "")
      val swapsField    = Field[Pair, List[A]](
        "swaps",
        ListOf(Obj(swapSelection)),
        arguments = List(filterArg, orderByArg, sortDirection)
      )
      Field("pair", Obj(swapsField), arguments = List(Argument("id", pairId, "String!")))
    }
  }

  def priceWMEMO(): Request[Either[CalibanClientError, Option[(String, BigDecimal)]], Any] = {
    val query = Queries.pairData(PairAddress.SUSHI_WMEMO_MIM)(Pair.name ~ Pair.token1Price)
    query.toRequest(Endpoints.SUSHISWAP).mapResponseRight(round)
  }

  def priceTIME(): Request[Either[CalibanClientError, Option[(String, BigDecimal)]], Any] = {
    val query = Queries.pairData(PairAddress.TJ_TIME_MIM)(Pair.name ~ Pair.token0Price)
    query.toRequest(Endpoints.TRADER_JOE).mapResponseRight(round)
  }

  private def round(res: Option[(String, BigDecimal)]): Option[(String, BigDecimal)] = {
    res.map { case (label, price) => (label, price.setScale(3, RoundingMode.HALF_UP)) }
  }

  def main(args: Array[String]): Unit = {
//    val query3 = Queries.pairSwapsSinceInstant(PairAddress.TJ_TIME_MIM, Instant.now().minusSeconds(60))(
//      Swap.id ~ Swap.timestamp ~ Swap.amountUSD
//    )
//    val uri    = Endpoints.TRADER_JOE
//
//    val request = query3.toRequest(uri)
//    println(request.toCurl)
//
//    val backend = HttpURLConnectionBackend()
//    val value1  = request.send(backend)
//    // println(value1.toString())
//    value1.body match {
//      case Right(s)                        =>
//        println(s.size)
//        println(s.mkString("\n"))
////        println(s.head)
////        println(s.last)
//      case Left(value: CalibanClientError) => println(value.getMessage())
//    }
  }

}
