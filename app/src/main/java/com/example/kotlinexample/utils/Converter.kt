package com.example.kotlinexample.utils

import com.example.kotlinexample.models.User
import com.google.gson.Gson

object Converter {
    val gson : Gson = Gson()

    fun toString(obj: Object) : String{
        return gson.toJson(obj)
    }

    fun toObject(objStr : String) : Any? {
        return gson.fromJson(objStr, User::class.java)
    }

    fun <T> toObject(objStr : String, clazz: Class<T>) : T {
        return gson.fromJson(objStr, clazz)
    }
}