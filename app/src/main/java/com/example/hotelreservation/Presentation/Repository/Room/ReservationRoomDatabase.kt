package com.example.hotelreservation.Presentation.Repository.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hotelreservation.Presentation.Repository.Model.Booking
import com.example.hotelreservation.Presentation.Repository.Model.Hotel
import com.example.hotelreservation.Presentation.Repository.Room.DAO.BookingDAO
import com.example.hotelreservation.Presentation.Repository.Room.DAO.HotelDAO
import com.example.hotelreservation.Presentation.Repository.Room.DAO.RoomDAO
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import com.example.hotelreservation.Presentation.Repository.Model.Room as RoomModel

@Database(entities = [Hotel::class, Booking::class, RoomModel::class], version = 1)
abstract class ReservationRoomDatabase : RoomDatabase() {

    abstract fun hotelDao(): HotelDAO
    abstract fun bookingDao(): BookingDAO
    abstract fun roomDao(): RoomDAO

    companion object {

        @Volatile
        private var INSTANCE: ReservationRoomDatabase? = null

        private const val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        fun getDatabase(context: Context): ReservationRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReservationRoomDatabase::class.java,
                    "reservation_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
