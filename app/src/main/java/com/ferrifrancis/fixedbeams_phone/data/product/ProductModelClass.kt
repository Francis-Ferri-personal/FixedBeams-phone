package com.ferrifrancis.fixedbeams_phone.data.product

data class ProductModelClass (
    var id: Int,
    var name: String,
    var summary: String,
    var price: Double,
    var stock: Int,
    var srcImage: String
)

/*
LUIS VERSION

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

 */