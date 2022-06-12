package com.example.topmovies.models.topmovie


import com.google.gson.annotations.SerializedName

data class ResponseTopMovies(
    @SerializedName("data")
    val `data`: List<Data?>?,
    @SerializedName("metadata")
    val metadata: Metadata?
) {
    data class Data(
        @SerializedName("country")
        val country: String?, // USA
        @SerializedName("genres")
        val genres: List<String?>?,
        @SerializedName("id")
        val id: Int?, // 6
        @SerializedName("images")
        val images: List<String?>?,
        @SerializedName("imdb_rating")
        val imdbRating: String?, // 8.9
        @SerializedName("poster")
        val poster: String?, // https://moviesapi.ir/images/tt0108052_poster.jpg
        @SerializedName("title")
        val title: String?, // Schindler's List
        @SerializedName("year")
        val year: String? // 1993
    )

    data class Metadata(
        @SerializedName("current_page")
        val currentPage: String?, // 1
        @SerializedName("page_count")
        val pageCount: Int?, // 2
        @SerializedName("per_page")
        val perPage: Int?, // 10
        @SerializedName("total_count")
        val totalCount: Int? // 19
    )
}