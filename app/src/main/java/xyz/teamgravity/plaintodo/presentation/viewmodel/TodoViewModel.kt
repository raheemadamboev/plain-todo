package xyz.teamgravity.plaintodo.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import xyz.teamgravity.plaintodo.data.model.TodoModel
import xyz.teamgravity.plaintodo.data.repository.TodoRepository
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val repository: TodoRepository
) : ViewModel() {

    var todo by mutableStateOf<TodoModel?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private val _event = Channel<TodoEvent> { }
    val event: Flow<TodoEvent> = _event.receiveAsFlow()

    init {
        val id = handle.get<Int>("id")!!
        if (id != -1) {
            viewModelScope.launch {
                repository.getTodo(id)?.let { todo ->
                    title = todo.title
                    description = todo.description
                    this@TodoViewModel.todo = todo
                }
            }
        }
    }

    fun onTitleChange(value: String) {
        title = value
    }

    fun onDescriptionChange(value: String) {
        description = value
    }

    fun onTodoUpdate() {
        viewModelScope.launch {

            if (title.isBlank()) {
                _event.send(TodoEvent.TitleEmpty)
                return@launch
            }

            repository.updateTodoSync(
                todo!!.copy(
                    title = title,
                    description = description
                )
            )

            _event.send(TodoEvent.TodoUpdated)
        }
    }

    sealed class TodoEvent {
        object TodoUpdated : TodoEvent()
        object TitleEmpty : TodoEvent()
    }
}