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
import com.ferrifrancis.fixedbeams_phone.util.SharedPreferencesManager.Companion.sharedPreferences
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        InicializarArchivoDePreferencias()
        LeerDatosDeArchivoPreferenciasEncriptado()

        textView_singUp.setOnClickListener {
            goToRegistroActivity()
        }
        button_signIn.setOnClickListener {
            val emailSigIn = editText_usernameEmail.text.toString()
            val passwordMailSigIn = editTextPasswordSigIn.text.toString()
            if (emailSigIn.isNotEmpty()&& passwordMailSigIn.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailSigIn,passwordMailSigIn).addOnCompleteListener {
                    if (it.isSuccessful){
                        goToCategoriesActivity()
                        finish()
                    }else
                        showAlert()

                }
            }else {
                Toast.makeText(this,"Ingrese email y contrasena", Toast.LENGTH_LONG).show()
            }
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
    fun LeerDatosDeArchivoPreferenciasEncriptado(){
        editText_usernameEmail.setText ( sharedPreferences.getString(LOGIN_KEY,"") )
        editTextPasswordSigIn.setText ( sharedPreferences.getString(PASSWORD_KEY,"") )

    }
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setIcon(R.drawable.ic_baseline_warning_24)
        builder.setMessage("Usuario no registrado")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    /*public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = FirebaseAuth.getInstance().currentUser
        //updateUI(currentUser)
        if(currentUser != null){
            intent.putExtra(LOGIN_KEY,FirebaseAuth.getInstance().currentUser!!.email)
            startActivity(Intent(this,CategoriesActivity::class.java))
        }
    }*/
}