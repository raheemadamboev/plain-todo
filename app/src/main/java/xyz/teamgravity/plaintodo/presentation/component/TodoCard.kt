package xyz.teamgravity.plaintodo.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.teamgravity.plaintodo.data.model.TodoModel

@Composable
fun TodoCard(
    todo: TodoModel,
    onTodoClick: (todo: TodoModel) -> Unit,
    onTodoDelete: (todo: TodoModel) -> Unit,
    onTodoCheckedChange: (todo: TodoModel, checked: Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onTodoClick(todo)
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1F),
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = todo.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        onTodoDelete(todo)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
            if (todo.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = todo.description)
            }
        }
        Checkbox(
            checked = todo.done,
            onCheckedChange = { checked ->
                onTodoCheckedChange(todo, checked)
            }
        )
    }

}