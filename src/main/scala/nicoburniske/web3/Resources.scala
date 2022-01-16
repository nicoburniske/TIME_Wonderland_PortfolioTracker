package nicoburniske.web3

import monix.execution.Scheduler
import org.asynchttpclient.Dsl.asyncHttpClient
import pureconfig._
import pureconfig.generic.auto._
import sttp.client3.asynchttpclient.monix.AsyncHttpClientMonixBackend

/**
 * Contains application wide resources.
 */
object Resources {
  implicit val scheduler = Scheduler.forkJoin(
    name = "web3",
    parallelism = 10,
    maxThreads = 10,
    daemonic = false
  )
  implicit val backendTask = AsyncHttpClientMonixBackend()
  val backend = AsyncHttpClientMonixBackend.usingClient(asyncHttpClient())

  case class Conf(
                   whaleTrackerApiKey: String
                 )

  val conf = ConfigSource.default.load[Conf]
}
