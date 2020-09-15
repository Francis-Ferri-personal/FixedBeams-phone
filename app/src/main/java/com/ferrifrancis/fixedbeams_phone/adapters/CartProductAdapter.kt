package com.ferrifrancis.fixedbeams_phone.adapters

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.data.product.ProductModelClass
import kotlinx.android.synthetic.main.layout_shopping_cart_list_item.view.*
import kotlin.collections.hashMapOf

class CartProductAdapter (private val context: Activity, private val products: ArrayList<ProductModelClass>): BaseAdapter(){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.layout_shopping_cart_list_item, null, true)
        val textViewProductName = rowView.findViewById<TextView>(R.id.textView_productName)
        val textViewProductPrice = rowView.findViewById<TextView>(R.id.textView_price)
        val textViewCantidad = rowView.findViewById<TextView>(R.id.textView_quantity)
        val textViewDiscount = rowView.findViewById<TextView>(R.id.textView_discount)

        textViewProductName.text = "${products[position].name}"
        textViewProductPrice.text = "$ ${products[position].price}"
        textViewDiscount.text = "Discount: 0%"
        //textViewCantidad.text = "${products[position].}"

        addButtonListeners(rowView)


        return rowView
    }

    fun addButtonListeners(itemView: View){
        var tempCounter = itemView.textView_quantity.text.toString().toInt()
        itemView.plus_button.setOnClickListener {
            tempCounter += 1
            itemView.textView_quantity.text = tempCounter.toString()
        }
        itemView.minus_button.setOnClickListener {
            tempCounter -= 1
            itemView.textView_quantity.text = tempCounter.toString()
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