package com.example.koin.db


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int,
    @ColumnInfo(name = "avatar")
    @SerializedName("avatar")
    val avatar: String,
    @ColumnInfo(name = "email")
    @SerializedName("email")
    val email: String,
    @ColumnInfo(name = "first_name")
    @SerializedName("first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    @SerializedName("last_name")
    val lastName: String
)