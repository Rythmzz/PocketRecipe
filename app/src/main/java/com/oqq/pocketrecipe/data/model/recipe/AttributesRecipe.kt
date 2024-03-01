package com.oqq.pocketrecipe.data.model.recipe

import com.oqq.pocketrecipe.data.model.client.UserAttribute
import com.oqq.pocketrecipe.data.model.client.UserRequest

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
val process:List<Process>?,
val userslike:UserRequest?):java.io.Serializable {
}