package com.example.kotlinexample.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    private val PREF_NAME : String = "MySharedPreference"
    private val NETWORK_STATUS : String = "network_status"
    private  var sharedPreferences :SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        @Volatile
        private var instance: PreferenceManager? = null

        fun getInstance(context: Context): PreferenceManager {
            return instance ?: synchronized(this) {
                instance ?: PreferenceManager(context)
            }
        }
    }

    fun getNetworkStatus():Boolean{
        return sharedPreferences.getBoolean(NETWORK_STATUS, false)
    }

    fun setNetworkStatus(status : Boolean){
        sharedPreferences.edit().putBoolean(NETWORK_STATUS, status).commit()
    }
}