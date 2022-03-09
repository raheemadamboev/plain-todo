package xyz.teamgravity.plaintodo.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import xyz.teamgravity.plaintodo.presentation.component.TodoCard
import xyz.teamgravity.plaintodo.presentation.viewmodel.TodoListViewModel

@Composable
fun TodoListScreen(
    onNavigateTodo: (id: Int) -> Unit,
    onNavigateTodoAdd: () -> Unit
) {
    val viewmodel = hiltViewModel<TodoListViewModel>()
    val todos by remember(viewmodel) { viewmodel.todos }.collectAsState(initial = emptyList())
    val scaffold = rememberScaffoldState()

    LaunchedEffect(key1 = viewmodel.event) {
        viewmodel.event.collectLatest { event ->
            when (event) {
                TodoListViewModel.TodoListEvent.TodoDeleted -> {
                    val result = scaffold.snackbarHostState.showSnackbar(
                        message = "Todo deleted successfully!",
                        actionLabel = "Undo"
                    )
                    if (result == SnackbarResult.ActionPerformed) viewmodel.onUndoDelete()
                }

                is TodoListViewModel.TodoListEvent.NavigateTodo -> {
                    onNavigateTodo(event.todo.id)
                }

                TodoListViewModel.TodoListEvent.NavigateTodoAdd -> {
                    onNavigateTodoAdd()
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffold,
        floatingActionButton = {
            FloatingActionButton(onClick = viewmodel::onTodoAdd) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(todos) { todo ->
                TodoCard(
                    todo = todo,
                    onTodoClick = viewmodel::onTodoClick,
                    onTodoDelete = viewmodel::onTodoDelete,
                    onTodoCheckedChange = viewmodel::onTodoCheckedChange
                )
            }
        }
    }
}