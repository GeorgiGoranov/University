package com.nilswinking.loopbackTodoList.datatypes

data class TodoListCreateRequest(
    val title: String,
    val color: String?,
)