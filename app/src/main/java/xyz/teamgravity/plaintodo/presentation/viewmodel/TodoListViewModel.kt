package xyz.teamgravity.plaintodo.presentation.viewmodel

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
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    val todos: Flow<List<TodoModel>> = repository.getAllTodo()

    private val _event = Channel<TodoListEvent> { }
    val event: Flow<TodoListEvent> = _event.receiveAsFlow()

    private var deletedTodo: TodoModel? = null

    fun onTodoAdd() {
        viewModelScope.launch { _event.send(TodoListEvent.NavigateTodoAdd) }
    }

    fun onTodoClick(todo: TodoModel) {
        viewModelScope.launch { _event.send(TodoListEvent.NavigateTodo(todo = todo)) }
    }

    fun onTodoDelete(todo: TodoModel) {
        viewModelScope.launch {
            deletedTodo = todo
            repository.deleteTodoSync(todo)
            _event.send(TodoListEvent.TodoDeleted)
        }
    }

    fun onUndoDelete() {
        deletedTodo?.let { todo -> viewModelScope.launch { repository.insertTodoSync(todo) } }
    }

    fun onTodoCheckedChange(todo: TodoModel, checked: Boolean) {
        viewModelScope.launch { repository.updateTodoSync(todo.copy(done = checked)) }
    }

    sealed class TodoListEvent {
        object TodoDeleted : TodoListEvent()
        data class NavigateTodo(val todo: TodoModel) : TodoListEvent()
        object NavigateTodoAdd : TodoListEvent()
    }
}