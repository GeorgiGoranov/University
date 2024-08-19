package com.nilswinking.loopbackTodoList.retrofit

import android.content.Context
import com.nilswinking.loopbackTodoList.datatypes.Todo
import com.nilswinking.loopbackTodoList.datatypes.TodoCreateRequest
import com.nilswinking.loopbackTodoList.datatypes.TodoList
import com.nilswinking.loopbackTodoList.datatypes.TodoListCreateRequest
import com.nilswinking.loopbackTodoList.datatypes.TodoListPatchRequest
import com.nilswinking.loopbackTodoList.datatypes.TodoPatchRequest
import retrofit2.Call
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TodoListService {
    @GET("/todo-lists")
    fun getTodoLists(): Call<List<TodoList>>

    @GET("/todo-lists/{id}")
    fun getTodoList(@Path("id") id: String): Call<TodoList>

    @POST("/todo-lists")
    fun createTodoList(@Body todoListCreateRequest: TodoListCreateRequest): Call<TodoList>

    @POST("/todo-lists/{id}/todos")
    fun createTodo(@Path("id") todoListId: String, @Body todoList: TodoCreateRequest): Call<Todo>

    @PATCH("/todos/{id}")
    fun updateTodo(@Path("id") id: String, @Body todo: TodoPatchRequest): Call<Todo>

    companion object {
        private var todoListService: TodoListService? = null
        fun getInstance(context: Context): TodoListService {
            if (todoListService == null) {
                todoListService = Retrofit.getRetrofitInstance(context).create<TodoListService>()
            }
            return todoListService!!
        }
    }
}
