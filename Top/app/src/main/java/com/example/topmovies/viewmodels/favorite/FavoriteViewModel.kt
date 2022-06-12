package com.example.topmovies.viewmodels.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topmovies.db.MoviesEntity
import com.example.topmovies.repositories.favorite.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: FavoriteRepository): ViewModel() {
    var favoriteListLive = MutableLiveData<List<MoviesEntity>>()
    var empty = MutableLiveData<Boolean>()


    fun getAllFavoriteList() = viewModelScope.launch {
        val list = repository.getAllFavoriteList()
        if (list.isNotEmpty()){
            favoriteListLive.postValue(list)
        }else{
            empty.postValue(true)
        }
    }
}