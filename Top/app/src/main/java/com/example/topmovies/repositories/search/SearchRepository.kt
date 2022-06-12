package com.example.topmovies.repositories.search

import com.example.topmovies.api.ApiServices
import javax.inject.Inject

class SearchRepository @Inject constructor(private val api : ApiServices){
    suspend fun searchMovies(name : String) = api.searchMovies(name)
}