package com.example.topmovies.viewmodels.topmovies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.topmovies.models.ResponseGenres
import com.example.topmovies.models.topmovie.ResponseTopMovies
import com.example.topmovies.paging.LastMoviesPagingSource
import com.example.topmovies.repositories.topmovies.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    var getTopMoviesLive = MutableLiveData<ResponseTopMovies>()
    var getGenresLive = MutableLiveData<ResponseGenres>()
    var loading = MutableLiveData<Boolean>()

    //Get top movies
    fun getTopMovies(id: Int) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.getTopMovies(id)
        if (response.isSuccessful) {
            getTopMoviesLive.postValue(response.body())
        }
        loading.postValue(false)
    }
    //Get genres

    fun getGenres() = viewModelScope.launch {
        val response = repository.getGenres()
        if (response.isSuccessful) {
            getGenresLive.postValue(response.body())
        }
    }

    //Get last movies with paging3
    val getLastMoviesList = Pager(PagingConfig(1)) {
        LastMoviesPagingSource(repository)
    }.flow.cachedIn(viewModelScope)

}