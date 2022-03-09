package xyz.teamgravity.plaintodo.presentation.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import xyz.teamgravity.plaintodo.core.constant.Route

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.TODO_LIST
    ) {
        composable(Route.TODO_LIST) {
            TodoListScreen(
                onNavigateTodo = { id ->
                    navController.navigate(Route.TODO + "?id=$id")
                },
                onNavigateTodoAdd = {
                    navController.navigate(Route.TODO_ADD)
                }
            )
        }

        composable(Route.TODO_ADD) {
            TodoAddScreen { navController.popBackStack() }
        }

        composable(Route.TODO + "?id={id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            TodoScreen { navController.popBackStack() }
        }
    }
}