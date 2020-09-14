package com.ferrifrancis.fixedbeams_phone.ui

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.ferrifrancis.fixedbeams_phone.*
import com.ferrifrancis.fixedbeams_phone.services.Network

class LoadingActivity : AppCompatActivity() {

    private var internetStatus = false
    private lateinit var sharedPreferences : SharedPreferences

    private var  id: Int = 0
    private lateinit var userName: String
    private var money: Double = 0.0
    private lateinit var srcImage: String

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
        if (id > 0){
            return true
        }
        return false
    }

    private fun goToMainActivity(){
        val intentToMain = Intent(this, MainActivity::class.java)
        intentToMain.putExtra(ID, id)
        intentToMain.putExtra(USER_NAME, userName)
        intentToMain.putExtra(MONEY, money)
        intentToMain.putExtra(SRC_IMAGE, srcImage)
        startActivity(intentToMain)
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
        id = sharedPreferences.getInt(ID, 0)
        userName = sharedPreferences.getString(USER_NAME, "").toString()
        money = sharedPreferences.getFloat(MONEY, 0F).toDouble()
        srcImage = sharedPreferences.getString(SRC_IMAGE, "").toString()
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

    private fun askPermission(){
        requestPermissions(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION),
            CODE_MAP_PERMISSION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CODE_MAP_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    checkInternetStatus()
                } else {
                    finish()
                }
            }
        }
    }

}
