package com.example.kotlinexample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinexample.models.User

class UserAdapter(userList: List<User>, context: Context) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var userList : List<User> = userList as MutableList<User>
    private var context: Context = context
    private var onUserSelectListener : OnUserSelectListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setOnUserSelectListener(onUserSelectListener: OnUserSelectListener){
        this.onUserSelectListener = onUserSelectListener
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user : User = userList[position]
        holder.userName.setText(user.firstName + " " +  user.lastName)
        holder.userEmail.setText(user.email)
        Glide.with(context)
            .load(user.avatar)
            .into(holder.userImage)

        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onUserSelectListener!!.onUserSelect(position)
            }

        })

    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName : TextView = itemView.findViewById(R.id.user_name)
        val userEmail : TextView = itemView.findViewById(R.id.user_email)
        val userImage : ImageView = itemView.findViewById(R.id.avatar)

    }

    interface OnUserSelectListener{
        fun onUserSelect(position : Int)
    }
}