package com.example.blank.controller

import com.example.blank.utils.BOT_ID
import com.example.blank.utils.setChatId
import com.example.blank.utils.turnTopic
import eu.vendeli.tgbot.annotations.UpdateHandler
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.component.MessageUpdate
import eu.vendeli.tgbot.types.component.UpdateType

class MessageObserver {
    @UpdateHandler([UpdateType.MESSAGE])
    fun messageCatcher(update: MessageUpdate, user: User) {
        when {
            update.message.newChatMembers?.any { it.isBot && it.id == BOT_ID } == true -> {
                // if bot added to some chat and set as admin > save chatId
                setChatId(update.message.chat.id)
            }

            update.message.isTopicMessage == false -> return // if it's not message in topic > skip
            update.message.forumTopicClosed != null -> turnTopic(user.id)
            // if we caught topic closed event reflect it in our data
            update.message.forumTopicReopened != null -> turnTopic(user.id, false)
            // and also catch opposite situation
        }
    }
}