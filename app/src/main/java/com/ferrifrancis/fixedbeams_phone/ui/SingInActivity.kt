package com.ferrifrancis.fixedbeams_phone.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ferrifrancis.fixedbeams_phone.*
import com.ferrifrancis.fixedbeams_phone.common.SECRET_SHARED_FILENAME
import com.ferrifrancis.fixedbeams_phone.data.user.UserModelClass
import com.ferrifrancis.fixedbeams_phone.util.SharedPreferencesManager.Companion.sharedPreferences
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.json.JSONObject

class SingInActivity : AppCompatActivity() {
    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initializeSharedPreferences()

        textViewIniciarSesion.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        buttonRegistrarUser.setOnClickListener {
            val email = editTextMail.text.toString()
            val passwordMail = editTextPasswordRegister.text.toString()
            val passwordMailConfirm = editTextPasswordRegisterConfirm.text.toString()
            val userName = editText_userName.text.toString()
            // TODO: VAlidar campos no vacios
            if (emailPasswordValidation(email, passwordMail)){
                if(passwordMail == passwordMailConfirm){
                    val firstName = editText_firstName.text.toString()
                    val lastName = editText_lastName.text.toString()
                    requestHttpLogin(email,passwordMail,userName,firstName,lastName)
                }else{
                    Toast.makeText(this,"Passwords do not match", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun goToMainActivity(user: UserModelClass){
        val intentToMain = Intent(this, MainActivity::class.java)
        intentToMain.putExtra(ID, user.id)
        intentToMain.putExtra(USER_NAME, user.userName)
        intentToMain.putExtra(MONEY, user.money)
        intentToMain.putExtra(SRC_IMAGE, ""+user.srcImage)
        startActivity(intentToMain)
        finish()
    }

    fun emailPasswordValidation(email : String, password : String): Boolean{
        val validEmail : Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val validPassword : Boolean = (password.length >= MIN_PASSWORD_LENGTH)
        if (validEmail && validPassword){
            return true
        } else {
            Toast.makeText(this,"Please, enter a valid email and password", Toast.LENGTH_LONG).show()
        }
        return false
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

    private fun requestHttpLogin(email: String, password: String, userName: String, firstName: String, lastName: String){
        val url: String = "$URL_BACKEND/$USER_SING_IN"
        val data = "{" +
                "\"email\": \"${email}\"," +
                "\"password\": \"${password}\"," +
                "\"userName\": \"${userName}\"," +
                "\"firstName\": \"${firstName}\"," +
                "\"lastName\": \"${lastName}\"," +
                "\"idsRol\": [${USER_ROL}]," +
                "\"money\": ${INITIAL_MONEY}" +
            "}"
        val dataJSON = JSONObject(data)
        val queue= Volley.newRequestQueue(this)
        val request = JsonObjectRequest(url, dataJSON, Response.Listener<JSONObject> {
                response ->
            try {
                val gson = Gson()
                val user = gson.fromJson(response.toString(), UserModelClass::class.java)
                if(user.id == 0){
                    showAlertUserNotRegistered()
                } else {
                    if (user.srcImage.isNullOrBlank()){
                        user.srcImage = "default"
                    }
                    saveCreatedUserData(user)
                    goToMainActivity(user)
                }
            } catch (e: Exception){

                Log.d("Error", e.toString())
                showAlertFailedRegisteringUser()
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }
        }, Response.ErrorListener { error -> Log.d("Error", error.toString()) })
        queue.add(request)
    }

    private fun saveCreatedUserData(user: UserModelClass){
        sharedPreferences.edit()
            .putInt(ID, user.id)
            .putString(USER_NAME, user.userName)
            .putFloat(MONEY, user.money.toFloat())
            .putString(SRC_IMAGE, user.srcImage)
            .putString(EMAIL, editTextMail.text.toString())
            .putString(PASSWORD, editTextPasswordRegister.text.toString())
            .apply()
    }

    private fun showAlertFailedRegisteringUser(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error to register user")
        builder.setIcon(R.drawable.ic_alert)
        builder.setMessage("The service is not available or internal error")
        builder.setPositiveButton("Accept",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showAlertUserNotRegistered(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Failed to register user")
        builder.setIcon(R.drawable.ic_alert)
        builder.setMessage("The user was not registered")
        builder.setPositiveButton("Accept",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }



}