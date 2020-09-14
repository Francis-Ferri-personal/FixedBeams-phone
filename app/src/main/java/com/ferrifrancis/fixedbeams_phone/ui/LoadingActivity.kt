package com.ferrifrancis.fixedbeams_phone.ui

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.ferrifrancis.fixedbeams_phone.LOGIN_KEY
import com.ferrifrancis.fixedbeams_phone.PASSWORD_KEY
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.SECRET_FILENAME
import com.ferrifrancis.fixedbeams_phone.services.Network

class LoadingActivity : AppCompatActivity() {

    private var internetStatus = false
    lateinit var sharedPreferences : SharedPreferences

    private lateinit var  email: String
    private lateinit var password: String

    private val CODIGO_SOLICITUD_PERMISO = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        initializeSharedPreferences()
    }

    override fun onStart() {
        super.onStart()
        if (validateLocationPermissions()){
            checkInternetStatus()
        } else {
            askPermission()
        }
    }

    private fun checkInternetStatus(){
        internetStatus = Network.networkExists(this)
        if(internetStatus){
            if(checkSessionLogin()){
                goToMainActivity()
            }else{
                goToLoginActivity()
            }
        } else {
            createDialogNoConnection()
        }
    }


    private fun checkSessionLogin(): Boolean{
        readDataEncryptedPreferencesFile()
        if (email != "" && password != ""){
            return true
        }
        return false
    }

    private fun goToMainActivity(){
        val intentToMain = Intent(this, MainActivity::class.java)
        intentToMain.putExtra(LOGIN_KEY, email)
        intentToMain.putExtra(PASSWORD_KEY, password)
        startActivity(intent)
        finish()
    }

    private fun goToLoginActivity() {
        val intentToLogin = Intent(this, LoginActivity::class.java)
        startActivity(intentToLogin)
        finish()
    }

    private fun  initializeSharedPreferences(){
        val masterKey: MasterKey = MasterKey.Builder(this,  MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        sharedPreferences = EncryptedSharedPreferences.create(this,
            SECRET_FILENAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
    }

    private fun readDataEncryptedPreferencesFile() {
        email = sharedPreferences.getString(LOGIN_KEY, "").toString()
        password = sharedPreferences.getString(PASSWORD_KEY, "").toString()
    }

    private fun createDialogNoConnection(){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("No internet connection")
        dialogBuilder.setMessage("You don't have internet connection...")
        dialogBuilder.setIcon(R.drawable.ic_alert)
        dialogBuilder.setPositiveButton("Retry") { _, _ -> checkInternetStatus() }
        dialogBuilder.setNegativeButton("Quit") { _, _ -> finish() }
        dialogBuilder.create().show()
    }

    private fun validateLocationPermissions(): Boolean{
        val existsFineLocation: Boolean = ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val existsCoarseLocation: Boolean = ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
       
        return existsFineLocation && existsCoarseLocation
    }

    fun askPermission(){
        requestPermissions(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION),
            CODIGO_SOLICITUD_PERMISO)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CODIGO_SOLICITUD_PERMISO -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    checkInternetStatus()
                } else {
                    finish()
                }
            }
        }
    }


}

/*
fun saveData(){
        SharedPreferencesManager.writeUserDataToEncryptedPrefFile("andresbrago","123456789",this)
    }

fun writeDataEncryptedPreferencesFile() {
        if (checkBoxRecordarme.isChecked) {
            sharedPreferences.edit()
                .putString(LOGIN_KEY, editText_email_ingreso.text.toString())
                .putString(PASSWORD_KEY, editText_password_ingreso.text.toString())
                .apply()
        } else {
            sharedPreferences
                .edit()
                .putString(LOGIN_KEY, "")
                .putString(PASSWORD_KEY, "")
                .apply()
        }
    }
 */