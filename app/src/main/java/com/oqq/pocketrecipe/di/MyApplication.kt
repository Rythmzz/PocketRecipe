package com.oqq.pocketrecipe.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication: Application() {

    companion object{
        const val PICK_IMAGE = 1
        const val url_local = "http://192.168.158.1:1337/"
    }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            koin.loadModules(arrayListOf(navOptionsModule ,repositoryModule, dataModule, networkingModule, viewModelModule))
        }
    }
}