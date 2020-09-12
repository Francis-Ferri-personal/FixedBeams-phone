package com.ferrifrancis.fixedbeams_phone.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ferrifrancis.fixedbeams_phone.R
import kotlinx.android.synthetic.main.activity_detalles.*

class DetallesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles)
        addButtonListeners()
    }
    fun addButtonListeners(){
        var tempCounter = textView6.text.toString().toInt()
        imageButton.setOnClickListener {
            tempCounter += 1
            textView6.text = tempCounter.toString()
        }
        imageButton2.setOnClickListener {
            tempCounter -= 1
            textView6.text = tempCounter.toString()
        }
        boton_prueba.setOnClickListener {
            finish()
        }
    }
}