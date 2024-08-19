package com.nilswinking.loopbackTodoList.datatypes

data class TodoCreateRequest(
    var title: String,
    var desc: String? = null,
    var isComplete: Boolean = false
)