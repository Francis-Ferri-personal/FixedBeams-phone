package com.ferrifrancis.fixedbeams_phone.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.common.DETAIL_MAX_CHARS
import com.ferrifrancis.fixedbeams_phone.data.ProductModelClass
import com.ferrifrancis.fixedbeams_phone.util.SharedPreferencesManager
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detalles.*

class DetallesActivity : AppCompatActivity() {

    private lateinit var product: ProductModelClass
    var products = arrayListOf<ProductModelClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles)
        val productID = intent.getStringExtra("ProductDocumentID")
        if (!productID.isNullOrEmpty()) {
            getProduct(productID)
        }
    }

    fun addButtonListeners() {
        var tempCounter = cantidadTextView.text.toString().toInt()
        imageButton.setOnClickListener {
            tempCounter += 1
            cantidadTextView.text = tempCounter.toString()
        }
        imageButton2.setOnClickListener {
            tempCounter -= 1
            cantidadTextView.text = tempCounter.toString()
        }
        button.setOnClickListener {
            var arrayList: ArrayList<ProductModelClass> =
                SharedPreferencesManager.readSavedProducts(it.context)
            product.productQuantity = tempCounter
            SharedPreferencesManager.saveProduct(product, this, "Activity")
            Toast.makeText(this, "Producto agregado", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    fun getProduct(productID: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("products").document(productID)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    var document = it.result
                    if (document != null && document.exists()) {
                        product = ProductModelClass(
                            document.id,
                            document["name"] as String,
                            document["description"] as String,
                            document["price"] as Long,
                            1,
                            document["imageURL"] as String,
                            document["manufacturer"] as String,
                            document["categories"] as ArrayList<String>
                        )
                        setFields()
                        addButtonListeners()
                    }
                } else {
                    Log.w("TAG", "ERROR")
                    Toast.makeText(this, "Error al leer la base de datos", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    fun setFields() {
        val textViewTitulo = findViewById<TextView>(R.id.tituloTextView)
        val textViewPrice = findViewById<TextView>(R.id.priceTextView)
        val textViewCategoria = findViewById<TextView>(R.id.categoriaTextView)
        val textViewDetalle = findViewById<TextView>(R.id.detallesTextView)
        val textViewDescripcion = findViewById<TextView>(R.id.descriptionTextView)
        val textViewCantidad = findViewById<TextView>(R.id.cantidadTextView)
        val buttonAddProduct = findViewById<Button>(R.id.button)
        val imageViewProduct = findViewById<ImageView>(R.id.imageView)

        Picasso.get().load(product.productImageURL).fit().placeholder(R.drawable.spinner)
            .into(imageViewProduct, object : Callback {
                override fun onSuccess() {
                    //
                }

                override fun onError(e: Exception) {
                    Log.d("TAG", "Error!")
                }
            })

        textViewTitulo.text = product.productName
        if (product.productDescription.length > DETAIL_MAX_CHARS) {
            textViewDetalle.text = product.productDescription.substring(0, 50)
        } else {
            textViewDetalle.text = product.productDescription
        }

        textViewDescripcion.text = product.productDescription
        textViewCategoria.text = product.productCategories!!.joinToString(", ")
        textViewPrice.text = "$" + product.productPrice.toString()
        textViewCantidad.text = product.productQuantity!!.toString()




    }
}