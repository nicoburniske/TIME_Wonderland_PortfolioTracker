package nicoburniske.web3

import monix.eval.Task
import nicoburniske.web3.Resources.scheduler
import nicoburniske.web3.task.TimeRebaseLogger
import nicoburniske.web3.task.whaletracker.WhaleTracker
import nicoburniske.web3.utils.BetterLogger
import org.rogach.scallop.ScallopConf

object Main extends BetterLogger {
  val CSVPATH = "log.csv"

  // Command Line arg configuration.
  case class CommandLineConf(arguments: Seq[String]) extends ScallopConf(arguments) {
    val walletAddress = opt[String](
      required = true,
      name = "walletAddress",
      descr = "Wallet address on AVAX C-Chain"
    )
    val csvPath = opt[String](
      default = Some(CSVPATH),
      name = "csvPath",
      descr = s"Path to csv file for logs. Default is $CSVPATH in working directory"
    )
    val runAtStart = opt[Boolean](
      name = "runAtStart",
      descr = "Will execute first log immediately"
    )
    verify()
  }

  def main(args: Array[String]): Unit = {
    val input = CommandLineConf(args)
    val key = Resources.conf.toOption.map(_.whaleTrackerApiKey)
    composed(input, key).runAsyncAndForget
  }

  def composed(input: CommandLineConf, whaleApiKey: Option[String]): Task[Unit] = {
    Task
      .parZip2(
        TimeRebaseLogger.schedule(input.walletAddress(), input.csvPath(), input.runAtStart()),
        whaleApiKey.fold(Task.unit)(k => WhaleTracker.schedule(k)))
      .void
  }
}
