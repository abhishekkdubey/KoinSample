package com.example.finin.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM USER")
    fun getUser(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserList(users: List<User>)
}