package com.ferrifrancis.fixedbeams_phone.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.data.CategoryModelClass
import kotlinx.android.synthetic.main.layout_categories.view.*

class CategoryAdapter (var context: Context, items:ArrayList<CategoryModelClass>): BaseAdapter(){
    var items:ArrayList<CategoryModelClass>?=null
    init {
        this.items = items
    }
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var holder:ViewHolder?=null
        var vista: View?= p1
        if (vista == null){
            vista = LayoutInflater.from(context).inflate(R.layout.layout_categories, null)
            holder = ViewHolder(vista)
            vista.tag = holder
        }else{
            holder = vista.tag as? ViewHolder
        }
        val item = getItem(p0) as CategoryModelClass
        holder?.descripcion?.text = item.descrpcion
        holder?.imagen?.setImageResource(item.imagen)
        return vista!!

    }

    override fun getItem(p0: Int): Any {
        return items?.get(p0)!!
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return items?.count()!!
    }
    private class ViewHolder(vista: View){
        var descripcion: TextView?= null
        var imagen: ImageView? = null
        init {
            this.descripcion = vista.tvDescripcionCategorie
            this.imagen = vista.imageViewCategory
        }
    }
}