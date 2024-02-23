package com.oqq.pocketrecipe.di

import androidx.navigation.navOptions
import com.oqq.pocketrecipe.R
import org.koin.dsl.module


val navOptionsModule = module {
    single {
        navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
    }
}