package com.oqq.pocketrecipe.data.model.client

import com.oqq.pocketrecipe.data.model.recipe.AttributesRecipe

data class UserInfo( val id: Int,
                     val username: String,
                     val email: String,
                     val provider: String,
                     val confirmed: Boolean,
                     val blocked: Boolean,
                     val createdAt: String,
                     val updatedAt: String,
                     val firstName: String,
                     val lastName: String,
                     val idMode: Int?,
                     val imgAvatar: String?,
                     val phone:String?,
                     val address:String,
                     val gender:Int,
                     var recipes: ArrayList<AttributesRecipe> ) {
}