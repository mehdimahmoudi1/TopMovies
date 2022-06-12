package com.example.topmovies.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.topmovies.db.MoviesEntity
import com.example.topmovies.utils.Constants

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: MoviesEntity)

    @Delete
    suspend fun delete(entity: MoviesEntity)

    @Query("SELECT * FROM ${Constants.MOVIE_TABLE_NAME}")
    fun getAll(): MutableList<MoviesEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM ${Constants.MOVIE_TABLE_NAME} WHERE id =  :movieId)")
    suspend fun existsMovie(movieId:Int) : Boolean
}