package com.example.hotelreservation.Presentation.Repository.Room.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.hotelreservation.Presentation.Repository.Model.BookingInfo
import com.example.hotelreservation.Presentation.Repository.Model.Booking

@Dao
interface BookingDAO {

    @Insert
    fun addBooking(booking: Booking)

    @Update
    fun updateBooking(booking: Booking)

    @Delete
    fun deleteBooking(booking: Booking)

    @Query("SELECT * FROM booking")
    fun getAllBookings(): LiveData<List<Booking>>

    @Query("SELECT * FROM booking WHERE booking_id = :id")
    fun getBookingById(id: Int): LiveData<List<Booking>>

    @Transaction
    @Query("SELECT * FROM booking INNER JOIN hotel ON booking.booking_hotel_id = hotel.hotel_id INNER JOIN room ON booking.booking_room_id = room.room_id")
    fun getBookingInfo(): LiveData<List<BookingInfo>>


    @Query("DELETE FROM booking")
    fun deleteAllBookings()

    @RawQuery
    fun resetAutoIncrement(query: SupportSQLiteQuery): Int

}
