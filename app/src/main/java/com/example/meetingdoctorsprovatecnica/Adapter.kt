package com.example.meetingdoctorsprovatecnica

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val list: List<String?>): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell, parent, false))

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var sameWordcount = 0
        list.forEach {
            if (list[position].equals(it, ignoreCase = true)) sameWordcount++
        }
        holder.textTV.text = "${list[position]} - Appears $sameWordcount times"
    }

}