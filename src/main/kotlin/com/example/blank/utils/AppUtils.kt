package com.example.blank.utils

import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.types.internal.MessageUpdate


suspend fun retryMessage(update: MessageUpdate, bot: TelegramBot) {
    message { "Maybe something went wrong, try send again." }.options {
        messageThreadId = update.message.messageThreadId
    }.send(update.message.chat, bot)
}