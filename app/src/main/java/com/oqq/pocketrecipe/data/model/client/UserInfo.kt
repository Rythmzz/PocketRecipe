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
                     var firstName: String,
                     var lastName: String,
                     val idMode: Int?,
                     var imgAvatar: String?,
                     var phone:String?,
                     var address:String,
                     var gender:Int,
                     var recipes: ArrayList<AttributesRecipe>,
                    var likes:ArrayList<AttributesRecipe>)