package com.ferrifrancis.fixedbeams_phone.ui.adapter

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.data.product.ProductModelClass
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

        Glide.with(context).load(products[position].srcImage)
            .centerInside()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageViewProduct)

        textViewProductName.text = "${products[position].name}"
        textViewProductPrice.text = "$ ${products[position].price}"
        textViewQuantity.text = products[position].quantity.toString()
        textViewDiscount.text = "Discount: 0%"
        addButtonListeners(rowView, position)
        return rowView
    }

    fun addButtonListeners(itemView: View, position: Int){
        var tempCounter = itemView.textView_quantity.text.toString().toInt()
        itemView.plus_button.setOnClickListener {
            tempCounter += 1
            itemView.textView_quantity.text = tempCounter.toString()
            products[position].quantity = tempCounter
            SharedPreferencesManager.saveProduct(products[position], itemView.context, "CarritoAdaptador")
        }
        itemView.minus_button.setOnClickListener {
            tempCounter -= 1
            itemView.textView_quantity.text = tempCounter.toString()
            products[position].quantity = tempCounter
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