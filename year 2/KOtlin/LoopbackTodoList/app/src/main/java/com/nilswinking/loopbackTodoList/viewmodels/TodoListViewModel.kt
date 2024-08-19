package com.nilswinking.loopbackTodoList.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nilswinking.loopbackTodoList.datatypes.Todo
import com.nilswinking.loopbackTodoList.datatypes.TodoCreateRequest
import com.nilswinking.loopbackTodoList.datatypes.TodoList
import com.nilswinking.loopbackTodoList.datatypes.TodoListCreateRequest
import com.nilswinking.loopbackTodoList.datatypes.TodoPatchRequest
import com.nilswinking.loopbackTodoList.retrofit.TodoListService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.nilswinking.loopbackTodoList.retrofit.enqueue
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public data class TodoListIdCallback(val success: Boolean, val id: String)

sealed interface TodoListsUIState {
    data class HasEntries(
        val todoLists: List<TodoList>,
        val isLoading: Boolean,
    ) : TodoListsUIState
}

sealed interface TodoListUIState {
    data class HasEntries(
        val todoList: TodoList?,
        val isLoading: Boolean,
    ) : TodoListUIState
}


class TodoListViewModel(private val todoListService: TodoListService) : ViewModel() {

    private val TAG = TodoListViewModel::class.java.simpleName
    val todoLists = MutableStateFlow(
        TodoListsUIState.HasEntries(
            emptyList(),
            isLoading = false
        )
    )
    val todoList = MutableStateFlow(
        TodoListUIState.HasEntries(
            isLoading = false,
            todoList = null
        )
    )

    init {
        viewModelScope.launch {
            Log.d(TAG, "Fetching initial todo list data: ")
            loadTodoLists()
        }
    }

    @SuppressLint("MissingPermission")
    fun loadTodoLists() {
        viewModelScope.launch {
            todoLists.update { it.copy(isLoading = true) }
            try {
                val response = todoListService.getTodoLists().enqueue()
                if (response.isSuccessful) {
                    Log.d(TAG, "loadTodoLists: success")
                    val todoLists = response.body()!!
                    Log.d(TAG, "loadTodoLists: $todoLists")
                    this@TodoListViewModel.todoLists.update {
                        it.copy(
                            todoLists = todoLists,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "loadTodoLists: error")
                e.printStackTrace()
            }
        }
    }

    fun createTodoList(
        title: String,
        color: String,
        callback: (TodoListIdCallback) -> Unit = {}
    ) {
        val call = todoListService.createTodoList(
            TodoListCreateRequest(
                title = title,
                color = color
            )
        )
        call.enqueue(object : Callback<TodoList> {
            override fun onResponse(
                call: Call<TodoList>,
                response: Response<TodoList>
            ) {
                response.body().let {
                    if (response.code() == 200) {
                        println("Saved successfully")
                        callback(TodoListIdCallback(true, response.body()!!.id.toString()))
                        return
                    } else {
                        println("Saving failed")
                        println("Error: " + response.message())
                    }
                }
                callback(TodoListIdCallback(false, ""))
            }

            override fun onFailure(call: Call<TodoList>, t: Throwable) {
                t.printStackTrace()
                callback(TodoListIdCallback(false, ""))
            }
        })
    }

    fun createTodo(
        todoListId: String,
        title: String,
        description: String,
        callback: (TodoListIdCallback) -> Unit = {}
    ) {
        val call = todoListService.createTodo(
            todoListId,
            TodoCreateRequest(
                title = title,
                desc = description
            )
        )
        call.enqueue(object : Callback<Todo> {
            override fun onResponse(
                call: Call<Todo>,
                response: Response<Todo>
            ) {
                response.body().let {
                    if (response.code() == 200) {
                        println("Saved successfully")
                        callback(TodoListIdCallback(true, todoListId))
                        loadTodoList(todoListId)
                        return
                    } else {
                        println("Saving failed")
                        println("Error: ${response.code()}" + response.message())
                    }
                }
                callback(TodoListIdCallback(false, ""))
            }

            override fun onFailure(call: Call<Todo>, t: Throwable) {
                t.printStackTrace()
                callback(TodoListIdCallback(false, ""))
            }
        })
    }

    fun patchTodo(
        todoId: String,
        title: String? = null,
        description: String? = null,
        isComplete: Boolean? = null,
        callback: (TodoListIdCallback) -> Unit = {}
    ) {
        val call = todoListService.updateTodo(
            todoId,
            TodoPatchRequest(
                title = title,
                desc = description,
                isComplete = isComplete
            )
        )
        call.enqueue(object : Callback<Todo> {
            override fun onResponse(
                call: Call<Todo>,
                response: Response<Todo>
            ) {
                response.body().let {
                    if (response.code() == 204) {
                        println("Saved successfully")
                        callback(TodoListIdCallback(true, todoId))
                        return
                    } else {
                        println("Saving failed")
                        println("Error: ${response.code()}" + response.message())
                    }
                }
                callback(TodoListIdCallback(false, ""))
            }

            override fun onFailure(call: Call<Todo>, t: Throwable) {
                t.printStackTrace()
                callback(TodoListIdCallback(false, ""))
            }
        })
    }


    fun loadTodoList(id: String) {
        viewModelScope.launch {
            todoLists.update { it.copy(isLoading = true) }
            try {
                val response = todoListService.getTodoList(id).enqueue()
                if (response.isSuccessful) {
                    println(response.body())
                    response.body()?.let {
                        todoList.update { state ->
                            state.copy(todoList = it, isLoading = false)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    companion object {
        fun provideFactory(
            todoListService: TodoListService,
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TodoListViewModel(
                        todoListService
                    ) as T
                }
            }
    }

}