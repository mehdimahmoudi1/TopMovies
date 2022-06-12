package com.example.topmovies.viewmodels.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topmovies.db.MoviesEntity
import com.example.topmovies.models.detail.ResponseDetailMovies
import com.example.topmovies.models.topmovie.ResponseTopMovies
import com.example.topmovies.repositories.detail.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DetailRepository,
) : ViewModel() {
    //Api
    var getDetailLive = MutableLiveData<ResponseDetailMovies>()
    var loading = MutableLiveData<Boolean>()

    //Get movies detail
    fun getMoviesDetail(movie_id: Int) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.getMoviesDetail(movie_id)
        if (response.isSuccessful) {
            getDetailLive.postValue(response.body())
        }
        loading.postValue(false)
    }
    //Database
    val isFavorite = MutableLiveData<Boolean>()
   suspend fun exists(id: Int) = withContext(viewModelScope.coroutineContext){repository.exists(id)}

    fun isFavorite(id: Int,moviesEntity: MoviesEntity) = viewModelScope.launch {
        val exists = repository.exists(id)
        if (exists){
            isFavorite.postValue(false)
            repository.delete(moviesEntity)
        }else{
            isFavorite.postValue(true)
            repository.insert(moviesEntity)
        }
    }
}