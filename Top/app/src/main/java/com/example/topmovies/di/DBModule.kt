package com.example.topmovies.di

import android.content.Context
import androidx.room.Room
import com.example.topmovies.db.MoviesEntity
import com.example.topmovies.db.dao.MovieDao
import com.example.topmovies.db.database.MovieDatabase
import com.example.topmovies.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {
    //Database
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        MovieDatabase::class.java,
        Constants.MOVIE_DATABASE_NAME
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
    //Dao
    @Provides
    @Singleton
    fun provideDao (db: MovieDatabase) = db.movieDao()
    //Model
    @Provides
    @Singleton
    fun provideMovieEntity() = MoviesEntity()
}