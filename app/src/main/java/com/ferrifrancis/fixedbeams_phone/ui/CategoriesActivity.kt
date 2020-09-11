package com.ferrifrancis.fixedbeams_phone.ui

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.data.CategoryModelClass
import com.ferrifrancis.fixedbeams_phone.ui.adapter.CategoryAdapter
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.android.synthetic.main.activity_products.*
import kotlinx.android.synthetic.main.activity_categories.button_viewCart as button_viewCart1
import kotlinx.android.synthetic.main.activity_products.imageButton_cart as imageButton_cart1

class CategoriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        val userData = intent.getStringExtra("UserData")
        textViewUserName.text = userData
        var categories = ArrayList<CategoryModelClass>()

        categories.add(CategoryModelClass("Herramientas",R.drawable.ic_drill))
        categories.add(CategoryModelClass("Seguridad",R.drawable.ic_ingeniero))
        categories.add(CategoryModelClass("Construcción",R.drawable.ic_pared_de_ladrillo))
        categories.add(CategoryModelClass("Cerrajeria",R.drawable.ic_cerrajero))
        categories.add(CategoryModelClass("Maquinaria",R.drawable.ic_retroexcavadora))
        categories.add(CategoryModelClass("Pintura",R.drawable.ic_rodillo))
        var adapter = CategoryAdapter(this,categories)
        gridViewCategories.adapter =adapter
        button_viewCart.setOnClickListener {
            goToShoppingCartActivity()
        }
        gridViewCategories.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
           goToProductsActivity()
        }
        imageButton_cart.setOnClickListener {
            goToShoppingCartActivity()
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