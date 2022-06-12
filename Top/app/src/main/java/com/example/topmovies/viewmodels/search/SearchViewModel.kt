package com.example.topmovies.viewmodels.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topmovies.models.lastmovies.ResponseLastMovies
import com.example.topmovies.repositories.search.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) : ViewModel() {
    var searchMovieLive = MutableLiveData<ResponseLastMovies>()
    var loading = MutableLiveData<Boolean>()
    var empty = MutableLiveData<Boolean>()


    fun searchMovies(name : String) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.searchMovies(name)
        if (response.isSuccessful){
            if (response.body()?.data!!.isNotEmpty()){
                searchMovieLive.postValue(response.body())
                empty.postValue(false)
            }else{
                empty.postValue(true)
            }
        }
        loading.postValue(false)

    }
}