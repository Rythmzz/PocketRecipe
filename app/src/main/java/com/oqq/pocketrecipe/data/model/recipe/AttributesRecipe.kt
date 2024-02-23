package com.oqq.pocketrecipe.data.model.recipe

data class AttributesRecipe(
    var id:Int?,
    val name:String?,
val brief:String?,
val description:String?,
val duration:Int?,
val kcal:Int?,
val count:Int?,
var view:Int?, val meal:String?,
val imgUrl:String?,
val ingredients:List<String>?,
val process:List<Process>?):java.io.Serializable {
}