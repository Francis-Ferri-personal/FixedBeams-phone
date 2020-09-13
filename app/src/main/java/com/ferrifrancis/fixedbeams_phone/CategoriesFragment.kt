package com.ferrifrancis.fixedbeams_phone

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
import kotlinx.android.synthetic.main.fragment_categories.view.*

private const val ID_DOMAIN = "idDomain"

class CategoriesFragment : Fragment() {

    private var  idDomain: Int = -1
    lateinit var listener: CategoryListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idDomain = it.getInt(ID_DOMAIN)
        }
        if (Network.networkExists(context as AppCompatActivity)){
            if(idDomain != null){
                // TODO: Traer las categorias
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
        view.boton_prueba.setOnClickListener {
            Toast.makeText(context, idDomain.toString(), Toast.LENGTH_LONG).show()
            listener.getCategorySelected(idDomain)
        }
        // TODO: Funcionalidad
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
        val url: String = "${URL_BACKEND}/${DOMAIN_CATEGORIES}/" + idDomain.toString()
        Toast.makeText(context,url,Toast.LENGTH_LONG).show()
        val queue= Volley.newRequestQueue(context)
        val request = StringRequest(Request.Method.GET, url, Response.Listener<String>{
            response ->
            try {
                Log.d("FixedBeams", response)
                Toast.makeText(context,response,Toast.LENGTH_LONG).show()
            } catch (e: Exception){
                Log.d("FixedBeams", e.toString())
            }
        }, Response.ErrorListener {  })
        queue.add(request)
    }
}