package com.ferrifrancis.fixedbeams_phone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.BaseAdapter
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.adapters.CartProductAdapter
import com.ferrifrancis.fixedbeams_phone.adapters.ProductAdapter
import com.ferrifrancis.fixedbeams_phone.data.product.ProductModelClass
import com.ferrifrancis.fixedbeams_phone.util.SharedPreferencesManager
import kotlinx.android.synthetic.main.activity_products.*
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import java.util.*
import kotlin.collections.ArrayList

class ShoppingCartActivity : AppCompatActivity() {
    var products = arrayListOf<ProductModelClass>()
   // lateinit var adaptador : CartProductAdapter

    var cantidadesArray = hashMapOf<View, Int>()
    var rowViews = ArrayList<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
        obtainProducts()
        continue_button.setOnClickListener {
            goToPagosActivity()
        }
      /*  */
        println("------------------")
        println("Paso shoping")
        println("------------------")
    }
    fun obtainProducts(){

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

    override fun onResume() {
        super.onResume()
        cantidadProd.text = products.size.toString()
        var total = 0.0
        var i = 0
        products.forEach{
        //    total += it.productPrice * cantidadesArray.get(rowViews.get(i))!!
            i += 1
        }
        totalTextView.text = total.toString()

        
    }

}