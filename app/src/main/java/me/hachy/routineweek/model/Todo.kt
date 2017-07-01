package me.hachy.routineweek.model

import java.util.Date

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Todo : RealmObject() {

    @PrimaryKey
    var id: Long = 0

    var day: Int = 0

    var content: String? = null

    var done: Boolean = false

    var tagColor: Int = 0

    var createdTime: Date? = null
}
