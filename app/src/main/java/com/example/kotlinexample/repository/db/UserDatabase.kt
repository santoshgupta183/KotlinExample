package com.example.kotlinexample.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlinexample.models.User

@Database(
    entities = [(User :: class)], version = 1
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao

    companion object {
        @Volatile private var instance : UserDatabase ? = null

        fun getInstance(context: Context) : UserDatabase{
            return instance?: synchronized(this){
                instance?: buildDatabase(context)
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                UserDatabase::class.java, "Users.db")
                .build()
    }
}