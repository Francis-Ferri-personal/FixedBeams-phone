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
        cancel_button.setOnClickListener {
            this.finish()
        }
    }
    fun obtainProducts(){
        products = SharedPreferencesManager.readSavedProducts(this)
        if(products.size == 0){
            Toast.makeText(this, "No existen productos", Toast.LENGTH_LONG).show()
        }
        val adapter = CartProductAdapter(products)

        recyclerView.adapter = adapter

       /* listView_cart_products.adapter =
            CartProductAdapter(
                this,
                products
            )*/
    }
    fun goToPagosActivity(){
        val intentExplicito = Intent(this,
            PagosActivity::class.java)
        startActivity(intentExplicito)
    }

    override fun onResume() {
        super.onResume()
        /*cantidadProd.text = products.size.toString()
        var total = 0.0
        var i = 0
        products.forEach{
        //    total += it.productPrice * cantidadesArray.get(rowViews.get(i))!!
            i += 1
        }
        totalTextView.text = total.toString()*/

        
    }

}