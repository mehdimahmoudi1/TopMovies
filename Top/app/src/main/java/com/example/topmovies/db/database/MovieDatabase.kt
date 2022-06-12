package com.example.topmovies.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.topmovies.db.MoviesEntity
import com.example.topmovies.db.dao.MovieDao
import javax.inject.Inject

@Database(entities = [MoviesEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
}