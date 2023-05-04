package com.example.hotelreservation.Presentation.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hotelreservation.DI.ServiceLocator
import com.example.hotelreservation.Presentation.Repository.Model.Booking
import com.example.hotelreservation.Presentation.Repository.Model.Hotel
import com.example.hotelreservation.Presentation.Repository.Model.Room

class BookingViewModel : ViewModel() {

    fun getRoomsByHotel(hotelId: Int): LiveData<List<Room>> {
        return ServiceLocator.getInstance().getRepository().getRoomsByHotel(hotelId)
    }

    fun addBooking(booking: Booking) {
        ServiceLocator.getInstance().getRepository().addBooking(booking)
    }

    fun updateBooking(booking: Booking) {
        ServiceLocator.getInstance().getRepository().updateBooking(booking)
    }

    fun getHotelById(hotelId: Int): LiveData<List<Hotel>> {
        return ServiceLocator.getInstance().getRepository().getHotelById(hotelId)
    }
}