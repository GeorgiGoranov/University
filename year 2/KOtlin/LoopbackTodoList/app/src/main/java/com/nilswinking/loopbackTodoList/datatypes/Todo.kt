package com.nilswinking.loopbackTodoList.datatypes

data class Todo(
    var id: Int,
    var title: String,
    var desc: String? = null,
    var isComplete: Boolean = false
)