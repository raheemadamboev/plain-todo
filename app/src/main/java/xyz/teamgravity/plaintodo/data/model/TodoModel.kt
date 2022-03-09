package xyz.teamgravity.plaintodo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import xyz.teamgravity.plaintodo.data.local.TodoConst

@Entity(tableName = TodoConst.TABLE_TODO)
data class TodoModel(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String = "",
    val description: String = "",
    val done: Boolean = false
)
