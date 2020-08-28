package com.ferrifrancis.fixedbeams_phone.ui.adapter

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.data.ProductModelClass
import com.ferrifrancis.fixedbeams_phone.util.SharedPreferencesManager
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_shopping_cart_list_item.view.*

class CartProductAdapter (private val context: Activity, private val products: ArrayList<ProductModelClass>): BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.layout_shopping_cart_list_item, null, true)
        val textViewProductName = rowView.findViewById<TextView>(R.id.textView_productName)
        val textViewProductPrice = rowView.findViewById<TextView>(R.id.textView_price)
        val textViewDiscount = rowView.findViewById<TextView>(R.id.textView_discount)
        val imageViewProduct = rowView.findViewById<ImageView>(R.id.imageViewProductImage)
        val textViewQuantity = rowView.findViewById<TextView>(R.id.textView_quantity)

        Picasso.get().load(products[position].productImageURL).fit().placeholder(R.drawable.spinner)
            .into(imageViewProduct, object : Callback {
                override fun onSuccess() {
                    //
                }

                override fun onError(e: Exception) {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show()
                }
            })

        textViewProductName.text = "${products[position].productName}"
        textViewProductPrice.text = "$ ${products[position].productPrice}"
        textViewQuantity.text = products[position].productQuantity.toString()
        textViewDiscount.text = "Discount: 0%"
        addButtonListeners(rowView, position)
        return rowView
    }

    fun addButtonListeners(itemView: View, position: Int){
        var tempCounter = itemView.textView_quantity.text.toString().toInt()
        itemView.plus_button.setOnClickListener {
            tempCounter += 1
            itemView.textView_quantity.text = tempCounter.toString()
            products[position].productQuantity = tempCounter
            SharedPreferencesManager.saveProduct(products[position], itemView.context, "CarritoAdaptador")
        }
        itemView.minus_button.setOnClickListener {
            tempCounter -= 1
            itemView.textView_quantity.text = tempCounter.toString()
            products[position].productQuantity = tempCounter
            SharedPreferencesManager.saveProduct(products[position], itemView.context, "CarritoAdaptador")
        }
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