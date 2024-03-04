package com.oqq.pocketrecipe.di

import com.oqq.pocketrecipe.view.viewmodel.LoginViewModel
import com.oqq.pocketrecipe.view.viewmodel.RecipeViewModel
import com.oqq.pocketrecipe.view.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        LoginViewModel(get())
    }

    viewModel {
        RegisterViewModel(get())
    }

    viewModel{
        RecipeViewModel(get())
    }
}