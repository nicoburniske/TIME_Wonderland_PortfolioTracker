package nicoburniske.web3.utils

import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory

trait BetterLogger {
  protected val logger: Logger = Logger(LoggerFactory.getLogger(getClass.getSimpleName.dropRight(1)))

  def logFailures[L, R](log: String)(maybeErrors: Either[L, R]*): Unit = {
    val s = maybeErrors.map(_.toString).mkString(", ")
    logger.error(s"$log : $s")
  }
}
