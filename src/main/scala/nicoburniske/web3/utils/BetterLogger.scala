package nicoburniske.web3.utils

import com.typesafe.scalalogging.Logger
import monix.eval.Task
import org.slf4j.LoggerFactory

trait BetterLogger {
  protected val logger: Logger = Logger(LoggerFactory.getLogger(getClass.getSimpleName.dropRight(1)))

  def logFailures[L, R](log: String)(maybeErrors: Either[L, R]*): Unit = {
    val errorsMapped: String = errorMessage(log)(maybeErrors: _*)
    logger.error(s"$log : $errorsMapped")
  }

  def errorMessage[L, R](log: String)(maybeErrors: Either[L, R]*): String = {
    val errorsMapped = maybeErrors.partitionMap(identity)._1.map(_.toString).mkString(", ")
    s"$log : $errorsMapped"
  }

  def logTask(log: String): Task[Unit] = {
    Task.eval(logger.info(log))
  }
}
