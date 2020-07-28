package com.ferrifrancis.fixedbeams_phone

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ProductAdapter (private val context: Activity, private val products: ArrayList<ProductModelClass>): BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.product_list_item, null, true)

        val textViewProductName = rowView.findViewById<TextView>(R.id.textView_productName)
        val textViewProductDetail = rowView.findViewById<TextView>(R.id.textView_productDescription)
        val textViewProductPrice = rowView.findViewById<TextView>(R.id.textView_productPrice)

        textViewProductName.text = "Product: ${products[position].productId}"
        textViewProductDetail.text = "Description: ${products[position].productDetail}"
        textViewProductPrice.text = "Price: ${products[position].productPrice}"

        return rowView
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