package nicoburniske.web3

import java.time.Instant
import java.util.concurrent.TimeUnit

import caliban.client.FieldBuilder.{ListOf, Obj, OptionOf}
import caliban.client.Operations.RootQuery
import caliban.client.SelectionBuilder.Field
import caliban.client.__Value.__ObjectValue
import caliban.client.{ArgEncoder, Argument, CalibanClientError, SelectionBuilder}
import generated.sushi.exchange.{Pair, Swap}
import io.circe.parser._
import sttp.client3._

import scala.concurrent.duration.FiniteDuration

/**
 * Interacts with CoinGecko API to retrieve market prices.
 */
object PriceService {
  val WONDERLAND = "wonderland"
  val ETHEREUM   = "ethereum"
  val defaultIDs = Seq(WONDERLAND, ETHEREUM)

  /**
   * Retrieves the prices for the supplied CoinGecko Crypto Ids.
   *
   * @param ids
   *   the ids to retrieve prices for
   * @return
   *   error String or Map[CoinGeckoID, Price]
   */
  def getPrices(ids: Seq[String] = defaultIDs): RequestT[Identity, Either[String, Map[String, Double]], Any] = {
    val parameters = Map("ids" -> ids.mkString(","), "vs_currencies" -> "usd")
    val uri        = uri"${Endpoints.COIN_GECKO_URL}?$parameters"
    basicRequest.get(uri).mapResponse {
      case Left(error) => Left(error)
      case Right(body)     =>
        parse(body)
          .flatMap(_.as[Map[String, Map[String, Double]]])
          .flatMap(data => Right(data.map { case (key, value) => key -> value("usd") }))
          .left
          .map(_.getMessage)
    }
  }

  implicit def encodeMap[V](implicit encodeVal: ArgEncoder[V]): ArgEncoder[Seq[(String, V)]] = (seq: Seq[(String, V)]) =>
    __ObjectValue(seq.toList.map { case (key, value) => key -> encodeVal.encode(value) })

  object Endpoints {
    val COIN_GECKO_URL = "https://api.coingecko.com/api/v3/simple/price"

    val COIN_GECKO = uri"https://api.coingecko.com/api/v3/simple/price"
    val TRADER_JOE = uri"https://api.thegraph.com/subgraphs/name/token-terminal/trader-joe-v1-avalanche"
    val SUSHISWAP  = uri"https://api.thegraph.com/subgraphs/name/sushiswap/avalanche-exchange"
  }

  object PairAddress {
    val TJ_TIME_MIM     = "0x113f413371fC4CC4C9d6416cf1DE9dFd7BF747Df".toLowerCase
    val SUSHI_WMEMO_MIM = "0x4d308C46EA9f234ea515cC51F16fba776451cac8".toLowerCase
  }

  object Queries {
    def pairData[A](pairId: String)(innerSelection: SelectionBuilder[Pair, A]): SelectionBuilder[RootQuery, Option[A]] =
      Field("pair", OptionOf(Obj(innerSelection)), arguments = List(Argument("id", pairId, "String!")))

    def pairSwaps[A](pairId: String)(innerSelection: SelectionBuilder[Swap, A]): SelectionBuilder[RootQuery, Seq[A]] =
      Field("pair", Obj(Pair.swaps(innerSelection)), arguments = List(Argument("id", pairId, "String!")))

    def pairSwapsSinceInstant[A](pairId: String, instant: Instant, minTradeAmount: BigInt = BigInt(0))(
        swapSelection: SelectionBuilder[Swap, A]): SelectionBuilder[RootQuery, Seq[A]] = {
      val instantToSec  = BigInt(FiniteDuration(instant.toEpochMilli, TimeUnit.MILLISECONDS).toSeconds)
      val filterArg     = Argument("where", Seq("timestamp_gt" -> instantToSec, "amountUSD_gt" -> minTradeAmount), "")
      val orderByArg    = Argument("orderBy", "timestamp", "")
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
    val query = Queries.pairData(PairAddress.SUSHI_WMEMO_MIM)(Pair.name ~ Pair.token0Price)
    query.toRequest(Endpoints.SUSHISWAP)
  }

  def priceTIME(): Request[Either[CalibanClientError, Option[(String, BigDecimal)]], Any] = {
    val query = Queries.pairData(PairAddress.TJ_TIME_MIM)(Pair.name ~ Pair.token0Price)
    query.toRequest(Endpoints.TRADER_JOE)
  }

  def main(args: Array[String]): Unit = {

    val query3 = Queries.pairSwapsSinceInstant(PairAddress.TJ_TIME_MIM, Instant.now().minusSeconds(60))(
      Swap.id ~ Swap.timestamp ~ Swap.amountUSD
    )
    val uri    = Endpoints.TRADER_JOE

    val request = query3.toRequest(uri)
    println(request.toCurl)

    val backend = HttpURLConnectionBackend()
    val value1  = request.send(backend)
    // println(value1.toString())
    value1.body match {
      case Right(s)                        =>
        println(s.size)
        println(s.mkString("\n"))
//        println(s.head)
//        println(s.last)
      case Left(value: CalibanClientError) => println(value.getMessage())
    }
  }

}
