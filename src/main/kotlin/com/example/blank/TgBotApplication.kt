package com.example.blank

import com.example.blank.entity.MetaData
import com.example.blank.entity.Topics
import com.example.blank.utils.saveBotId
import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.api.botactions.getMe
import eu.vendeli.tgbot.types.internal.getOrNull
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main() = runBlocking {
    val bot = TelegramBot("BOT_TOKEN")

    Database.connect("jdbc:sqlite:./Topics.db", "org.sqlite.JDBC")
    transaction {
        SchemaUtils.create(MetaData, Topics)
    }

    // save bot info
    getMe().sendAsync(bot).getOrNull()?.also {
        saveBotId(it.id)
    }

    bot.handleUpdates()
}
