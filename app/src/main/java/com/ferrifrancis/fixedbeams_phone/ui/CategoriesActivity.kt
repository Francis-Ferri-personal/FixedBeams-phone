package com.ferrifrancis.fixedbeams_phone.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ferrifrancis.fixedbeams_phone.R
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.android.synthetic.main.activity_products.*

class CategoriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        imageViewCart.setOnClickListener {
            goToShoppingCartActivity()
        }
        buttonVerCarrito.setOnClickListener {
            goToShoppingCartActivity()
        }
        imageView2.setOnClickListener {
            goToProductsActivity()
        }
        imageView3.setOnClickListener {
            goToProductsActivity()
        }
        imageView4.setOnClickListener {
            goToProductsActivity()
        }
        imageView5.setOnClickListener {
            goToProductsActivity()
        }
    }
    fun goToShoppingCartActivity(){
        val intentExplicito = Intent(this,
            ShoppingCartActivity::class.java)
        startActivity(intentExplicito)
    }
    fun goToProductsActivity(){
        val intentExplicito = Intent(this,
            ProductsActivity::class.java)
        startActivity(intentExplicito)
    }
}