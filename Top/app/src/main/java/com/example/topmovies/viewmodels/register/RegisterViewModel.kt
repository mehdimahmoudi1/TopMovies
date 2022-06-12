package com.example.topmovies.viewmodels.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topmovies.models.register.BodyRegister
import com.example.topmovies.models.register.ResponseRegister
import com.example.topmovies.repositories.register.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository): ViewModel() {

    //registerUserLiveData
    var registerUser = MutableLiveData<ResponseRegister>()
    var isLoading = MutableLiveData<Boolean>()

    //registerUser
    fun registerUsr(bodyRegister: BodyRegister) = viewModelScope.launch {
        isLoading.postValue(true)
        val response = repository.registerUser(bodyRegister)
        if (response.isSuccessful){
            registerUser.postValue(response.body())
        }
        isLoading.postValue(false)
    }
}