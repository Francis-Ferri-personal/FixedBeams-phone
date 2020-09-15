package com.ferrifrancis.fixedbeams_phone.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.data.product.ProductModelClass

class ProductAdapter(private val context: Fragment, private val products: ArrayList<ProductModelClass>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.layoutInflater
        val row = inflater.inflate(R.layout.product_item,null, true)
        val categoryImageView = row.findViewById<ImageView>(R.id.imageView_product)
        Glide.with(context).load(products[position].srcImage)
            .centerInside()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(categoryImageView)
        val productTextViewName = row.findViewById<TextView>(R.id.textView_productName)
        productTextViewName.text = "${products[position].name}".capitalize()
        val productTextViewDescription = row.findViewById<TextView>(R.id.textView_productDescription)
        productTextViewDescription.text = products[position].summary.substring(0,80)
        val productTextViewPrice = row.findViewById<TextView>(R.id.textView_productPrice)
        productTextViewPrice.text = "$ " + String.format("%.2f", products[position].price)
        return row
    }

    override fun getItem(position: Int): Any {
        return products[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return products.size
    }
}