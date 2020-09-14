package com.ferrifrancis.fixedbeams_phone.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment
import com.ferrifrancis.fixedbeams_phone.R

class SearchDialog: AppCompatDialogFragment() {
    private lateinit var editTextSearch: EditText
    private lateinit var listener: SearchDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder =  AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.dialog_search, null)
        dialogBuilder.setView(view)
        dialogBuilder.setTitle("Search")
        dialogBuilder.setIcon(R.drawable.ic_buscar)
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            //pass
        })
        dialogBuilder.setPositiveButton("Search", DialogInterface.OnClickListener { dialog, which ->
            listener.getProductName(editTextSearch.text.toString())
        })
        if (view != null) {
            editTextSearch = view.findViewById(R.id.editText_searchProduct)
        }
        return   dialogBuilder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as SearchDialogListener
        } catch (e: ClassCastException){
            throw ClassCastException(context.toString() + "must implement the interface")
        }

    }

    interface SearchDialogListener{
        fun getProductName(productName: String){}
    }
}