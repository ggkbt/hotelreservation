package com.example.hotelreservation.Presentation.Repository.Room

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.hotelreservation.Presentation.Repository.Model.BookingInfo
import com.example.hotelreservation.Presentation.Repository.Model.Booking
import com.example.hotelreservation.Presentation.Repository.Model.Hotel
import com.example.hotelreservation.Presentation.Repository.Model.Room
import com.example.hotelreservation.Presentation.Repository.RepositoryInterface
import com.example.hotelreservation.Presentation.Repository.Room.DAO.BookingDAO
import com.example.hotelreservation.Presentation.Repository.Room.DAO.HotelDAO
import com.example.hotelreservation.Presentation.Repository.Room.DAO.RoomDAO

class ReservationRepository(application: Application): RepositoryInterface {

    private val hotelDao: HotelDAO
    private val bookingDao: BookingDAO
    private val roomDao: RoomDAO
    private val allHotels: LiveData<List<Hotel>>
    private val allRooms: LiveData<List<Room>>
    private val allBookings: LiveData<List<Booking>>

    init {
        val database = ReservationRoomDatabase.getDatabase(application)
        hotelDao = database.hotelDao()
        bookingDao = database.bookingDao()
        roomDao = database.roomDao()
        allHotels = hotelDao.getAllHotels()
        allRooms = roomDao.getAllRooms()
        allBookings = bookingDao.getAllBookings()
    }

    override fun getAllHotels(): LiveData<List<Hotel>> {
        return allHotels
    }

    override fun getAllBookings(): LiveData<List<Booking>> {
        return allBookings
    }

    override fun addHotel(hotel: Hotel) {
        ReservationRoomDatabase.databaseWriteExecutor.execute {
            hotelDao.addHotel(hotel)
        }
    }

    override fun deleteHotel(hotel: Hotel) {
        ReservationRoomDatabase.databaseWriteExecutor.execute {
            hotelDao.deleteHotel(hotel)
        }
    }

    override fun addBooking(booking: Booking) {
        ReservationRoomDatabase.databaseWriteExecutor.execute {
            bookingDao.addBooking(booking)
        }
    }

    override fun updateBooking(booking: Booking) {
        ReservationRoomDatabase.databaseWriteExecutor.execute {
            bookingDao.updateBooking(booking)
        }
    }

    override fun addRoom(room: Room) {
        ReservationRoomDatabase.databaseWriteExecutor.execute {
            roomDao.addRoom(room)
        }
    }

    override fun getBookingById(bookingId: Int): LiveData<List<Booking>> {
        return bookingDao.getBookingById(bookingId)
    }

    override fun getBookingInfo(): LiveData<List<BookingInfo>> {
        return bookingDao.getBookingInfo()
    }

    override fun deleteBooking(booking: Booking) {
        ReservationRoomDatabase.databaseWriteExecutor.execute {
            bookingDao.deleteBooking(booking)
        }
    }

    override fun getRoomById(roomId: Int): LiveData<List<Room>> {
        return roomDao.getRoomById(roomId)
    }

    override fun deleteRoom(room: Room) {
        ReservationRoomDatabase.databaseWriteExecutor.execute {
            roomDao.deleteRoom(room)
        }
    }

    override fun getRoomsByHotel(hotelId: Int): LiveData<List<Room>> {
        return roomDao.getRoomsByHotel(hotelId)
    }


    override fun getHotelById(hotelId: Int): LiveData<List<Hotel>> {
        return hotelDao.getHotelById(hotelId)
    }

    override fun deleteAllBookings() {
        val supportQuery = SimpleSQLiteQuery("DELETE FROM sqlite_sequence WHERE name='booking'")

        ReservationRoomDatabase.databaseWriteExecutor.execute {
            bookingDao.deleteAllBookings()
            bookingDao.resetAutoIncrement(supportQuery)
        }
    }

    override fun deleteAllHotels() {
        val supportQuery = SimpleSQLiteQuery("DELETE FROM sqlite_sequence WHERE name='hotel'")

        ReservationRoomDatabase.databaseWriteExecutor.execute {
            hotelDao.deleteAllHotels()
            hotelDao.resetAutoIncrement(supportQuery)
        }
    }

    override fun deleteAllRooms() {
        val supportQuery = SimpleSQLiteQuery("DELETE FROM sqlite_sequence WHERE name='room'")

        ReservationRoomDatabase.databaseWriteExecutor.execute {
            roomDao.deleteAllRooms()
            roomDao.resetAutoIncrement(supportQuery)
        }
    }




}
