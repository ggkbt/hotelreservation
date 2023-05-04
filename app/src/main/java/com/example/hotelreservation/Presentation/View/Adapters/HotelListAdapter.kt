package com.example.hotelreservation.Presentation.View.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelreservation.Presentation.Repository.Model.Hotel
import com.example.hotelreservation.Presentation.View.HotelListFragmentDirections
import com.example.hotelreservation.databinding.HotelListElementBinding

class HotelListAdapter(private val hotels: List<Hotel>) : RecyclerView.Adapter<HotelListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: HotelListElementBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hotel: Hotel) {
            with(binding) {
                hotelName.text = hotel.name
                hotelAddress.text = hotel.address
                hotelRoomNumber.text = hotel.roomCount.toString()
            }
            itemView.setOnClickListener {
                //val action = HotelListFragmentDirections
                val action = HotelListFragmentDirections.actionHotelListFragmentToBookingFragment(hotel.id)
                it.findNavController().navigate(action)
                Toast.makeText(itemView.context, "Click", Toast.LENGTH_SHORT).show()
//                val bundle = bundleOf("hotelId" to hotel.id)
//                it.findNavController().navigate(R.id.bookingFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HotelListElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)


        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(hotels[position])
    }

    override fun getItemCount() = hotels.size
}
