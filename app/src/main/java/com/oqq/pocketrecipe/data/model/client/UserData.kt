package com.oqq.pocketrecipe.data.model.client

import com.oqq.pocketrecipe.data.model.recipe.Recipe

data class UserData (val username:String, val email:String, val password:String,
                     val firstName:String, val lastName:String, var recipes:List<Recipe>?
)