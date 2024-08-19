package com.nilswinking.loopbackTodoList.datatypes

data class TodoPatchRequest(
    var title: String? = null,
    var desc: String? = null,
    var isComplete: Boolean? = null
)