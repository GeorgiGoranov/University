package com.nilswinking.loopbackTodoList.datatypes

data class TodoList(
    val id: Int,
    val title: String,
    val color: String?,
    val todos: MutableList<Todo>? = mutableListOf()
)