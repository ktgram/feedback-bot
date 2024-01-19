package com.example.blank.utils

import com.example.blank.entity.MetaData
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

fun saveBotId(botId: Long) = transaction {
    MetaData.insertIgnore { it[this.botId] = botId }
}

fun setChatId(chatId: Long) = transaction {
    MetaData.update { it[MetaData.chatId] = chatId }
}

val CHAT_ID: Long by lazy {
    transaction {
        MetaData.selectAll().first()[MetaData.chatId]!!
    }
}

val BOT_ID: Long by lazy {
    transaction {
        MetaData.selectAll().first()[MetaData.botId]
    }
}