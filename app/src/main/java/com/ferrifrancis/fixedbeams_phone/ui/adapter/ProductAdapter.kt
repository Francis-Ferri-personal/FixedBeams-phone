package com.ferrifrancis.fixedbeams_phone.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.common.DETAIL_MAX_CHARS
import com.ferrifrancis.fixedbeams_phone.data.ProductModelClass
import com.ferrifrancis.fixedbeams_phone.ui.DetallesActivity
import com.ferrifrancis.fixedbeams_phone.ui.ShoppingCartActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ProductAdapter (private val context: Activity, private val products: ArrayList<ProductModelClass>): BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.layout_product_list_item, null, true)

        val textViewProductName = rowView.findViewById<TextView>(R.id.textView_productName)
        val textViewProductDetail = rowView.findViewById<TextView>(R.id.textView_productDescription)
        val textViewProductPrice = rowView.findViewById<TextView>(R.id.textView_productPrice)
        val buttonAddProduct = rowView.findViewById<Button>(R.id.button_addProduct)
        val imageViewProduct = rowView.findViewById<ImageView>(R.id.imageView_product)

        Picasso.get().load(products[position].productImageURL).fit().placeholder(R.drawable.spinner)
            .into(imageViewProduct, object : Callback {
                override fun onSuccess() {
                    //
                }

                override fun onError(e: Exception) {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show()
                }
            })

        textViewProductName.text = "Product: ${products[position].productName}"
        if(products[position].productDescription.length> DETAIL_MAX_CHARS){
            textViewProductDetail.text = "Description: ${products[position].productDescription.substring(0, 50)}"
        }else{
            textViewProductDetail.text = "Description: ${products[position].productDescription}"
        }

        textViewProductPrice.text = "Price: ${products[position].productPrice}"
        buttonAddProduct.setOnClickListener {
            goToShoppingCartActivity(products[position])
        }
        return rowView
    }
    fun goToShoppingCartActivity(product: ProductModelClass){
        val intentExplicito = Intent(this.context,
            DetallesActivity::class.java).apply {
            putExtra("ProductDocumentID",product.productId)
        }
        this.context.startActivity(intentExplicito)
    }
    override fun getItem(position: Int): Any {
        return products.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return products.size
    }

}