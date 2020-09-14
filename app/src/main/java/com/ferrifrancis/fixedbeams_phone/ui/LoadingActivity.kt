package com.ferrifrancis.fixedbeams_phone.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        initializeSharedPreferences()
        checkInternetStatus()
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
            Toast.makeText(this,"No Connection",Toast.LENGTH_LONG).show()
            // TODO: Alert DIalog
        }
    }

    // TODO: Probar en on resume


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