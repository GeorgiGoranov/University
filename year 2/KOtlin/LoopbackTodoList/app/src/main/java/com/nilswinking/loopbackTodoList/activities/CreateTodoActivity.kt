package com.nilswinking.loopbackTodoList.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nilswinking.loopbackTodoList.R
import com.nilswinking.loopbackTodoList.retrofit.TodoListService
import com.nilswinking.loopbackTodoList.ui.theme.LoopbackTodoListTheme
import com.nilswinking.loopbackTodoList.viewmodels.TodoListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class CreateTodoActivity : ComponentActivity() {
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
            var title by rememberSaveable { mutableStateOf("") }
            var description by rememberSaveable { mutableStateOf("") }
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()
            LoopbackTodoListTheme {
                Scaffold(topBar = { TopAppBar() }, floatingActionButton = {
                    FAB(
                        todoListViewModel, id, title, description, scope, snackbarHostState
                    )
                }, snackbarHost = { SnackbarHost(snackbarHostState) },
                    content = content(title, description)
                )
            }
        }
    }

    @Composable
    private fun content(
        title: String,
        description: String
    ): @Composable (PaddingValues) -> Unit {
        var title1 = title
        var description1 = description
        return { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = title1,
                    onValueChange = { title1 = it },
                    label = { Text(text = "Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next, keyboardType = KeyboardType.Text
                    ),
                )
                OutlinedTextField(
                    value = description1,
                    onValueChange = { description1 = it },
                    label = { Text(text = "Description") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    @Composable
    private fun FAB(
        todoListViewModel: TodoListViewModel,
        id: String,
        title: String,
        description: String,
        scope: CoroutineScope,
        snackbarHostState: SnackbarHostState
    ) {
        ExtendedFloatingActionButton(onClick = {
            todoListViewModel.createTodo(id, title, description) {
                if (it.success) {
                    todoListViewModel.loadTodoList(id)
                    finish()
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "Failed to create todo"
                        )
                    }
                }
            }
        }, icon = {
            Icon(
                painter = painterResource(id = R.drawable.round_save_24), contentDescription = ""
            )
        }, text = {
            Text(text = "Create")
        })
    }

    @Composable
    private fun TopAppBar() {
        CenterAlignedTopAppBar(title = { Text(text = "Create Todo") })
    }
}
