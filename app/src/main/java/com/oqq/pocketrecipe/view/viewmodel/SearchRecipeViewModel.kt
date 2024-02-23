package com.oqq.pocketrecipe.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oqq.pocketrecipe.repo.Repository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class SearchRecipeViewModel(val repository: Repository) :ViewModel() {
    private val _success = MutableSharedFlow<Any>()
    val success:SharedFlow<Any> get() = _success

    private val _loading = MutableSharedFlow<Any>()
    val loading:SharedFlow<Any> get() = _loading

    fun getRecipeSearch(){
        viewModelScope.launch {
            _loading.emit(true)

//            when(val result = repository.getRecipeSearch()){
//                is BaseResponse.Success -> {
//
//                }
            }
        }
}