package com.example.hotelreservation.Presentation.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hotelreservation.DI.ServiceLocator
import com.example.hotelreservation.Presentation.Repository.Model.BookingInfo
import com.example.hotelreservation.Presentation.Repository.Model.Booking
import com.example.hotelreservation.Presentation.Repository.Model.Room

class BookingListViewModel : ViewModel() {

    fun getBookingList(): LiveData<List<Booking>> {
        return ServiceLocator.getInstance().getRepository().getAllBookings()
    }

    fun getBookingsWithHotels(): LiveData<List<BookingInfo>> {
        return ServiceLocator.getInstance().getRepository().getBookingInfo()
    }

    fun getBookingById(bookingId: Int): LiveData<List<Booking>> {
        return ServiceLocator.getInstance().getRepository().getBookingById(bookingId)
    }

    fun deleteBooking(booking: Booking) {
        ServiceLocator.getInstance().getRepository().deleteBooking(booking)
    }


    fun getRoomById(roomId: Int): LiveData<List<Room>> {
        return ServiceLocator.getInstance().getRepository().getRoomById(roomId)
    }

}