package com.example.kotlinexample

import android.app.Application
import android.content.Context

class MyApp:Application() {

    companion object {
        var context : Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}