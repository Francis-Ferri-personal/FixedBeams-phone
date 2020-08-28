package com.ferrifrancis.fixedbeams_phone.data

data class ProductModelClass(
    var productId: String,
    var productName: String,
    var productDescription: String,
    var productPrice: Long,
    var productQuantity: Int? = 1,
    var productImageURL: String?,
    var productManufacturer: String?,
    var productCategories: ArrayList<String>?
)