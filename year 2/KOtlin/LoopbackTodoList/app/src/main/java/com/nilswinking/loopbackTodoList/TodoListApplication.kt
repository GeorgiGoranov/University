package com.nilswinking.loopbackTodoList

import android.app.Application
import com.google.android.material.color.DynamicColors

class TodoListApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}