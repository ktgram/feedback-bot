package com.example.blank.utils

import com.example.blank.entity.Topics
import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.api.forum.createForumTopic
import eu.vendeli.tgbot.types.component.getOrNull
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

private fun getTopicData(tgId: Long): Pair<Int, Boolean>? = transaction {
    Topics.select(Topics.topicId).where { Topics.tgId eq tgId }.firstOrNull()
        ?.let { it[Topics.topicId] to it[Topics.isClosed] }
}

fun getTgId(topicId: Int): Long? = transaction {
    Topics.select(Topics.tgId).where { Topics.topicId eq topicId }.firstOrNull()?.let {
        it[Topics.tgId]
    }
}

suspend fun ensureTopic(tgId: Long, bot: TelegramBot) = getTopicData(tgId).let { data ->
    if (data == null) {
        val threadId = createForumTopic("$tgId").sendReturning(CHAT_ID, bot).getOrNull()!!.messageThreadId
        transaction {
            Topics.insert {
                it[Topics.tgId] = tgId
                it[topicId] = threadId
            }
        }
        threadId to false
    } else data.first to data.second
}

fun turnTopic(tgId: Long, close: Boolean = true) = transaction {
    Topics.update({ Topics.tgId eq tgId }) {
        it[isClosed] = close
    }
}

fun removeTopic(tgId: Long) = transaction {
    Topics.deleteWhere { Topics.tgId eq tgId }
}