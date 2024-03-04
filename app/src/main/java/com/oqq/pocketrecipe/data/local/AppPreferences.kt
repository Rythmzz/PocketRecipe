package com.oqq.pocketrecipe.data.local

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oqq.pocketrecipe.data.model.client.UserResponse
import com.securepreferences.SecurePreferences

class AppPreferences(private val securePreferences: SecurePreferences) {
    companion object {
        const val TOKEN = "USER_TOKEN"
        const val USER_INFO = "USER_INFO"
        const val FIRST_THEME = "FIRST_THEME"
    }


    fun setFirstTheme(show:Boolean){
        securePreferences.edit().putBoolean(FIRST_THEME, show).apply()
    }

    fun getFirstTheme() = securePreferences.getBoolean(FIRST_THEME,true)

    fun setToken(token:String?){
        securePreferences.edit().putString(TOKEN,token).apply()
    }

    fun getToken() = securePreferences.getString(TOKEN,null)

    fun setUserInfo(user:UserResponse?){
        val gson = Gson()
        val json = gson.toJson(user)
        securePreferences.edit().putString(USER_INFO,json).apply()
    }

    fun getUserInfo() : UserResponse{
        val gson = Gson()
        val string = securePreferences.getString(USER_INFO,null)
        return gson.fromJson(string, object:TypeToken<UserResponse>() {}.type)
    }




}