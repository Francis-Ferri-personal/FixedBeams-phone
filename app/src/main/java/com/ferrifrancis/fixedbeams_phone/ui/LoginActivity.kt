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
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ferrifrancis.fixedbeams_phone.*
import com.ferrifrancis.fixedbeams_phone.adapters.CategoryAdapter
import com.ferrifrancis.fixedbeams_phone.data.category.CategoriesListClass
import com.ferrifrancis.fixedbeams_phone.data.user.UserModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_categories.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initializeSharedPreferences()

        textView_singUp.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
            finish()
        }

        button_signIn.setOnClickListener {
            val email = editText_usernameEmail.text.toString()
            val password = editTextPasswordSigIn.text.toString()
            if(emailPasswordValidation(email, password)){
                requestHttpLogin(email, password)
            }
            // TODO: showAlert()
        }
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

    private fun requestHttpLogin(email: String, password: String){
        val url: String = "$URL_BACKEND/$USER_LOGIN"
        val dataJSON = JSONObject("{\"email\": \"${email}\", \"password\": \"${password}\"}")
        val queue= Volley.newRequestQueue(this)
        val request = JsonObjectRequest(url, dataJSON, Response.Listener<JSONObject> {
            response ->
            try {
                val gson = Gson()
                val user = gson.fromJson(response.toString(), UserModelClass::class.java)
                if(user.id == 0){
                    showAlertUserNotFound()
                } else {
                    saveSharedPreferences(user)
                    goToMainActivity(user)
                }
            } catch (e: Exception){
                showAlertFailedGetUser()
                Log.d("Error", e.toString())
            }
        }, Response.ErrorListener { error -> Log.d("Error", error.toString()) })
        queue.add(request)
    }

    private fun showAlertFailedGetUser(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Failed to get user")
        builder.setIcon(R.drawable.ic_alert)
        builder.setMessage("The service is not available")
        builder.setPositiveButton("Accept",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showAlertUserNotFound(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Failed to get user")
        builder.setIcon(R.drawable.ic_alert)
        builder.setMessage("The user is not registered")
        builder.setPositiveButton("Accept",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun saveSharedPreferences(user: UserModelClass){
        if(checkBox_rememberMe.isChecked){
            sharedPreferences.edit()
                .putInt(ID, user.id)
                .putString(USER_NAME, user.userName)
                .putFloat(MONEY, user.money.toFloat())
                .putString(SRC_IMAGE, user.srcImage)
                .apply()
        } else {
            sharedPreferences.edit()
                .putInt(ID, 0)
                .putString(USER_NAME, "")
                .putFloat(MONEY, 0F)
                .putString(SRC_IMAGE, "")
                .apply()
        }
    }

    private fun goToMainActivity(user: UserModelClass){
        Toast.makeText(this, "SE LLEGO", Toast.LENGTH_LONG).show()
        val intentToMain = Intent(this, MainActivity::class.java)
        intentToMain.putExtra(ID, user.id)
        intentToMain.putExtra(USER_NAME, user.userName)
        intentToMain.putExtra(MONEY, user.money)
        intentToMain.putExtra(SRC_IMAGE, user.srcImage)
        startActivity(intentToMain)
        finish()
    }
}

