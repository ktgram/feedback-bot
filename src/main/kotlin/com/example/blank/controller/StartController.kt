package com.example.blank.controller

import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.CommandHandler
import eu.vendeli.tgbot.api.message
import eu.vendeli.tgbot.types.User

class StartController {
    @CommandHandler(["/test"])
    suspend fun start(user: User, bot: TelegramBot) {
        // catch start command
        message {
            "Hello, if you want to contact the author of the bot, you can write as normal as private messages " +
                    "and everything will be forwarded to the author."
        }.send(user, bot)
        // set next point to messaging catching point
        bot.inputListener[user] = "messaging"
    }
}
