package com.example.kotlinexample.view

import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinexample.*
import com.example.kotlinexample.broadcastreceiver.NetworkBroadcastReceiver
import com.example.kotlinexample.models.User
import com.example.kotlinexample.utils.Constants
import com.example.kotlinexample.utils.PreferenceManager
import com.example.kotlinexample.view.ui.userList.UserListFragment
import com.example.kotlinexample.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private var networkBroadcastReceiver: NetworkBroadcastReceiver =  NetworkBroadcastReceiver()
    private val navigationManager : NavigationManager = FragmentNavigationManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar : Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle(R.string.user_list_title)
        setSupportActionBar(toolbar)

        navigationManager.showFragment(FragmentTag.USER_LIST,null)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(networkBroadcastReceiver, IntentFilter(Context.CONNECTIVITY_SERVICE))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkBroadcastReceiver)
    }

}
