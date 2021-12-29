package nicoburniske.web3

/**
 * Interacts with CoinGecko API to retrieve market prices.
 */
object PriceService {

  import io.circe.parser._
  import scalaj.http._

  val WONDERLAND = "wonderland"
  val ETHEREUM = "ethereum"
  val defaultIDs = Seq(WONDERLAND, ETHEREUM)

  /**
   * Retrieves the prices for the supplied CoinGecko Crypto Ids.
   *
   * @param ids the ids to retrieve prices for
   * @return error String or Map[CoinGeckoID, Price]
   */
  def getPrices(ids: Seq[String] = defaultIDs): Either[String, Map[String, Double]] = {
    val responseBody = Http("https://api.coingecko.com/api/v3/simple/price")
      .param("ids", ids.mkString(","))
      .param("vs_currencies", "usd").asString.body
    parse(responseBody)
      .flatMap(_.as[Map[String, Map[String, Double]]]) match {
      case Left(value) => Left(value.getMessage) // convert Exception to String.
      case Right(value) =>
        // Simplify response json.
        val simplified = value.map { case (key, value) => key -> value("usd") }
        Right(simplified)
    }
  }
}
