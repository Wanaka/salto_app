package com.example.saltoapp.view.recyclerView

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.saltoapp.R
import com.example.saltoapp.view.model.DoorInteraction
import kotlinx.android.synthetic.main.event_list_item.view.*


class EventListAdapter(private val items: List<DoorInteraction>, private val context: Context) :
    RecyclerView.Adapter<EventListAdapter.EventListViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder {
        return EventListViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.event_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        holder.door.text = items[position].door
        holder.user.text = items[position].user
        holder.access = items[position].access

        when (holder.access) {
            true -> holder.view.setBackgroundColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.colorGreen
                )
            )
            false -> holder.view.setBackgroundColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.colorRed
                )
            )
        }
    }


    class EventListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val door: TextView = view.doorText
        val user: TextView = view.userText
        val view: View = view.eventListView
        var access: Boolean = false
    }
}