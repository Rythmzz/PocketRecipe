package com.oqq.pocketrecipe.repo

import com.oqq.pocketrecipe.data.model.client.*
import com.oqq.pocketrecipe.data.model.recipe.Recipe
import com.oqq.pocketrecipe.data.model.recipe.RecipeInfo
import com.oqq.pocketrecipe.data.model.recipe.RecipeRequest
import com.oqq.pocketrecipe.data.model.recipe.RecipeResponse
import com.oqq.pocketrecipe.data.remote.ApiService
import okhttp3.MultipartBody
import okhttp3.ResponseBody

class Repository(private val api:ApiService) {

    suspend fun createRecipe(recipeRequest: RecipeRequest) : BaseResponse<ResponseBody>{
        return try{
            BaseResponse.Success(api.createRecipe(recipeRequest))
        }
        catch (ex:Exception){
            return BaseResponse.Error(ResponseError(101,ex.message.toString()))
        }
    }
    suspend fun uploadImage(image:MultipartBody.Part) : BaseResponse<ResponseBody>{
        return try{
            BaseResponse.Success(api.uploadImage(image))
        }
        catch (ex:Exception){
            return BaseResponse.Error(ResponseError(101,ex.message.toString()))
        }
    }
    suspend fun updateRecipe(id:Int, recipeRequest: RecipeRequest) : BaseResponse<RecipeRequest>{
        return try{
            BaseResponse.Success(api.updateRecipe(id,recipeRequest))
        }
        catch (ex:Exception){
            return BaseResponse.Error(ResponseError(101,ex.message.toString()))
        }
    }

    suspend fun updateInfoUser(id:Int, userInfo:UserInfo ) : BaseResponse<UserInfo>{
        return  try{
            BaseResponse.Success(api.updateInfoUser(id,userInfo))
        }
        catch (ex:Exception){
            return BaseResponse.Error(ResponseError(101,ex.message.toString()))
        }
    }
    suspend fun getInfoUser(id:Int) : BaseResponse<UserInfo>{
        return try{
            BaseResponse.Success(api.getInfoUser(id))
        }
        catch (ex:Exception){
            return BaseResponse.Error(ResponseError(101,ex.message.toString()))
        }
    }

    suspend fun getListNotContainRecipeMeal(meal:String):BaseResponse<RecipeResponse>{
        return try{
            BaseResponse.Success(api.getListNotContainRecipeMeal(meal))
        }
        catch (ex:Exception){
            return BaseResponse.Error(ResponseError(101,ex.message.toString()))
        }
    }

    suspend fun getListRecipeMeal(meal:String):BaseResponse<RecipeResponse>{
        return try{
            BaseResponse.Success(api.getListRecipeMeal(meal))
        }
        catch (ex:Exception){
            return BaseResponse.Error(ResponseError(101,ex.message.toString()))
        }
    }

    suspend fun getSpecificRecipe(id:Int):BaseResponse<RecipeInfo>{
        return try{
            BaseResponse.Success(api.getSpecificRecipe(id))
        }
        catch (ex:Exception){
            return BaseResponse.Error(ResponseError(101,ex.message.toString()))
        }
    }

    suspend fun getListRecipe(start:Int, limit:Int):BaseResponse<RecipeResponse>{
        return try{
            BaseResponse.Success(api.getListRecipe(start,limit))
        }
        catch (ex:Exception){
            return BaseResponse.Error(ResponseError(101,ex.message.toString()))
        }
    }

    suspend fun login(username:String, password:String): BaseResponse<UserResponse>{
        return try{
            BaseResponse.Success(api.login(username,password))
        }
        catch (ex:Exception){
            return BaseResponse.Error(ResponseError(101,ex.message.toString()))
        }
    }

    suspend fun register(userData: UserData) : BaseResponse<Any> {

        try {
            val response = api.createAccount(userData)
            if (response.isSuccessful){
                return BaseResponse.Success<UserData>(userData)
            }
            else {
                return BaseResponse.Error(ResponseError(101,response.message().toString()))
            }
        }
        catch (ex:Exception){
            return BaseResponse.Error(ResponseError(101,ex.message.toString()))
        }
    }

}