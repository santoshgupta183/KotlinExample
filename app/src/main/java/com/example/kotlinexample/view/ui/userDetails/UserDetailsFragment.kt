package com.example.kotlinexample.view.ui.userDetails

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.kotlinexample.FragmentNavigationManager
import com.example.kotlinexample.FragmentTag
import com.example.kotlinexample.NavigationManager
import com.example.kotlinexample.R
import com.example.kotlinexample.models.User
import com.example.kotlinexample.utils.Constants
import com.example.kotlinexample.utils.Converter
import com.example.kotlinexample.view.MainActivity

class UserDetailsFragment : Fragment() {

    private lateinit var viewModel: UserDetailsViewModel
    private lateinit var userDetailsView: View
    private lateinit var userImage: ImageView
    private lateinit var firstNameTV: TextView
    private lateinit var lastNameTV: TextView
    private lateinit var emailTV: TextView
    private lateinit var idTV: TextView
    private lateinit var navigationManager : NavigationManager
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userDetailsView = inflater.inflate(R.layout.user_details_fragment, container, false)

        navigationManager = FragmentNavigationManager.getInstance(activity as MainActivity)
        val bundle = arguments
        user  = Converter.toObject(bundle!!.getString(Constants.OBJ_KEY)!!, User::class.java)

        userImage = userDetailsView.findViewById(R.id.imageView)
        firstNameTV = userDetailsView.findViewById(R.id.first_name_val)
        lastNameTV = userDetailsView.findViewById(R.id.last_name_val)
        emailTV = userDetailsView.findViewById(R.id.email_val)
        idTV = userDetailsView.findViewById(R.id.person_id_val)

//        initUI(user)

        return userDetailsView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserDetailsViewModel::class.java)
        viewModel.userObj.observe(viewLifecycleOwner, Observer {
            initUI(it)
        })
       viewModel.setUserId(user.id)

        val toolbar : Toolbar = activity!!.findViewById(R.id.toolbar)
        toolbar.setTitle(R.string.user_details_title)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { navigationManager.showFragment(FragmentTag.USER_LIST, null) }
    }

    private fun initUI(user: User){
        firstNameTV.text = user.firstName
        lastNameTV.text = user.lastName
        val id :Int = user.id
        idTV.text = "$id"
        emailTV.text = user.email

        Glide.with(this.context!!)
            .load(user.avatar)
            .into(userImage)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item : MenuItem = menu.findItem(R.id.action_refresh)
        item?.let { item.setVisible(false) }
    }
}
