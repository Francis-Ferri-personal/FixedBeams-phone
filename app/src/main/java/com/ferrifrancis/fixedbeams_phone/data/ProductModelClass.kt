package com.ferrifrancis.fixedbeams_phone.data

data class ProductModelClass(
    var productId: Int,
    var productName: String,
    var productDetail: String,
    var productPrice: Double,
    var productQuantity: Int? = 1
)