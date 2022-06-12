package com.example.topmovies.api

import com.example.topmovies.models.ResponseGenres
import com.example.topmovies.models.detail.ResponseDetailMovies
import com.example.topmovies.models.lastmovies.ResponseLastMovies
import com.example.topmovies.models.register.BodyRegister
import com.example.topmovies.models.register.ResponseRegister
import com.example.topmovies.models.topmovie.ResponseTopMovies
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {
    //Register
    @POST("register")
    suspend fun registerUser(
        @Body body: BodyRegister
    ): Response<ResponseRegister>

    //Get Top Movies
    @GET("genres/{genre_id}/movies")
    suspend fun getTopMovies(
        @Path("genre_id") id: Int
    ): Response<ResponseTopMovies>

    //Get Genres List
    @GET("genres")
    suspend fun getGenres(): Response<ResponseGenres>

    //Get Last Movies
    @GET("movies")
    suspend fun getLastMovies(@Query("page") page: Int): Response<ResponseLastMovies>

    //Search Movie
    @GET("movies")
    suspend fun searchMovies(@Query("q") name: String): Response<ResponseLastMovies>

    //Get Movie Detail
    @GET("movies/{movie_id}")
    suspend fun getMoviesDetail(@Path("movie_id") movie_id: Int): Response<ResponseDetailMovies>
}