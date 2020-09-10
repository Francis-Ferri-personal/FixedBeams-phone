package com.ferrifrancis.fixedbeams_phone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ferrifrancis.fixedbeams_phone.R
import kotlinx.android.synthetic.main.activity_registro.*

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        buttonRegistrarUser.setOnClickListener {
            goToInicioActivity()
        }
    }
    fun goToInicioActivity(){
        val intentExplicito = Intent(this,
            SignInActivity::class.java)
        startActivity(intentExplicito)
<<<<<<< HEAD

=======
>>>>>>> parent of 38fdf7d... Registro Y login
    }

}