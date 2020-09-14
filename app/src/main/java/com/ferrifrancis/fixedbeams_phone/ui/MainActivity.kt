package com.ferrifrancis.fixedbeams_phone.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.dialogs.SearchDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CategoriesFragment.CategoryListener, SearchDialog.SearchDialogListener {
    val fragmentManager: FragmentManager = supportFragmentManager
    lateinit var buttonActualMenu: ImageButton;
    var idDomain: Int = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Cargar las categorias iniciales
        buttonActualMenu = imageButton_tools
        loadCategories()
        // Cargar el fragento incial
        createCategoryFragment(idDomain,true)

        // Inicializar clic listeners shopping cart
        imageButton_cart.setOnClickListener { goToShoppingCartActivity() }
        button_viewCart.setOnClickListener { goToShoppingCartActivity() }

        // Inicializar el boton de busqueda
        imageButton_search.setOnClickListener {
            openSearchDialog()
        }

        // Inicializarr el boton de cerrar sesion
        imageButton_user.setOnClickListener {
            createDialogCloseSession()
        }


        // Inicializar los click listeners Menu
        imageButton_tools.setOnClickListener { changeCategories(imageButton_tools) }
        imageButton_material.setOnClickListener { changeCategories(imageButton_material) }
        imageButton_equipment.setOnClickListener { changeCategories(imageButton_equipment) }
        imageButton_vehicles.setOnClickListener { changeCategories(imageButton_vehicles) }
        imageButton_teams.setOnClickListener { changeCategories(imageButton_teams) }

    }

    fun changeCategories(selectedButton: ImageButton){
        if (selectedButton != buttonActualMenu){
            buttonActualMenu.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.transparent));
            buttonActualMenu = selectedButton
            buttonActualMenu.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary));
            loadCategories()
        }
    }

    fun loadCategories(){
        when (buttonActualMenu) {
            imageButton_tools -> idDomain = 5
            imageButton_material -> idDomain = 3
            imageButton_equipment -> idDomain = 2
            imageButton_vehicles -> idDomain = 1
            imageButton_teams -> idDomain = 4
        }
        createCategoryFragment(idDomain, true)
    }

    fun createCategoryFragment(idDomain: Int, replace: Boolean){
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val categoryFragment = CategoriesFragment.newInstance(idDomain)
        if (replace){
            while(fragmentManager.backStackEntryCount > 0) { fragmentManager.popBackStackImmediate()}
            fragmentTransaction.replace(container.id, categoryFragment)
        } else {
            fragmentTransaction.add(container.id, categoryFragment)
        }
        fragmentTransaction.commit()
    }

    override fun getCategorySelected(idCategory: Int) {
        super.getCategorySelected(idCategory)
        Toast.makeText(this, idCategory.toString(), Toast.LENGTH_LONG).show()
        // TODO:
        //createProductFragment(idCategory)
    }

    fun createProductFragment(idCategory: Int){
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        // TODO: Change to a new one
        val productFragment = CategoriesFragment.newInstance(idCategory)
        fragmentTransaction.replace(container.id, productFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    /*
    override fun getProductSelected(idProduct: String) {
        super.getProductSelected(idProduct)
        Toast.makeText(this, idProduct, Toast.LENGTH_SHORT).show()
    }

     */

    override fun getProductName(productName: String) {
        super.getProductName(productName)
        Toast.makeText(this, productName, Toast.LENGTH_LONG).show()
        // TODO: Send to PProduct Activity
    }
    fun goToShoppingCartActivity(){
        val explicitIntent = Intent(this,
            ShoppingCartActivity::class.java)
        startActivity(explicitIntent)
    }

    fun createDialogCloseSession(){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Close session")
        dialogBuilder.setMessage("Are you sure to close your session ${textView_userName.text}?")
        dialogBuilder.setIcon(R.drawable.ic_userlogin)
        dialogBuilder.setPositiveButton("Close", DialogInterface.OnClickListener { dialog, which ->
            closeUserSession()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            //pass
        })
        dialogBuilder.create().show()
    }

    private fun openSearchDialog(){
        val searchDialog = SearchDialog()
        searchDialog.show(supportFragmentManager, "Search Dialog")
    }

    private fun closeUserSession(){
        // TODO: Delete user Data fron SharedPreferences
    }
}
