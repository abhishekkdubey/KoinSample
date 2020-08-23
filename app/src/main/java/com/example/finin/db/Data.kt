package com.example.finin.db


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("ad")
    val ad: Ad,
    @SerializedName("data")
    val users: List<User>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)