package com.ferrifrancis.fixedbeams_phone.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ferrifrancis.fixedbeams_phone.DOMAIN_CATEGORIES
import com.ferrifrancis.fixedbeams_phone.services.Network
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.URL_BACKEND
import com.ferrifrancis.fixedbeams_phone.adapters.CategoryAdapter
import com.ferrifrancis.fixedbeams_phone.data.category.CategoriesListClass
import com.ferrifrancis.fixedbeams_phone.data.category.CategoryModelClass
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_categories.view.*

private const val ID_DOMAIN = "idDomain"

class CategoriesFragment : Fragment() {

    private var  idDomain: Int = -1
    lateinit var listener: CategoryListener
    var categories = arrayListOf<CategoryModelClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idDomain = it.getInt(ID_DOMAIN)
        }
        if (Network.networkExists(
                context as AppCompatActivity
            )
        ){
            if(idDomain != -1){
                requestHttpCategories(idDomain)
            }
        } else {
            Toast.makeText(context, "No internet conection", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View =  inflater.inflate(R.layout.fragment_categories, container, false)
        view.gridView.setOnItemClickListener { parent, view, position, id ->
            listener.getCategorySelected(categories[position].id)
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as CategoryListener
        } catch (e: java.lang.ClassCastException){
            throw ClassCastException(context.toString() + "Please, implement the interface")
        }
    }

    interface CategoryListener{
        fun getCategorySelected(idCategory: Int){}
    }

    companion object {
        @JvmStatic
        fun newInstance(idDomain: Int) =
            CategoriesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID_DOMAIN, idDomain)
                }
            }
    }

    private fun requestHttpCategories(idDomain: Int){
        val url: String = "$URL_BACKEND/$DOMAIN_CATEGORIES/" + idDomain.toString()
        val queue= Volley.newRequestQueue(context)
        val request = StringRequest(Request.Method.GET, url, Response.Listener<String>{
            response ->
            try {
                val gson = Gson()
                val categoriesJSON = "{\"categories\": ${response}}"
                val res = gson.fromJson(categoriesJSON, CategoriesListClass::class.java)
                categories = res.categories
                gridView.adapter = CategoryAdapter(this, categories)
            } catch (e: Exception){
                Log.d("Error", e.toString())
            }
        }, Response.ErrorListener { error ->  Log.d("Error", error.toString()) })
        queue.add(request)
    }
}