package com.oqq.pocketrecipe.listener

import androidx.navigation.NavController

interface RegisterListener {
    fun registerOnListener(result:Boolean, navController: NavController)
}