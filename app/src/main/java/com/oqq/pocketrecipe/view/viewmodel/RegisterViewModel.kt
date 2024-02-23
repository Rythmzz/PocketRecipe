package com.oqq.pocketrecipe.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oqq.pocketrecipe.data.model.client.UserData
import com.oqq.pocketrecipe.repo.BaseResponse
import com.oqq.pocketrecipe.repo.Repository
import com.oqq.pocketrecipe.repo.ResponseError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: Repository) :ViewModel(){
    private val _success = MutableSharedFlow<Any>()
    val success:SharedFlow<Any> get() = _success

    private val _loading = MutableSharedFlow<Any>()
    val loading:SharedFlow<Any> get() = _loading

    fun register(userData:UserData){
        viewModelScope.launch {
            _loading.emit(true)
            when (val result = repository.register(userData)){
                is BaseResponse.Success -> {
                    _success.emit(result.data)
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
}