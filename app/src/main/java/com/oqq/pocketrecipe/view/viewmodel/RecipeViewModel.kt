package com.oqq.pocketrecipe.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oqq.pocketrecipe.data.model.recipe.RecipeRequest
import com.oqq.pocketrecipe.data.model.recipe.RecipeResponse
import com.oqq.pocketrecipe.repo.BaseResponse
import com.oqq.pocketrecipe.repo.Repository
import com.oqq.pocketrecipe.repo.ResponseError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import org.json.JSONArray

class RecipeViewModel(private val repository: Repository) :ViewModel(){
    private val _success = MutableSharedFlow<Any>()
    val success:SharedFlow<Any> get() = _success

    private val _success2 = MutableSharedFlow<Any>()
    val success2:SharedFlow<Any> get() = _success2

    private val _loading = MutableSharedFlow<Any>()
    val loading:SharedFlow<Any> get() = _loading

    var currentListRecipe:RecipeResponse? = null

    var countMainRecipe:Int = 0
    var countSubRecipe:Int = 0
    var countOther:Int = 0

    var currentUrlImageRecipe:String = ""

    var currentLike:Int = 0

    fun createRecipe(recipeRequest: RecipeRequest){
        viewModelScope.launch {
            _loading.emit(true)
            when(val result = repository.createRecipe(recipeRequest)){
                is BaseResponse.Success -> {
                    println("TAO RECIPE THANH CONG !")
                    _success2.emit(true)
                }
                is BaseResponse.Error -> {
                    _success2.emit((result.exception as ResponseError).msg)
                    _loading.emit(false)
                }
                else -> {}
            }
        }
    }

    fun uploadImageRecipe(image:MultipartBody.Part){
        viewModelScope.launch {
            _loading.emit(true)
            when(val result = repository.uploadImage(image)){
                is BaseResponse.Success -> {
                    val responseBody: String = result.data.string()
                    val jsonArray = JSONArray(responseBody)
                    val jsonObject = jsonArray.getJSONObject(0)
                    currentUrlImageRecipe = (jsonObject.getString("url")).substring(1)
                    println("URL IMAGE: $currentUrlImageRecipe")
                    _success.emit(true)
                }
                is BaseResponse.Error -> {
                    _success.emit((result.exception as ResponseError).msg)
                    _loading.emit(false)
                }
                else -> {}
            }

        }
    }

    fun updateRecipe(id:Int, recipeRequest: RecipeRequest){
        viewModelScope.launch {
            _loading.emit(true)

            when(val result = repository.updateRecipe(id,recipeRequest)){
                is BaseResponse.Success -> {
                    _success.emit(true)
                }
                is BaseResponse.Error -> {
                    _success.emit((result.exception as ResponseError).msg)
                    _loading.emit(false)
                }
                else -> {}
            }
        }
    }

    fun getListNotContainRecipeMeal(meal:String){
        viewModelScope.launch {
            _loading.emit(true)

            when (val result = repository.getListNotContainRecipeMeal(meal)){
                is BaseResponse.Success -> {
                    result.data.let {
                        currentListRecipe = it
                        countOther = it.data.size
                    }
                    _success.emit(true)
                    _loading.emit(false)
                }
                is BaseResponse.Error -> {
                    _success.emit((result.exception as ResponseError).msg)
                    _loading.emit(false)
                }
                else -> {}
            }
        }
    }



    fun getListRecipeMeal(meal:String){
        viewModelScope.launch {
            _loading.emit(true)

            when (val result = repository.getListRecipeMeal(meal)){
                is BaseResponse.Success -> {
                    result.data.let {
                        currentListRecipe = it
                        if (meal == "Chính") countMainRecipe = it.data.size
                        else if (meal == "Phụ") countSubRecipe = it.data.size
                    }
                    _success.emit(true)
                    _loading.emit(false)
                }
                is BaseResponse.Error -> {
                    _success.emit((result.exception as ResponseError).msg)
                    _loading.emit(false)
                }
                else -> {}
            }
        }
    }

    fun getSpecificRecipe(id:Int){
        viewModelScope.launch {
            _loading.emit(true)

            when(val result = repository.getSpecificRecipe(id)){
                is BaseResponse.Success -> {
                    result.data.let {

                        currentLike = it.data.attributes.userslike!!.data.size
                    }
                    _success2.emit(true)
                    _loading.emit(false)
                }
                is BaseResponse.Error ->{
                    _success2.emit((result.exception as ResponseError).msg)
                    _loading.emit(false)
                }
                else -> {}
            }
        }
    }

    fun getListRecipe(start:Int, limit:Int){
        viewModelScope.launch {
            _loading.emit(true)

            when(val result = repository.getListRecipe(start,limit)){
                is BaseResponse.Success -> {
                    result.data.let {
                        currentListRecipe = it

                        println("CURRENTLIST:${currentListRecipe}")
                    }
                    _success.emit(true)
                    _loading.emit(false)
                }
                is BaseResponse.Error ->{
                    _success.emit((result.exception as ResponseError).msg)
                    _loading.emit(false)
                }
                else -> {}
            }
        }
    }
}