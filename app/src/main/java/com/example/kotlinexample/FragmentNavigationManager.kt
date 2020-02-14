package com.example.kotlinexample

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.kotlinexample.utils.Constants
import com.example.kotlinexample.view.MainActivity
import com.example.kotlinexample.view.ui.userDetails.UserDetailsFragment
import com.example.kotlinexample.view.ui.userList.UserListFragment

class FragmentNavigationManager private constructor(activity: Activity) : NavigationManager {
    private val activity = activity as MainActivity
    private var fragmentManager : FragmentManager = this.activity.supportFragmentManager

    companion object{
        @Volatile var instance : FragmentNavigationManager? = null

        fun getInstance(activity: Activity) : FragmentNavigationManager{
            return instance?: synchronized(this){
                instance?: FragmentNavigationManager(activity).also { instance = it }
            }
        }
    }

    override fun showFragment(tag: FragmentTag, bundle: Bundle?) {
        val fragment : Fragment = findFragmentByTag(tag)
        bundle?.let { fragment.arguments = bundle }
        val ft = fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, tag.toString())

        if(tag == FragmentTag.USER_DETAILS)
            ft.addToBackStack(null).commit()
        else
            ft.commit()
    }

    private fun findFragmentByTag(tag : FragmentTag) : Fragment{
        return fragmentManager.findFragmentByTag(tag.toString())
            ?: when(tag){
                FragmentTag.USER_LIST ->{
                    UserListFragment()
                }
                FragmentTag.USER_DETAILS->{
                    UserDetailsFragment()
                }
            }
    }
}