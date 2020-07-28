package com.ferrifrancis.fixedbeams_phone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_products.*

class ProductsActivity : AppCompatActivity() {
    var products = arrayListOf<ProductModelClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        // Productos Quemados TODO: Reemplazar cn productos de Base de datos
        products.add(ProductModelClass(1, "Producto 1", "Detalle 1", 20.00))
        products.add(ProductModelClass(2, "Producto 2", "Detalle 2", 20.00))
        products.add(ProductModelClass(3, "Producto 3", "Detalle 3", 20.00))
        products.add(ProductModelClass(4, "Producto 4", "Detalle 4", 20.00))

        listView_products.adapter = ProductAdapter(this, products)
    }
}