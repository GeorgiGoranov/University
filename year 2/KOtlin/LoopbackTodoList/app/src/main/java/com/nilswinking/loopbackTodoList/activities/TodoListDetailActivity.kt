package com.nilswinking.loopbackTodoList.activities

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nilswinking.loopbackTodoList.datatypes.Todo
import com.nilswinking.loopbackTodoList.retrofit.TodoListService
import com.nilswinking.loopbackTodoList.ui.theme.LoopbackTodoListTheme
import com.nilswinking.loopbackTodoList.viewmodels.TodoListUIState
import com.nilswinking.loopbackTodoList.viewmodels.TodoListViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
class TodoListDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getStringExtra("todoListId")
        if (id === null) {
            finish()
        }
        id!!
        val todoListViewModel: TodoListViewModel by viewModels(factoryProducer = {
            TodoListViewModel.provideFactory(TodoListService.getInstance(this))
        })
        setContent {
            val todoListUIState by todoListViewModel.todoList.collectAsState()
            val pullRefreshState = rememberPullRefreshState(
                todoListUIState.isLoading, { todoListViewModel.loadTodoList(id) }
            )
            todoListViewModel.loadTodoList(id)
            LoopbackTodoListTheme {
                Scaffold(modifier = Modifier.fillMaxHeight(), topBar = {
                    TopAppBar(
                        todoListUIState,
                        todoListViewModel
                    )
                }, floatingActionButton = { FAB(id) }, content = content(
                    pullRefreshState,
                    todoListUIState,
                    todoListViewModel,
                    id
                )
                )
            }
        }
    }

    @Composable
    private fun content(
        pullRefreshState: PullRefreshState,
        todoListUIState: TodoListUIState.HasEntries,
        todoListViewModel: TodoListViewModel,
        id: String
    ): @Composable (PaddingValues) -> Unit = { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .padding(innerPadding)
        ) {
            LazyColumn(
                Modifier.fillMaxHeight(),
            ) {
                if (!todoListUIState.isLoading && todoListUIState.todoList !== null) {
                    items(todoListUIState.todoList.todos ?: emptyList(), key = {
                        it.hashCode()
                    }) { todo ->
                        var complete by remember {
                            mutableStateOf(todo.isComplete)
                        }
                        ListItem(headlineContent = {
                            Text(text = todo.title)
                        }, supportingContent = {
                            todo.desc?.let { it1 -> Text(text = it1) }
                        }, leadingContent = {
                            Checkbox(checked = complete, onCheckedChange = {
                                todoListViewModel.patchTodo(todo.id.toString(), isComplete = it)
                                complete = it
                                todoListViewModel.loadTodoList(id)
                            })
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

    @Composable
    private fun FAB(id: String) {
        FloatingActionButton(onClick = {
            startActivity(Intent(this, CreateTodoActivity::class.java).apply {
                putExtra("todoListId", id)
            })
        }) {
            Icon(Icons.Rounded.Add, "Add new Todo")
        }
    }

    @Composable
    private fun TopAppBar(
        todoListUIState: TodoListUIState.HasEntries,
        todoListViewModel: TodoListViewModel
    ) {
        val title by lazy {
            todoListUIState.todoList?.title ?: "TodoList"
        }
        CenterAlignedTopAppBar(title = { Text(text = title) }, actions = {
            IconButton(onClick = todoListViewModel::loadTodoLists) {
                Icon(
                    imageVector = Icons.Rounded.Refresh,
                    contentDescription = "Localized description"
                )
            }
        }, navigationIcon = {
            IconButton(onClick = {
                finish()
            }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Go back to TodoLists"
                )
            }
        })
    }
}
