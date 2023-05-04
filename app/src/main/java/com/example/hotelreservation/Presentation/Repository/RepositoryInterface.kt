package com.example.hotelreservation.Presentation.Repository

import androidx.lifecycle.LiveData
import com.example.hotelreservation.Presentation.Repository.Model.BookingInfo
import com.example.hotelreservation.Presentation.Repository.Model.Booking
import com.example.hotelreservation.Presentation.Repository.Model.Hotel
import com.example.hotelreservation.Presentation.Repository.Model.Room

interface RepositoryInterface {
    fun getAllHotels(): LiveData<List<Hotel>>
    fun getAllBookings(): LiveData<List<Booking>>
    fun addHotel(hotel: Hotel)
//    fun updateHotel(hotel: Hotel)
    fun deleteHotel(hotel: Hotel)
    fun addBooking(booking: Booking)
    fun updateBooking(booking: Booking)
    fun getBookingById(bookingId: Int): LiveData<List<Booking>>
    fun getBookingInfo(): LiveData<List<BookingInfo>>
//    fun updateBooking(booking: Booking)
    fun deleteBooking(booking: Booking)
//    fun findBookingById(bookingId: Int): LiveData<Booking>
    fun addRoom(room: Room)
//    fun updateRoom(room: Room)
    fun deleteRoom(room: Room)
    fun getRoomById(roomId: Int): LiveData<List<Room>>
    fun getRoomsByHotel(hotelId: Int): LiveData<List<Room>>
    fun getHotelById(hotelId: Int): LiveData<List<Hotel>>
    fun deleteAllBookings()
    fun deleteAllHotels()
    fun deleteAllRooms()
}
