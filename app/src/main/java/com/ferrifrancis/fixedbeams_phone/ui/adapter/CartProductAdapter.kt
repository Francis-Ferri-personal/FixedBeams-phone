package com.ferrifrancis.fixedbeams_phone.ui.adapter

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.data.ProductModelClass
import kotlinx.android.synthetic.main.layout_shopping_cart_list_item.view.*

class CartProductAdapter (private val context: Activity, private val products: ArrayList<ProductModelClass>): BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.layout_shopping_cart_list_item, null, true)
        val textViewProductName = rowView.findViewById<TextView>(R.id.textView_productName)
        val textViewProductPrice = rowView.findViewById<TextView>(R.id.textView_price)
        val textViewDiscount = rowView.findViewById<TextView>(R.id.textView_discount)

        textViewProductName.text = "${products[position].productName}"
        textViewProductPrice.text = "$ ${products[position].productPrice}"
        textViewDiscount.text = "Discount: 0%"
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