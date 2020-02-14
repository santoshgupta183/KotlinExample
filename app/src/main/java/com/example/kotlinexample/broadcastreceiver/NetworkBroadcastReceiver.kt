package com.example.kotlinexample.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.net.Network
import android.net.NetworkInfo
import androidx.core.content.ContextCompat.getSystemService
import com.example.kotlinexample.utils.PreferenceManager


class NetworkBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        PreferenceManager.getInstance(context).setNetworkStatus(isNetworkConnected(context))
    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (cm != null) {
            if (Build.VERSION.SDK_INT < 23) {
                val ni = cm.activeNetworkInfo

                if (ni != null) {
                    return ni.isConnected && (ni.type == ConnectivityManager.TYPE_WIFI || ni.type == ConnectivityManager.TYPE_MOBILE)
                }
            } else {
                val n = cm.activeNetwork

                if (n != null) {
                    val nc = cm.getNetworkCapabilities(n)

                    return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                }
            }
        }

        return false
    }
}
