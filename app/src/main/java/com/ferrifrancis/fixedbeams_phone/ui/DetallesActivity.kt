package com.ferrifrancis.fixedbeams_phone.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.data.ProductModelClass
import com.ferrifrancis.fixedbeams_phone.util.SharedPreferencesManager
import kotlinx.android.synthetic.main.activity_detalles.*
import kotlinx.android.synthetic.main.layout_shopping_cart_list_item.view.*

class DetallesActivity : AppCompatActivity() {

    private lateinit var product : ProductModelClass
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
        button.setOnClickListener {
            var arrayList: ArrayList<ProductModelClass> = SharedPreferencesManager.readSavedProducts(it.context)
            product.productQuantity = tempCounter
            SharedPreferencesManager.saveProduct(product, this, "Activity")
            Toast.makeText(this, "Producto agregado", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}