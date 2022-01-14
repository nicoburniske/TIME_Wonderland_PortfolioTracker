package nicoburniske.web3.exchange

import io.circe.parser.parse
import sttp.client3.{Identity, RequestT, basicRequest, _}

/**
 * Interacts with CoinGecko API to retrieve market prices. Subject to rate limiting.
 */
object CEX {
  val COIN_GECKO_SIMPLE_PRICE = "https://api.coingecko.com/api/v3/simple/price"

  val WONDERLAND = "wonderland"
  val ETHEREUM   = "ethereum"
  val BITCOIN    = "bitcoin"
  val defaultIDs = Seq(BITCOIN, ETHEREUM)

  /**
   * Retrieves the prices for the supplied CoinGecko Crypto Ids.
   *
   * @param ids
   *   the ids to retrieve prices for
   * @return
   *   error String or Map[CoinGeckoID, Price]
   */
  def getPrices(ids: Seq[String] = defaultIDs): RequestT[Identity, Either[String, Seq[(String, Double)]], Any] = {
    val parameters = Map("ids" -> ids.mkString(","), "vs_currencies" -> "usd")
    val uri        = uri"$COIN_GECKO_SIMPLE_PRICE?$parameters"
    basicRequest.get(uri).mapResponse {
      case Left(error) => Left(error)
      case Right(body) =>
        parse(body)
          .flatMap(_.as[Map[String, Map[String, Double]]])
          .map(data => data.map { case (key, value) => key -> value("usd") }.toSeq)
          .map(t => t.sortBy(_._1))
          .left
          .map(_.getMessage)
    }
  }
}
