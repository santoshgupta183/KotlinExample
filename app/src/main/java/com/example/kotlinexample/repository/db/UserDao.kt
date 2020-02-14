package com.example.kotlinexample.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlinexample.models.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAllUsers(): LiveData<List<User>>

    @Query("Select * from User where id = :userId")
    fun getUserById(userId : Int): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user : User)
}