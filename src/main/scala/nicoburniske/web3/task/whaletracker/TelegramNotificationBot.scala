package nicoburniske.web3.task.whaletracker

import com.bot4s.telegram.cats.TelegramBot
import nicoburniske.web3.Resources

abstract class TelegramNotificationBot(val token: String) extends TelegramBot(token, Resources.backend)
