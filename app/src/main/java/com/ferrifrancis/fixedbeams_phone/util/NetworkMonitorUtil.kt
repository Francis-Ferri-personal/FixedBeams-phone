package com.ferrifrancis.fixedbeams_phone.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager

//Código obtenido de https://johncodeos.com/how-to-check-for-internet-connection-in-android-using-kotlin/

enum class ConnectionType {
    Wifi, Cellular
}

class NetworkMonitorUtil(context: Context) {

    private var mContext = context

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    lateinit var result: ((isAvailable: Boolean, type: ConnectionType?) -> Unit)

    @Suppress("DEPRECATION")
    fun register() {
        // Use Intent Filter for Android 8 and below
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        mContext.registerReceiver(networkChangeReceiver, intentFilter)
    }

    fun unregister() {
        mContext.unregisterReceiver(networkChangeReceiver)
    }

    @Suppress("DEPRECATION")
    private val networkChangeReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo

            if (activeNetworkInfo != null) {
                // Get Type of Connection
                when (activeNetworkInfo.type) {
                    ConnectivityManager.TYPE_WIFI -> {

                        // WIFI
                        result(true, ConnectionType.Wifi)
                    }
                    else -> {

                        // CELLULAR
                        result(true, ConnectionType.Cellular)
                    }
                }
            } else {

                // UNAVAILABLE
                result(false, null)
            }
        }
    }
}