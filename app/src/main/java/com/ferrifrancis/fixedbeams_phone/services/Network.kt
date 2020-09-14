package com.ferrifrancis.fixedbeams_phone.services

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity

class Network {
    companion object {
        fun networkExists(activity: AppCompatActivity): Boolean{
            var result = false
            val connectivityManager: ConnectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo: NetworkCapabilities? = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if(networkInfo != null){
                result = when {
                    networkInfo.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    networkInfo.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    networkInfo.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
            return result
        }
    }
}