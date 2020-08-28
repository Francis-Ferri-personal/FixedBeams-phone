@file:Suppress("DEPRECATION")

package com.ferrifrancis.fixedbeams_phone.util

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.ferrifrancis.fixedbeams_phone.common.LOGIN_KEY
import com.ferrifrancis.fixedbeams_phone.common.PASSWORD_KEY
import com.ferrifrancis.fixedbeams_phone.common.PRODUCTS_KEY
import com.ferrifrancis.fixedbeams_phone.common.SECRET_SHARED_FILENAME
import com.ferrifrancis.fixedbeams_phone.data.ProductModelClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SharedPreferencesManager{
    companion object{
        lateinit var sharedPreferences: SharedPreferences
        fun initPrefFile(context: Context) {
            val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            sharedPreferences = EncryptedSharedPreferences.create(
                SECRET_SHARED_FILENAME,//filename
                masterKeyAlias,
                context,//context
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }

        fun writeUserDataToEncryptedPrefFile(login: String, password: String, context: Context){
            initPrefFile(context)
            sharedPreferences.edit()
                .putString(LOGIN_KEY, login)
                .putString(PASSWORD_KEY, password)
                .apply()
        }

        fun readUserDataFromEncryptedPrefFile(context: Context): Array<String> {
            initPrefFile(context)
            return arrayOf<String>(sharedPreferences.getString(LOGIN_KEY, "")!!,
                sharedPreferences.getString(PASSWORD_KEY, "")!!
            )
        }

        fun readSavedProducts(context: Context): ArrayList<ProductModelClass> {
            initPrefFile(context)
            val listaSerializada = Gson() // Serializar tipo JSON
            val contenidoJSON = sharedPreferences.getString(PRODUCTS_KEY, null)
            val tipo: Type = object : TypeToken<ArrayList<ProductModelClass>>() {}.type

            if(contenidoJSON != null){
                return listaSerializada.fromJson(contenidoJSON, tipo)
            } else{
                return ArrayList<ProductModelClass>();
            }
        }

        fun saveArrayList(list: ArrayList<ProductModelClass>, context: Context){
            initPrefFile(context)
            val editor = sharedPreferences.edit()
            val listaSerializada = Gson() // Serializar tipo JSON
            val contenidoJSON = listaSerializada.toJson(list)
            editor.putString(PRODUCTS_KEY, contenidoJSON)
            editor.apply()
        }

        fun saveProduct(nuevoProducto: ProductModelClass, context: Context, origen: String){
            var productosArrayList = readSavedProducts(context)
            var existe = false
            for(producto in productosArrayList){
                if(producto.productId == nuevoProducto.productId){
                    if(origen == "Adaptador"){
                        producto.productQuantity = 0
                    } // Si viene del adaptador será 0, es su inicialización
                    else if (origen=="CarritoAdaptador"){
                        producto.productQuantity = nuevoProducto.productQuantity!!
                    }else{
                        producto.productQuantity = producto.productQuantity!! + nuevoProducto.productQuantity!!
                    }
                    existe = true
                    break
                }
            }

            if(!existe){
                productosArrayList.add(nuevoProducto)
            }

            saveArrayList(productosArrayList,context)
        }

        fun clear(context: Context){
            initPrefFile(context)
            var editor = sharedPreferences.edit().clear().commit()
        }
    }

}
