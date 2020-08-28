package com.ferrifrancis.fixedbeams_phone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.util.ConnectionType
import com.ferrifrancis.fixedbeams_phone.util.NetworkMonitorUtil
import com.ferrifrancis.fixedbeams_phone.util.SharedPreferencesManager

class LoadingActivity : AppCompatActivity() {
    //Based on: https://devdeeds.com/android-create-splash-screen-kotlin/
    /*private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1500 //1.5 seconds*/

    private val networkMonitor = NetworkMonitorUtil(this)

    private var internetStatus = false;

    /*internal val goToInicioActivity: Runnable = Runnable {
        if (!isFinishing && internetStatus) {

            val intentToInicioActivity = Intent(this,
                SignInActivity::class.java)
            startActivity(intentToInicioActivity)
            finish()
        }
    }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        networkMonitor.register()
        //saveData()
        checkInternetStatus()
        /*// Leugo de la valdación ir a la actividad Inicio
        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(goToInicioActivity, SPLASH_DELAY)*/
    }
/*    public override fun onDestroy() {
        super.onDestroy()
    }*/

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
                        when (type) {
                            ConnectionType.Wifi -> {
                                Log.i("NETWORK_MONITOR_STATUS", "Wifi Connection")
                                Toast.makeText(this,"Wifi Connection",Toast.LENGTH_LONG).show()
                                internetStatus = isAvailable
                                if(checkSessionLogin()){
                                    goToCategoriesActivity()
                                }else{
                                    goToInicioActivity()
                                }
                            }
                            ConnectionType.Cellular -> {
                                Log.i("NETWORK_MONITOR_STATUS", "Cellular Connection")
                                Toast.makeText(this,"Cellular Connection",Toast.LENGTH_LONG).show()
                                internetStatus = isAvailable
                                if(checkSessionLogin()){
                                    goToCategoriesActivity()
                                }else{
                                    goToInicioActivity()
                                }
                            }
                            else -> {
                                internetStatus = isAvailable
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

    fun checkSessionLogin(): Boolean{
        SharedPreferencesManager.initPrefFile(this)
        var userData = SharedPreferencesManager.readUserDataFromEncryptedPrefFile(this)
        if (userData.isNullOrEmpty()){
            Log.d("TAG","GOING-BACK")
            return false
        }else{
            for (data in userData){
                if(data.isNullOrEmpty()){
                    Log.d("TAG","EMPTY")
                    return false
                }
                Log.d("TAG","NOT-EMPTY")
                return true
            }
        }

    return false
    }
    fun goToCategoriesActivity(){
        Log.d("TAG","ENTERING GO-TO-CATEGORIES")
        var userData = SharedPreferencesManager.readUserDataFromEncryptedPrefFile(this)
        val intentExplicito = Intent(this,
            CategoriesActivity::class.java).apply {
            putExtra("UserData",userData[0])
        }
        startActivity(intentExplicito)
        finish()
        Log.d("TAG","EXITING GO-TO-CATEGORIES?")
    }
    fun goToInicioActivity() {
        val intentToInicioActivity = Intent(
            this,
            SignInActivity::class.java
        )
        startActivity(intentToInicioActivity)
        finish()
    }
    fun saveData(){
        SharedPreferencesManager.writeUserDataToEncryptedPrefFile("andresbrago","123456789",this)
    }

}