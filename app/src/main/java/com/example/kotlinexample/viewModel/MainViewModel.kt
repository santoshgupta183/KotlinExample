package com.example.kotlinexample.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinexample.MyApp
import com.example.kotlinexample.models.User
import com.example.kotlinexample.repository.UserRepository
import com.example.kotlinexample.repository.db.UserDatabase
import com.example.kotlinexample.utils.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var showLoading = MutableLiveData<Boolean>()
    private var userList : LiveData<List<User>> = MutableLiveData<List<User>>()
    private var userRepository = MyApp.context?.let { UserRepository.getInstance(it) }
    private var db  = MyApp.context?.let { UserDatabase.getInstance(it) }
    private var preferenceManager = MyApp.context?.let { PreferenceManager.getInstance(it) }
    private var showNetworkToast = MutableLiveData<Boolean>()

    init {
        refreshData()
    }

    fun getList() : LiveData<List<User>>{
        return userList
    }

    fun getLoadingStatus() : LiveData<Boolean>{
        return showLoading
    }

    fun getNetworkStatus() : LiveData<Boolean>{
        return showNetworkToast
    }

    fun refreshData() {
        showLoading.value = true
        if (preferenceManager!!.getNetworkStatus()){
            showNetworkToast.value = true
            userRepository!!.fetchUsers()
        }else{
            showNetworkToast.value = false
            userList = db!!.userDao().getAllUsers()
        }

        GlobalScope.launch(context = Dispatchers.Main){
            delay(1000)
            showLoading.value = false
        }
    }

}