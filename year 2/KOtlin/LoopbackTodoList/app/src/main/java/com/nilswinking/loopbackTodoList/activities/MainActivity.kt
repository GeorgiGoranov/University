package com.nilswinking.loopbackTodoList.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color as Color1
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.nilswinking.loopbackTodoList.datatypes.Todo
import com.nilswinking.loopbackTodoList.retrofit.TodoListService
import com.nilswinking.loopbackTodoList.ui.theme.LoopbackTodoListTheme
import com.nilswinking.loopbackTodoList.viewmodels.TodoListViewModel
import com.nilswinking.loopbackTodoList.viewmodels.TodoListsUIState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val todoListViewModel: TodoListViewModel by viewModels(factoryProducer = {
            TodoListViewModel.provideFactory(TodoListService.getInstance(this))
        })
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val todoListUIState by todoListViewModel.todoLists.collectAsState()
            val pullRefreshState = rememberPullRefreshState(
                todoListUIState.isLoading, todoListViewModel::loadTodoLists
            )
            LoopbackTodoListTheme {
                /**
                 * The scaffold is a pre-defined composable that implements the basic material design.
                 * It takes in child composables for various slots like the TopAppBar, FloatingActionButton or the content.
                 */
                Scaffold(modifier = Modifier.statusBarsPadding(),
                    topBar = { TopAppBar(todoListViewModel) },
                    floatingActionButton = { FAB() },
                    content = { innerPadding ->
                        Content(
                            pullRefreshState,
                            todoListUIState,
                            innerPadding
                        )
                    }
                )
            }
        }
    }

    /**
     * The content of the scaffold is the main content of the screen.
     */
    @Composable
    private fun Content(
        pullRefreshState: PullRefreshState,
        todoListUIState: TodoListsUIState.HasEntries,
        innerPadding: PaddingValues
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .padding(innerPadding)
        ) {
            LazyColumn(
                Modifier.fillMaxHeight(),
            ) {
                if (!todoListUIState.isLoading) {
                    items(todoListUIState.todoLists) { todolist ->
                        val color = try {
                            Color1(Color.parseColor(todolist.color))
                        } catch (e: Exception) {
                            MaterialTheme.colorScheme.primary
                        }
                        ListItem(modifier = Modifier.clickable {
                            val intent = Intent(
                                this@MainActivity,
                                TodoListDetailActivity::class.java
                            )
                            intent.putExtra("todoListId", todolist.id.toString())
                            startActivity(intent)
                        }, headlineContent = {
                            Text(text = todolist.title)
                        }, leadingContent = {
                            // circle using color
                            Surface(
                                modifier = Modifier.size(24.dp),
                                color = color,
                                shape = RoundedCornerShape(12.dp)
                            ) {}
                        }, trailingContent = {
                            Text(text = if (todolist.todos !== null) todolist.todos.size.toString() else "0")
                        })
                    }
                }
            }
            PullRefreshIndicator(
                todoListUIState.isLoading, pullRefreshState, Modifier.align(
                    Alignment.TopCenter
                )
            )
        }

    }

    /**
     * The top app bar is the app bar that shows up at the top of the screen.
     */
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TopAppBar(todoListViewModel: TodoListViewModel) {
        CenterAlignedTopAppBar(title = { Text(text = "TodoLists") }, actions = {
            IconButton(onClick = todoListViewModel::loadTodoLists) {
                Icon(
                    imageVector = Icons.Rounded.Refresh,
                    contentDescription = "Localized description"
                )
            }
        })
    }

    /**
     * The floating action button is the button that is displayed at the bottom right of the screen.
     * In this activity it is used to create a new todo list.
     */
    @Composable
    private fun FAB() {
        FloatingActionButton(onClick = {
            startActivity(
                Intent(
                    this@MainActivity, CreateTodoListActivity::class.java
                )
            )
        }) {
            Icon(
                imageVector = Icons.Rounded.Add, contentDescription = "Create Todo List"
            )
        }
    }
}
