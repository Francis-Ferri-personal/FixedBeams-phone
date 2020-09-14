package com.ferrifrancis.fixedbeams_phone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ferrifrancis.fixedbeams_phone.R

class LoadingActivity : AppCompatActivity() {
    //Based on: https://devdeeds.com/android-create-splash-screen-kotlin/
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1500 //1.5 seconds

    internal val goToInicioActivity: Runnable = Runnable {
        if (!isFinishing) {

            // val intentToInicioActivity = Intent(this, SignInActivity::class.java)
            val intentToInicioActivity = Intent(this, MainActivity::class.java)
            startActivity(intentToInicioActivity)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        // TODO: Validar conexion a internet
        // TODO: Vrificar que el usuario este logueado
        // TODO: Cargar bases de datos
        // Leugo de la valdación ir a la actividad Inicio
        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(goToInicioActivity, SPLASH_DELAY)
    }
    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(goToInicioActivity)
        }

        super.onDestroy()
    }

}