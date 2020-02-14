package com.example.kotlinexample

import android.os.Bundle

interface NavigationManager {
    fun showFragment(tag : FragmentTag, bundle: Bundle?)
}