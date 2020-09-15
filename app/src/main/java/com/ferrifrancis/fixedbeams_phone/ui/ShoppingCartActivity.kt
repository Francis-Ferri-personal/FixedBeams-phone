package com.ferrifrancis.fixedbeams_phone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.BaseAdapter
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.data.ProductModelClass
import com.ferrifrancis.fixedbeams_phone.ui.adapter.CartProductAdapter
import com.ferrifrancis.fixedbeams_phone.ui.adapter.ProductAdapter
import kotlinx.android.synthetic.main.activity_products.*
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import java.util.*
import kotlin.collections.ArrayList

class ShoppingCartActivity : AppCompatActivity() {
    var products = arrayListOf<ProductModelClass>()
    lateinit var adaptador : CartProductAdapter

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
        products.add(
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
        )
        products.add(
            ProductModelClass(
                3,
                "Producto 3",
                "Detalle 3",
                20.00
            )
        )
        adaptador = CartProductAdapter(
            this,
            products,
            cantidadesArray,
            rowViews
        )
        cantidadesArray  = adaptador.getCantidades()
        listView_cart_products.adapter = adaptador

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
            total += it.productPrice * cantidadesArray.get(rowViews.get(i))!!
            i += 1
        }
        totalTextView.text = total.toString()

        
    }

}