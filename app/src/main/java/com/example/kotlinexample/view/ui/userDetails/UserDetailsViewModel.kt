package com.example.kotlinexample.view.ui.userDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.kotlinexample.MyApp
import com.example.kotlinexample.models.User
import com.example.kotlinexample.repository.db.UserDatabase

class UserDetailsViewModel : ViewModel() {
    private var db  = MyApp.context?.let { UserDatabase.getInstance(it) }
    private val userId = MutableLiveData<Int>()
    val userObj : LiveData<User> = Transformations.switchMap(userId){
        db!!.userDao().getUserById(it)
    }

    fun setUserId(id : Int){
        userId.value = id
    }


}
