package xyz.teamgravity.plaintodo.injection

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.teamgravity.plaintodo.data.local.TodoConst
import xyz.teamgravity.plaintodo.data.local.TodoDao
import xyz.teamgravity.plaintodo.data.local.TodoDatabase
import xyz.teamgravity.plaintodo.data.repository.TodoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase =
        Room.databaseBuilder(app, TodoDatabase::class.java, TodoConst.NAME)
            .addMigrations()
            .build()

    @Provides
    @Singleton
    fun provideTodoDao(db: TodoDatabase): TodoDao = db.todoDao()

    @Provides
    @Singleton
    fun provideTodoRepository(dao: TodoDao): TodoRepository = TodoRepository(dao)
}