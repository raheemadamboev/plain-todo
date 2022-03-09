package xyz.teamgravity.plaintodo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.teamgravity.plaintodo.data.model.TodoModel

@Database(
    entities = [TodoModel::class],
    version = TodoConst.VERSION
)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
}