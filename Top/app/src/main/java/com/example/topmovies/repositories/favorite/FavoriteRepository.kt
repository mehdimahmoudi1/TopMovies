package com.example.topmovies.repositories.favorite

import com.example.topmovies.db.dao.MovieDao
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val dao : MovieDao) {

    fun getAllFavoriteList() = dao.getAll()

}