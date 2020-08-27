package com.ferrifrancis.fixedbeams_phone.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.util.ConnectionType
import com.ferrifrancis.fixedbeams_phone.util.NetworkMonitorUtil
import java.io.FileOutputStream

class LoadingActivity : AppCompatActivity() {
    //Based on: https://devdeeds.com/android-create-splash-screen-kotlin/
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1500 //1.5 seconds

    private val networkMonitor = NetworkMonitorUtil(this)

    private var internetStatus = false;

    internal val goToInicioActivity: Runnable = Runnable {
        if (!isFinishing && internetStatus) {

            val intentToInicioActivity = Intent(this,
                SignInActivity::class.java)
            startActivity(intentToInicioActivity)
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        checkInternetStatus()
        // TODO: Vrificar que el usuario este logueado
        // TODO: Cargar bases de datos
        // Leugo de la valdación ir a la actividad Inicio
        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(goToInicioActivity, SPLASH_DELAY)
    }
    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(goToInicioActivity)
        }

        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }

    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
    }

    fun checkInternetStatus(){
        networkMonitor.result = { isAvailable, type ->
            runOnUiThread {
                when (isAvailable) {
                    true -> {
                        this.internetStatus = when (type) {
                            ConnectionType.Wifi -> {
                                Log.i("NETWORK_MONITOR_STATUS", "Wifi Connection")
                                Toast.makeText(this,"Wifi Connection",Toast.LENGTH_LONG).show()
                                isAvailable
                            }
                            ConnectionType.Cellular -> {
                                Log.i("NETWORK_MONITOR_STATUS", "Cellular Connection")
                                Toast.makeText(this,"Cellular Connection",Toast.LENGTH_LONG).show()
                                isAvailable
                            }
                            else -> {
                                isAvailable
                            }
                        }
                    }
                    false -> {
                        Log.i("NETWORK_MONITOR_STATUS", "No Connection")
                        Toast.makeText(this,"No Connection",Toast.LENGTH_LONG).show()
                        internetStatus = isAvailable
                    }
                }
            }
        }
    }

}