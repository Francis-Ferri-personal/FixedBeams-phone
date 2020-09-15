package com.ferrifrancis.fixedbeams_phone.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ferrifrancis.fixedbeams_phone.CATEGORY_PRODUCTS
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.URL_BACKEND
import com.ferrifrancis.fixedbeams_phone.adapters.CategoryAdapter
import com.ferrifrancis.fixedbeams_phone.adapters.ProductAdapter
import com.ferrifrancis.fixedbeams_phone.data.category.CategoriesListClass
import com.ferrifrancis.fixedbeams_phone.data.product.ProductModelClass
import com.ferrifrancis.fixedbeams_phone.data.product.ProductsListClass
import com.ferrifrancis.fixedbeams_phone.services.Network
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_products.*

private const val ID_CATEGORY = "idCategory"

class ProductsFragment : Fragment() {

    private var  idCategory: Int = -1
    lateinit var listener: ProductListener
    var products = arrayListOf<ProductModelClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idCategory = it.getInt(ID_CATEGORY)
        }
        if (Network.networkExists( context as AppCompatActivity)){
            if(idCategory != -1){
                requestHttpProducts(idCategory)
            }
        } else {
            Toast.makeText(context, "No internet conection", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false)
    }


    interface ProductListener{
        fun getProductSelected(idProduct: Int){}
    }

    companion object {
        @JvmStatic
        fun newInstance(idCategory: Int) =
            ProductsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID_CATEGORY, idCategory)
                }
            }
    }

    private fun requestHttpProducts(idCategory: Int){
        val url: String = "$URL_BACKEND/$CATEGORY_PRODUCTS/" + idCategory.toString()
        val queue= Volley.newRequestQueue(context)
        val request = StringRequest(Request.Method.GET, url, Response.Listener<String>{
                response ->
            try {
                val gson = Gson()
                val productsJSON = "{\"products\": ${response}}"
                val res = gson.fromJson(productsJSON, ProductsListClass::class.java)
                products = res.products
                listView.adapter = ProductAdapter(this, products)
            } catch (e: Exception){
                Log.d("Error", e.toString())
            }
        }, Response.ErrorListener { error ->  Log.d("Error", error.toString()) })
        queue.add(request)
    }
}

/*
LUIS VERSION
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
 */