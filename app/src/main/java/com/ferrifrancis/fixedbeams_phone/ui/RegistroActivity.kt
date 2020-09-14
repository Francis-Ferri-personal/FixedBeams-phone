package com.ferrifrancis.fixedbeams_phone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.common.LOGIN_KEY
import com.ferrifrancis.fixedbeams_phone.common.PASSWORD_KEY
import com.ferrifrancis.fixedbeams_phone.common.SECRET_SHARED_FILENAME
import com.ferrifrancis.fixedbeams_phone.common.USERLOGIN
import com.ferrifrancis.fixedbeams_phone.util.SharedPreferencesManager.Companion.sharedPreferences
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registro.*

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        InicializarArchivoDePreferencias()
        buttonRegistrarUser.setOnClickListener {
            val email = editTextMail.text.toString()
            val passwordMail = editTextPasswordRegister.text.toString()
            val passwordMailConfirm = editTextPasswordRegisterConfirm.text.toString()
            val userName = editTextNameUser.text.toString()

            if (isEmailValid(email) && passwordMail.length>8 && userName.length>=8){
                if(passwordMail == passwordMailConfirm){
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,passwordMail).addOnCompleteListener {
                        if(it.isSuccessful){
                            EscribirDatosEnArchivoPreferenciasEncriptados()
                            Toast.makeText(this,"Usuario registrado correctamente", Toast.LENGTH_LONG).show()
                            goToInicioActivity()
                            finish()
                        }else{
                            showAlert()
                        }
                    }
                }else{
                    Toast.makeText(this,"Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
                }
            }else{
                if (userName.length<=8){
                    Toast.makeText(this,"El nombre de usuario debe tener al menos 8 caracteres", Toast.LENGTH_LONG).show()
                }
                Toast.makeText(this,"Datos ingresados incorrectamenta", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun goToInicioActivity(){
        val intentExplicito = Intent(this,
            MainActivity::class.java)
        startActivity(intentExplicito)
    }
    fun EscribirDatosEnArchivoPreferenciasEncriptados(){

        if(checkBoxRecordarme.isChecked){
            sharedPreferences.edit()
                .putString(USERLOGIN,editTextNameUser.text.toString())
                .putString(LOGIN_KEY,editTextMail.text.toString())
                .putString(PASSWORD_KEY,editTextPasswordRegister.text.toString())
                .apply()
        }
        else{
            sharedPreferences.edit().apply(){
                putString(USERLOGIN,"")
                putString(LOGIN_KEY,"")
                putString(PASSWORD_KEY,"")
            }.apply()

        }

    }
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se a producido un error al conectar a la base de datos")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun InicializarArchivoDePreferencias(){
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        sharedPreferences = EncryptedSharedPreferences.create(
            SECRET_SHARED_FILENAME,//filename
            masterKeyAlias,
            this,//context
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

}