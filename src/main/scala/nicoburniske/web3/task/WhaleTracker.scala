package nicoburniske.web3.task
import com.bot4s.telegram.cats.Polling
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models.Message
import monix.eval.Task

import scala.concurrent.duration.FiniteDuration

object WhaleTracker {
  case class WhaleTrackerBot(override val token: String, chats: List[Long])
      extends TelegramNotificationBot(token)
      with Polling[Task] {

    def sendMessage(msg: String): Task[Unit] = {
      Task.parTraverse(chats)(c => request(SendMessage(c, msg))).void
    }

    override def receiveMessage(msg: Message): Task[Unit] =
      null
//      msg.text.fold(Task.pure(this)) {
//        case "subscribe" =>
//          request(SendMessage(msg.source, "you have been subscribed")).flatMap { _ =>
//            Task.now(this.copy(chats = msg.source::chats))
//          }
//        case _           => Task.pure(this)
//      }
  }

  // def task(interval: FiniteDuration, chats: List[String] = Nil): Task[SubscribeBot] = {}
}
