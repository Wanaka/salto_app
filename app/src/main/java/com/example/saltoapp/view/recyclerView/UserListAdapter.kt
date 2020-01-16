package com.example.saltoapp.view.recyclerView

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.example.saltoapp.R
import com.example.saltoapp.view.model.User
import kotlinx.android.synthetic.main.user_list_item.view.*


class UserListAdapter(
    private val items: List<User>,
    private val context: Context,
    private val mListener: OnItemClickListener?
) : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(
            doorName: String,
            mUser: String,
            frontDoor: Boolean,
            storageRoom: Boolean,
            isAdmin: Boolean
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        return UserListViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.user_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.user.text = items[position].name
        holder.frontDoor.isChecked = items[position].frontDoor
        holder.storageRoom.isChecked = items[position].storageRoom
        holder.isAdmin = items[position].isAdmin

        holder.frontDoor.setOnCheckedChangeListener { _, isChecked ->
            mListener!!.onItemClick(
                "Front Door",
                holder.user.text.toString(),
                isChecked,
                holder.storageRoom.isChecked,
                holder.isAdmin
            )
        }

        holder.storageRoom.setOnCheckedChangeListener { _, isChecked ->
            mListener!!.onItemClick(
                "Storage Room",
                holder.user.text.toString(),
                holder.frontDoor.isChecked,
                isChecked,
                holder.isAdmin
            )
        }

    }


    class UserListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val user: TextView = view.employeeTextUser
        val frontDoor: Switch = view.employeeSwitchFrontDoor
        val storageRoom: Switch = view.employeeSwitchStorageRoom
        var isAdmin: Boolean = false
    }
}