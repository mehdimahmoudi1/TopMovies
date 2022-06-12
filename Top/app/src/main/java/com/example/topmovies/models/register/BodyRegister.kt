package com.example.topmovies.models.register

import com.google.gson.annotations.SerializedName

data class BodyRegister(
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("password")
    var password: String?= null,
    @SerializedName("email")
    var email: String?= null
)
