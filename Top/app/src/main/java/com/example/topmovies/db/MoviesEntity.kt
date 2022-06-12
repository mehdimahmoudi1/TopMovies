package com.example.topmovies.db

import androidx.compose.ui.unit.Constraints
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.topmovies.utils.Constants

@Entity(tableName = Constants.MOVIE_TABLE_NAME)
data class MoviesEntity(
    @PrimaryKey
    var id : Int= 0,
    var poster: String = "",
    var title: String = "",
    var genre: String = "",
    var imdbRating: String = "",
    var year: String = "",
    var country: String = ""
)
