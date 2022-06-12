package com.example.topmovies.repositories.topmovies

import com.example.topmovies.api.ApiServices
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api: ApiServices) {
    //get top movies
    suspend fun getTopMovies(id:Int) = api.getTopMovies(id)
    //get genres list
    suspend fun getGenres() = api.getGenres()
    //get last movies
    suspend fun getLastMovies(page:Int) = api.getLastMovies(page)

}