package com.ferrifrancis.fixedbeams_phone.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ferrifrancis.fixedbeams_phone.ID_PRODUCT
import com.ferrifrancis.fixedbeams_phone.PRODUCT
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.URL_BACKEND
import com.ferrifrancis.fixedbeams_phone.common.DETAIL_MAX_CHARS
import com.ferrifrancis.fixedbeams_phone.data.product.ProductModelClass
import com.ferrifrancis.fixedbeams_phone.util.SharedPreferencesManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*

class DetailsActivity : AppCompatActivity() {

    private lateinit var product: ProductModelClass
    var products = arrayListOf<ProductModelClass>()
    private var idProduct = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        idProduct = intent.getIntExtra(ID_PRODUCT, idProduct)
        if (idProduct > 0) {
            requestHttpProduct(idProduct)
        }
    }

    private fun requestHttpProduct(idProduct: Int) {
        val url: String = "$URL_BACKEND/$PRODUCT/" + idProduct.toString()
        val queue = Volley.newRequestQueue(this)
        val request = StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->
            try {
                val product = Gson().fromJson(response, ProductModelClass::class.java)
                product.quantity = 1
                setFields(product)
                addButtonListeners(product)
            } catch (e: Exception) {
                Log.d("Error", e.toString())
            }
        }, Response.ErrorListener { error -> Log.d("Error", error.toString()) })
        queue.add(request)
    }


    fun setFields(product: ProductModelClass) {
        val textViewTitulo = findViewById<TextView>(R.id.tituloTextView)
        val textViewPrice = findViewById<TextView>(R.id.priceTextView)
        val textViewDetalle = findViewById<TextView>(R.id.detallesTextView)
        val textViewDescripcion = findViewById<TextView>(R.id.descriptionTextView)
        val textViewCantidad = findViewById<TextView>(R.id.cantidadTextView)
        val buttonAddProduct = findViewById<Button>(R.id.addCartButton)
        val imageViewProduct = findViewById<ImageView>(R.id.imageView)

        Glide.with(this).load(product.srcImage)
            .centerInside()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageViewProduct)

        textViewTitulo.text = product.name
        if (product.summary.length > DETAIL_MAX_CHARS) {
            textViewDetalle.text = product.summary.substring(0, 50)
        } else {
            textViewDetalle.text = product.summary
        }

        textViewDescripcion.text = product.summary
        textViewPrice.text = "$" + product.price.toString()
        textViewCantidad.text = product.quantity.toString()

    }

    fun addButtonListeners(product: ProductModelClass) {
        var tempCounter = cantidadTextView.text.toString().toInt()
        imageButton.setOnClickListener {
            tempCounter += 1
            cantidadTextView.text = tempCounter.toString()
        }
        imageButton2.setOnClickListener {
            tempCounter -= 1
            cantidadTextView.text = tempCounter.toString()
        }
        addCartButton.setOnClickListener {
            var arrayList: ArrayList<ProductModelClass> =
                SharedPreferencesManager.readSavedProducts(it.context)
            product.quantity = tempCounter
            SharedPreferencesManager.saveProduct(product, this, "Activity")
            Toast.makeText(this, "Producto agregado", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}


