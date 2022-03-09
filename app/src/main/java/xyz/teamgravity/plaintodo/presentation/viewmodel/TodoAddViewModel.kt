package xyz.teamgravity.plaintodo.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
class TodoAddViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private val _event = Channel<TodoAddEvent> { }
    val event: Flow<TodoAddEvent> = _event.receiveAsFlow()

    fun onTitleChange(value: String) {
        title = value
    }

    fun onDescriptionChange(value: String) {
        description = value
    }

    fun onTodoAdd() {
        viewModelScope.launch {

            if (title.isBlank()) {
                _event.send(TodoAddEvent.TitleEmpty)
                return@launch
            }

            repository.insertTodoSync(
                TodoModel(
                    title = title,
                    description = description
                )
            )

            _event.send(TodoAddEvent.TodoAdded)
        }
    }

    sealed class TodoAddEvent {
        object TodoAdded : TodoAddEvent()
        object TitleEmpty : TodoAddEvent()
    }
}