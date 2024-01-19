package com.example.blank.entity

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object MetaData : Table() {
    val botId: Column<Long> = long("bot_id")
    val chatId: Column<Long?> = long("chat_id").uniqueIndex().nullable()

    override val primaryKey = PrimaryKey(botId)
}