package com.ferrifrancis.fixedbeams_phone.data.category

data class CategoryModelClass (
    var id: Int,
    var name: String,
    var description: String,
    var srcImage: String
)


/*
LUIS VERSION
class CategoryModelClass(descrpcion:String, imagen:Int) {
    var descrpcion:String = ""
    var imagen:Int = 0
    init {
        this.descrpcion = descrpcion
        this.imagen = imagen
    }
}
 */