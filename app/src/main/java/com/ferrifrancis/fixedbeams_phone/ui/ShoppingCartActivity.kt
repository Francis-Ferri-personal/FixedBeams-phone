package com.ferrifrancis.fixedbeams_phone.ui

import CartProductAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.data.product.ProductModelClass
import com.ferrifrancis.fixedbeams_phone.util.SharedPreferencesManager
import kotlinx.android.synthetic.main.activity_products.*
import kotlinx.android.synthetic.main.activity_shopping_cart.*

class ShoppingCartActivity : AppCompatActivity() {
    var products = arrayListOf<ProductModelClass>()
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView_cart_products)
        recyclerView.layoutManager = LinearLayoutManager(this)
        obtainProducts()
        continue_button.setOnClickListener {
            goToPagosActivity()
        }
    }
    fun obtainProducts(){
        products = SharedPreferencesManager.readSavedProducts(this)
        if(products.size == 0){
            Toast.makeText(this, "No existen productos", Toast.LENGTH_LONG).show()
        }
        val adapter = CartProductAdapter(products)

        recyclerView.adapter = adapter

    }
    fun goToPagosActivity(){
        val intentExplicito = Intent(this,
            PagosActivity::class.java)
        startActivity(intentExplicito)
    }
}