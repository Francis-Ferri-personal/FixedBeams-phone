package com.ferrifrancis.fixedbeams_phone.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.data.category.CategoryModelClass


// class CategoryAdapter(private val context: Activity, private val contactos: ArrayList<CategoryModelClass>): BaseAdapter() {
class CategoryAdapter(private val context: Fragment, private val categories: ArrayList<CategoryModelClass>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.layoutInflater
        val cellView = inflater.inflate(R.layout.category_item,null, true)

        val categoryImageView = cellView.findViewById<ImageView>(R.id.imageView_category)
        Glide.with(context).load(categories[position].srcImage)
            .centerInside()
            .into(categoryImageView)
        val categoryTextViewName = cellView.findViewById<TextView>(R.id.textView_categoryName)

        // TODO: La imagen con Glide
        categoryTextViewName.text = "${categories[position].name}"

        return cellView
    }

    override fun getItem(position: Int): Any {
        return categories[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return categories.size
    }
}