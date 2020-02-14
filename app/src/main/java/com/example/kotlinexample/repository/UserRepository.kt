package com.example.kotlinexample.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.kotlinexample.models.User
import com.example.kotlinexample.models.UserResponseModel
import com.example.kotlinexample.repository.db.UserDatabase
import com.example.kotlinexample.retrofit.ApiClient
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.flowOf
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(context: Context){

    private var userList = MutableLiveData<List<User>>()
    private var job : CompletableJob? = null
    private var db = UserDatabase.getInstance(context)

    init {
        fetchUsers()
    }

    companion object{
        @Volatile private var userRepository : UserRepository? = null

        fun getInstance(context: Context) : UserRepository{
            return userRepository?: synchronized(this){
                userRepository?:UserRepository(context).also { userRepository = it }
            }
        }
    }

    fun getUserList() : MutableLiveData<List<User>>{
        return userList
    }

    fun fetchUsers(){
        var dataList = mutableListOf<User>()

        val call : Call<UserResponseModel> = ApiClient.getInterface().getUserList()
        call.enqueue(object : Callback<UserResponseModel> {
            override fun onResponse(call: Call<UserResponseModel>, response: Response<UserResponseModel>) {
                var userResponseModel  =  response!!.body()!!
                dataList.addAll(userResponseModel!!.userList)
                insertUsers(dataList)
                userList.value = dataList
            }

            override fun onFailure(call: Call<UserResponseModel>, t: Throwable) {
            }

        })
    }

    fun getUsers() : MutableLiveData<List<User>>{
        job = Job()
        return object : MutableLiveData<List<User>>(){
            override fun onActive() {
                super.onActive()
                job?.let {
                    CoroutineScope(IO + it).launch {
                        val user = ApiClient.getInterface().getUserList2()
                        Log.e("KOTLIN","user : "+user.toString())
                        withContext(Main){
                            value = user.userList
                            it.complete()
                        }
                    }
                }
            }
        }
    }

    fun cancelJob(){
        job?.cancel()
    }

    private fun insertUsers(userList : List<User>){
        CoroutineScope(IO).launch {
            for (user in userList){
                db.userDao().saveUser(user)
            }
        }
    }
}