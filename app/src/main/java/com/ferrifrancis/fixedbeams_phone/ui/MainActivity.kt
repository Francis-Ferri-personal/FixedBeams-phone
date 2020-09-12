package com.ferrifrancis.fixedbeams_phone.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ferrifrancis.fixedbeams_phone.CategoriesFragment
import com.ferrifrancis.fixedbeams_phone.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CategoriesFragment.CategoryListener {
    val fragmentManager: FragmentManager = supportFragmentManager
    lateinit var buttonActualMenu: ImageButton;
    var idDomain: Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cargar las categorias iniciales
        buttonActualMenu = imageButton_tools
        loadCategories()
        // Cargar el fragento incial
        createCategoryFragment(idDomain,false)

        // Inicializar clic listeners shopping cart
        imageButton_cart.setOnClickListener { goToShoppingCartActivity() }
        button_viewCart.setOnClickListener { goToShoppingCartActivity() }

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
            imageButton_tools -> idDomain = 4
            imageButton_material -> idDomain = 3
            imageButton_equipment -> idDomain = 2
            imageButton_vehicles -> idDomain = 1
            imageButton_teams -> idDomain = 5
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
        createProductFragment(idCategory)
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

    fun goToShoppingCartActivity(){
        val explicitIntent = Intent(this,
            ShoppingCartActivity::class.java)
        startActivity(explicitIntent)
    }
}
