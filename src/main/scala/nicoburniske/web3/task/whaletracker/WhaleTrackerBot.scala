package nicoburniske.web3.task.whaletracker

import com.bot4s.telegram.cats.Polling
import com.bot4s.telegram.methods.SendMessage
import com.bot4s.telegram.models.Message
import monix.eval.Task
import nicoburniske.web3.task.TelegramNotificationBot

case class WhaleTrackerBot(override val token: String) extends TelegramNotificationBot(token) with Polling[Task] {

  def sendMessage(msg: String): Task[Unit] = {
    for {
      chats <- getChats
      _ <- Task.now(logger.trace(s"Sending message to ${chats.size} chats"))
      messages = chats.map(SendMessage(_, msg, disableNotification = Some(true)))
      _ <- Task.parTraverse(messages)(request(_))
    } yield ()
  }

  def getChats: Task[List[String]] = {
    Task.now(List("5012128436"))
  }

  override def receiveMessage(msg: Message): Task[Unit] =
    msg.text.fold(Task.unit) {
      case "/start" =>
        for {
          _ <- request(SendMessage(msg.source, s"dm me this chat id -> ${msg.source} so I can add you manually"))
        } yield logger.info(s"User added from chat ${msg.source.toString}")
      case _ =>
        Task.now(logger.info(s"Non-recognized command ${msg.text} from ${msg.source}"))
    }
}
