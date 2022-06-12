package com.example.topmovies.repositories.detail

import com.example.topmovies.api.ApiServices
import com.example.topmovies.db.MoviesEntity
import com.example.topmovies.db.dao.MovieDao
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val api: ApiServices,
    private val dao : MovieDao
    ) {
    //get movies detail
    suspend fun getMoviesDetail(id: Int) = api.getMoviesDetail(id)
    //Insert to favorite
    suspend fun insert(moviesEntity: MoviesEntity) = dao.insert(moviesEntity)
    suspend fun delete(moviesEntity: MoviesEntity) = dao.delete(moviesEntity)

    suspend fun exists(id: Int) = dao.existsMovie(id)
}