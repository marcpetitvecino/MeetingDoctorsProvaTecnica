package com.example.meetingdoctorsprovatecnica

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val textTV: AppCompatTextView = view.findViewById(R.id.textTV)
}