package com.ferrifrancis.fixedbeams_phone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.ui.adapter.ProductAdapter
import com.ferrifrancis.fixedbeams_phone.data.ProductModelClass
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_products.*

class ProductsActivity : AppCompatActivity() {
    var products = arrayListOf<ProductModelClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        obtainProducts()
        button_viewCart.setOnClickListener {
            goToShoppingCartActivity()
        }
        imageButton_cart.setOnClickListener {
            goToShoppingCartActivity()
        }
    }
    fun obtainProducts(){
        val db = FirebaseFirestore.getInstance()
        db.collection("products")
            .get()
            .addOnCompleteListener{
                if(it.isSuccessful){
                    for (document in it.getResult()!!) {
                        products.add(
                            ProductModelClass(document.id,document["name"] as String, document["description"] as String, document["price"] as Long, 1,document["imageURL"] as String, document["manufacturer"] as String,
                                document["categories"] as ArrayList<String>
                            )
                        )
                    }

                    listView_products.adapter =
                        ProductAdapter(
                            this,
                            products
                        )
                } else{
                    Log.w("TAG", "ERROR")
                    Toast.makeText(this, "Error al leer la base de datos", Toast.LENGTH_SHORT).show()
                }
            }
    }
    fun goToShoppingCartActivity(){
        val intentExplicito = Intent(this,
            ShoppingCartActivity::class.java)
        startActivity(intentExplicito)
    }
}