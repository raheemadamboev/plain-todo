package xyz.teamgravity.plaintodo.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import xyz.teamgravity.plaintodo.presentation.viewmodel.TodoAddViewModel

@Composable
fun TodoAddScreen(
    onPopBackStack: () -> Unit
) {
    val viewmodel = hiltViewModel<TodoAddViewModel>()
    val scaffold = rememberScaffoldState()

    LaunchedEffect(key1 = viewmodel.event) {
        viewmodel.event.collectLatest { event ->
            when (event) {
                TodoAddViewModel.TodoAddEvent.TodoAdded -> {
                    onPopBackStack()
                }

                TodoAddViewModel.TodoAddEvent.TitleEmpty -> {
                    scaffold.snackbarHostState.showSnackbar(message = "Title can't be empty")
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffold,
        floatingActionButton = {
            FloatingActionButton(onClick = viewmodel::onTodoAdd) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Done"
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TextField(
                value = viewmodel.title,
                onValueChange = viewmodel::onTitleChange,
                placeholder = {
                    Text(text = "Title")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewmodel.description,
                onValueChange = viewmodel::onDescriptionChange,
                placeholder = {
                    Text(text = "Description")
                },
                singleLine = false,
                maxLines = 5,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}