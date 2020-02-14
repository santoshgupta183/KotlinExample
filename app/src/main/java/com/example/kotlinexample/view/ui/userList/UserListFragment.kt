package com.example.kotlinexample.view.ui.userList

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinexample.*
import com.example.kotlinexample.models.User
import com.example.kotlinexample.utils.Constants
import com.example.kotlinexample.utils.Converter
import com.example.kotlinexample.view.MainActivity
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList


class UserListFragment : Fragment() {

    private lateinit var viewModel: UserListViewModel
    lateinit var rootView : CoordinatorLayout
    lateinit var recyclerView : RecyclerView
    lateinit var adapter : UserAdapter
    var userList  = ArrayList<User>()
    lateinit var progressBar: ProgressBar
    lateinit var userListView: View
    lateinit var navigationManager : NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userListView =inflater.inflate(R.layout.user_list_fragment, container, false)

        var toolbar : Toolbar = activity!!.findViewById(R.id.toolbar)
        toolbar.setTitle(R.string.user_list_title)
        toolbar.navigationIcon = null

        rootView = activity!!.findViewById(R.id.rootView)
        progressBar = userListView.findViewById(R.id.progress_bar)
        recyclerView = userListView.findViewById(R.id.recyclerView)
        adapter = UserAdapter(userList, this.activity!!.applicationContext)

        recyclerView.layoutManager = LinearLayoutManager(this.activity)
        recyclerView.adapter = adapter

        adapter.setOnUserSelectListener(object : UserAdapter.OnUserSelectListener{
            override fun onUserSelect(position: Int) {
                var bundle  = Bundle()
                bundle.putString(Constants.OBJ_KEY, Converter.toString(userList.get(position) as Object))
                navigationManager.showFragment(FragmentTag.USER_DETAILS, bundle)
            }
        })

        return  userListView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navigationManager = FragmentNavigationManager.getInstance(this.activity as MainActivity)
        viewModel = ViewModelProvider(this).get(UserListViewModel::class.java)

        viewModel.getLoadingStatus().observe(viewLifecycleOwner, Observer {
            if(it)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.GONE
        })

        viewModel.getList().observe(viewLifecycleOwner, Observer {
            userList.clear()
            userList.addAll(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.getNetworkStatus().observe(viewLifecycleOwner, Observer {

            if(it)
                Snackbar.make(rootView,"Internet Connected!", Snackbar.LENGTH_SHORT).show()
            else
                Snackbar.make(rootView,"No Network Connection!", Snackbar.LENGTH_SHORT).show()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.e(Constants.TAG, "Fragment onOptionsItemSelected")
        return when(item.itemId){
            R.id.action_refresh ->{
                viewModel.refreshData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
