package com.ferrifrancis.fixedbeams_phone.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.data.ProductModelClass
import com.ferrifrancis.fixedbeams_phone.ui.adapter.CartProductAdapter
import com.ferrifrancis.fixedbeams_phone.ui.adapter.ProductAdapter
import com.ferrifrancis.fixedbeams_phone.util.SharedPreferencesManager
import kotlinx.android.synthetic.main.activity_products.*
import kotlinx.android.synthetic.main.activity_shopping_cart.*

class ShoppingCartActivity : AppCompatActivity() {
    var products = arrayListOf<ProductModelClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
        obtainProducts()
        continue_button.setOnClickListener {
            goToPagosActivity()
        }
    }
    fun obtainProducts(){
        /*products.add(
            ProductModelClass(
                1,
                "Producto 1",
                "Detalle 1",
                20.00
            )
        )
        products.add(
            ProductModelClass(
                2,
                "Producto 2",
                "Detalle 2",
                20.00
            )
        )*/
        products = SharedPreferencesManager.readSavedProducts(this)

        listView_cart_products.adapter =
            CartProductAdapter(
                this,
                products
            )
    }
    fun goToPagosActivity(){
        val intentExplicito = Intent(this,
            PagosActivity::class.java)
        startActivity(intentExplicito)
    }
}