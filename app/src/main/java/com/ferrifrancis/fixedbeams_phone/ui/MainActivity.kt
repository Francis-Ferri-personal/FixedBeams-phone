package com.ferrifrancis.fixedbeams_phone.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ferrifrancis.fixedbeams_phone.*
import com.ferrifrancis.fixedbeams_phone.dialogs.SearchDialog
import com.ferrifrancis.fixedbeams_phone.util.SharedPreferencesManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_shopping_cart_list_item.*

class MainActivity : AppCompatActivity(), CategoriesFragment.CategoryListener, SearchDialog.SearchDialogListener, ProductsFragment.ProductListener {

    val fragmentManager: FragmentManager = supportFragmentManager
    lateinit var fragmentTransaction: FragmentTransaction
    private lateinit var sharedPreferences : SharedPreferences

    lateinit var buttonActualMenu: ImageButton;
    var idDomain: Int = 4

    // User data
    var idUser: Int = 0
    var userName: String ="Default"
    var money: Double = 0.0
    var srcImage: String? = ""

    override fun onResume() {
        super.onResume()
        updateCostSummaryBar()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeSharedPreferences()
        // Obtain user data

        idUser = intent.getIntExtra(ID, idUser)
        money = intent.getDoubleExtra(MONEY, money)
        userName = intent.getStringExtra(USER_NAME)
        srcImage = intent.getStringExtra(SRC_IMAGE)
        textView_userName.text = userName
        textView_money.text = "$ " + String.format("%.2f", money)

        if (!srcImage.equals("default")){
            loadProfileImage()
        }
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

        updateCostSummaryBar()
    }

    fun updateCostSummaryBar(){
        var resumencosto = SharedPreferencesManager.returnProductsTotal(this)
        textView_ItemNumber.text = resumencosto["Cantidad"].toString()

        textView_priceAccumulated.text = "$ " + resumencosto["Costo"].toString()
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
        fragmentTransaction = fragmentManager.beginTransaction()
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
        fragmentTransaction = fragmentManager.beginTransaction()
        val productFragment = ProductsFragment.newInstance(idCategory)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(container.id, productFragment)
        fragmentTransaction.commit()
    }


    override fun getProductSelected(idProduct: Int) {
        super.getProductSelected(idProduct)
        val intentToDetail = Intent(this, DetailsActivity::class.java)
        intentToDetail.putExtra(ID_PRODUCT, idProduct)
        startActivity(intentToDetail)
        //finish()
    }


    /*
    override fun getProductName(productName: String) {
        super.getProductName(productName)
        Toast.makeText(this, productName, Toast.LENGTH_LONG).show()
        // TODO: Send to PProduct Activity
    }

     */


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
            cleanUserData()
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

    private fun  initializeSharedPreferences(){
        val masterKey: MasterKey = MasterKey.Builder(this,  MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        sharedPreferences = EncryptedSharedPreferences.create(this,
            SECRET_FILENAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
    }

    private fun cleanUserData(){
        sharedPreferences.edit()
            .putInt(ID, 0)
            .putString(USER_NAME, "")
            .putFloat(MONEY, 0F)
            .putString(SRC_IMAGE, "")
            .apply()
    }

    private fun loadProfileImage(){
        Glide.with(this).load(srcImage)
            .centerInside()
            .circleCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageButton_user)
    }
}
