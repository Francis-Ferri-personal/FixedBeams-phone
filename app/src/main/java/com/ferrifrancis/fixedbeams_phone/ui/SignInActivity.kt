package com.ferrifrancis.fixedbeams_phone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ferrifrancis.fixedbeams_phone.R
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        textView_singUp.setOnClickListener {
            goToRegistroActivity()
        }
        button_signIn.setOnClickListener {
            goToCategoriesActivity()
        }
    }
    fun goToRegistroActivity(){
        val intentExplicito = Intent(this,
            RegistroActivity::class.java)
        startActivity(intentExplicito)
    }
    fun goToCategoriesActivity(){
        val intentExplicito = Intent(this,
            MainActivity::class.java)
        startActivity(intentExplicito)
    }
}