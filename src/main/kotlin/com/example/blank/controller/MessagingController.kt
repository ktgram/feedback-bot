package com.example.blank.controller

import com.example.blank.utils.*
import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.InputHandler
import eu.vendeli.tgbot.annotations.UnprocessedHandler
import eu.vendeli.tgbot.api.message.copyMessage
import eu.vendeli.tgbot.api.message.forwardMessage
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.chat.ChatType
import eu.vendeli.tgbot.types.internal.MessageUpdate
import eu.vendeli.tgbot.types.internal.onFailure

class MessagingController {
    @InputHandler(["messaging"])
    suspend fun messaging(update: MessageUpdate?, user: User, bot: TelegramBot) = update?.message.run {
        when {
            update?.message?.chat?.type == ChatType.Supergroup && update.message.messageThreadId != null -> {
                // sending message topic > user
                copyMessage(CHAT_ID, update.message.messageId).send(getTgId(update.message.messageThreadId!!)!!, bot)
            }

            update?.message?.chat?.type == ChatType.Private -> {
                // get topicId by tgId or create one
                val topic = ensureTopic(user.id, bot)
                if (!topic.second) {
                    message { "You're restricted for messages." }.send(user.id, bot)
                    // if topic closed then restrict.
                    return@run
                }

                // or use copyMessage for user anonymity
                forwardMessage(user, update.message.messageId).options {
                    messageThreadId = topic.first
                }.sendReturning(CHAT_ID, bot).onFailure {
                    // if topic not found remove record in database
                    if (it.description?.contains("message thread not found") == true) removeTopic(user.id)
                    retryMessage(update, bot) // send retry msg
                }
            }
        }
        bot.inputListener[user] = "messaging" // set point again
    }

    @UnprocessedHandler
    suspend fun unprocessed(update: MessageUpdate, user: User, bot: TelegramBot) {
        // if something went wrong, try to set message listener again
        retryMessage(update, bot)
        bot.inputListener[user] = "messaging"
    }
}