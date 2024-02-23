package com.oqq.pocketrecipe.data.model.recipe

data class Meal(val image:Int, val name:String, var recipe:List<Recipe>?) {
}