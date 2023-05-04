package com.example.hotelreservation.Presentation.View.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelreservation.Presentation.Repository.Model.BookingInfo
import com.example.hotelreservation.databinding.BookingListElementBinding

class BookingListAdapter(private val bookings: List<BookingInfo>) : RecyclerView.Adapter<BookingListAdapter.ViewHolder>() {
    private var listener: ((BookingInfo) -> Unit)? = null
    private var onCloseListener: ((BookingInfo) -> Unit)? = null

    inner class ViewHolder(private val binding: BookingListElementBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bookingInfo: BookingInfo) {
            with(binding) {
                hotelName.text = bookingInfo.hotel.name
                hotelAddress.text = bookingInfo.hotel.address
                guestName.text = bookingInfo.booking.guestName
                hotelRoomNumber.text = "Номер: ${bookingInfo.room.roomNumber} (${bookingInfo.room.type})"
                stayDates.text = "${bookingInfo.booking.checkInDate} - ${bookingInfo.booking.checkOutDate}"
                closeButton.setOnClickListener {
                    onCloseListener?.invoke(bookingInfo)
                }
                root.setOnClickListener {
                    listener?.invoke(bookingInfo)
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (BookingInfo) -> Unit) {
        this.listener = listener
    }

    fun setOnItemCloseListener(onCloseListener: (BookingInfo) -> Unit) {
        this.onCloseListener = onCloseListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BookingListElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bookings[position])
    }

    override fun getItemCount() = bookings.size
}