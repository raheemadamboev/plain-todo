package xyz.teamgravity.plaintodo.data.model

import androidx.room.PrimaryKey

data class TodoModel(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String = "",
    val description: String = "",
    val done: Boolean = false
)
