package com.ferrifrancis.fixedbeams_phone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registro.*

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        buttonRegistrarUser.setOnClickListener {
            irInicio()
        }
    }
    fun irInicio(){
        val intentExplicito = Intent(this,InicioActivity::class.java)
        startActivity(intentExplicito)

    }

}