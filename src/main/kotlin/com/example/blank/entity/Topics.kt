package com.example.blank.entity

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object Topics : IntIdTable() {
    val tgId: Column<Long> = long("tg_id").uniqueIndex()
    val topicId: Column<Int> = integer("topic_id")
    val isClosed: Column<Boolean> = bool("is_closed").default(false)
}