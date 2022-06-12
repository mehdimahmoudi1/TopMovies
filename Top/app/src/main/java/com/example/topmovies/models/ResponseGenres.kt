package com.example.topmovies.models


import com.google.gson.annotations.SerializedName

class ResponseGenres : ArrayList<ResponseGenres.ResponseGenersItem>(){
    data class ResponseGenersItem(
        @SerializedName("id")
        val id: Int?, // 1
        @SerializedName("name")
        val name: String? // Sport
    )
}