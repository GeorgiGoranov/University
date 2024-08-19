package com.nilswinking.loopbackTodoList.datatypes

data class TodoListPatchRequest(
    val title: String,
    val color: String?,
)