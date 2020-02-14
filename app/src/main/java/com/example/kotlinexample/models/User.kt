package com.example.kotlinexample.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @PrimaryKey
    @Expose
    @SerializedName("id")
    var id : Int,

    @ColumnInfo(name = "FirstName")
    @Expose
    @SerializedName("first_name")
    var firstName : String,

    @ColumnInfo(name = "LastName")
    @Expose
    @SerializedName("last_name")
    var lastName : String,

    @ColumnInfo(name = "Email")
    @Expose
    @SerializedName("email")
    var email : String,

    @ColumnInfo(name = "Image")
    @Expose
    @SerializedName("avatar")
    var avatar : String )

/*
"id": 1,
      "email": "george.bluth@reqres.in",
      "first_name": "George",
      "last_name": "Bluth",
      "avatar": "https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg"
 */