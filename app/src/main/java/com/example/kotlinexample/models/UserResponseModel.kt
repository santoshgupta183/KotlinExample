package com.example.kotlinexample.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserResponseModel(
    @Expose
    @SerializedName("data")
    var userList : List<User>
)