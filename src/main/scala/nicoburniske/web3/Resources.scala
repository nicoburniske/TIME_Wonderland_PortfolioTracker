package nicoburniske.web3

import monix.execution.Scheduler
import org.asynchttpclient.Dsl.asyncHttpClient
import sttp.client3.asynchttpclient.monix.AsyncHttpClientMonixBackend

object Resources {
  implicit val scheduler   = Scheduler.forkJoin(
    name = "web3",
    parallelism = 10,
    maxThreads = 10,
    daemonic = false
  )
  implicit val backendTask = AsyncHttpClientMonixBackend()
  val backend              = AsyncHttpClientMonixBackend.usingClient(asyncHttpClient())
}
