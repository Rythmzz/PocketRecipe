package com.oqq.pocketrecipe.data.remote

import com.oqq.pocketrecipe.data.model.client.*
import com.oqq.pocketrecipe.data.model.recipe.Recipe
import com.oqq.pocketrecipe.data.model.recipe.RecipeInfo
import com.oqq.pocketrecipe.data.model.recipe.RecipeRequest
import com.oqq.pocketrecipe.data.model.recipe.RecipeResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @POST("/api/recipes")
    suspend fun createRecipe(@Body body:RecipeRequest) : ResponseBody
    @Multipart
    @POST("/api/upload")
    suspend fun uploadImage(@Part image: MultipartBody.Part) : ResponseBody
    @PUT("/api/users/{id}")
    suspend fun updateInfoUser(@Path("id") id:Int, @Body userInfo: UserInfo) : UserInfo
    @GET("/api/users/{id}?populate=*")
    suspend fun getInfoUser(@Path("id") id:Int) : UserInfo
    @GET("/api/recipes")
    suspend fun getListNotContainRecipeMeal(@Query("filters[meal][\$notContains]") meal:String): RecipeResponse
    @GET("/api/recipes")
    suspend fun getListRecipeMeal(@Query("filters[meal][\$eq]") meal:String): RecipeResponse

    @POST("/api/auth/local/register")
    suspend fun createAccount(@Body body:UserData) : Response<ResponseBody>

    @FormUrlEncoded
    @POST("/api/auth/local")
    suspend fun login(
        @Field("identifier") username:String,
    @Field("password") password:String): UserResponse

    @GET("/api/recipes")
    suspend fun getListRecipe(@Query("pagination[start]") start:Int, @Query("pagination[limit]") limit:Int): RecipeResponse

    @GET("/api/recipes/{id}?populate=*")
    suspend fun getSpecificRecipe(@Path("id") id:Int) : RecipeInfo

    @PUT("/api/recipes/{id}")
    suspend fun updateRecipe(@Path("id") id:Int, @Body recipeRequest: RecipeRequest) : RecipeRequest
}