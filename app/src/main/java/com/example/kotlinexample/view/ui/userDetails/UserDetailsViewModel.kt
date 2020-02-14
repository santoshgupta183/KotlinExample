package com.example.kotlinexample.view.ui.userDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserDetailsViewModel : ViewModel() {
    private var mText: MutableLiveData<String> = MutableLiveData()

    init {
        mText.value = "This is User Details fragment"
    }

    fun getText(): LiveData<String>? {
        return mText
    }
}
