package com.example.hotelreservation.Presentation.View.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.hotelreservation.Presentation.Repository.Model.Room

class RoomSpinnerAdapter(context: Context, resource: Int, rooms: List<Room>) :
    ArrayAdapter<Room>(context, resource, rooms) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_spinner_item, parent, false)
        val room = getItem(position)
        val displayText = "${room?.roomNumber} - ${room?.type} - ${room?.price}"
        (view as? TextView)?.text = displayText
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
        val room = getItem(position)
        val displayText = "${room?.roomNumber} - ${room?.type} - ${room?.price}"
        (view as? TextView)?.text = displayText
        return view
    }
}
