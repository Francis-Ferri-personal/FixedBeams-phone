package com.ferrifrancis.fixedbeams_phone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        // TODO: Validar conexion a internet
        // TODO: Vrificar que el usuario este logueado
        // TODO: Cargar bases de datos
    }
}