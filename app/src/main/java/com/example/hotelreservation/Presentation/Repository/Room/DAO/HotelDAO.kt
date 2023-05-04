package com.example.hotelreservation.Presentation.Repository.Room.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.hotelreservation.Presentation.Repository.Model.Hotel

@Dao
interface HotelDAO {

    @Insert
    fun addHotel(hotel: Hotel)

    @Update
    fun updateHotel(hotel: Hotel)

    @Delete
    fun deleteHotel(hotel: Hotel)

    @Query("SELECT * FROM hotel")
    fun getAllHotels(): LiveData<List<Hotel>>

    @Query("SELECT * FROM hotel WHERE hotel_id = :id")
    fun getHotelById(id: Int): LiveData<List<Hotel>>

    @Query("DELETE FROM hotel")
    fun deleteAllHotels()

    @RawQuery
    fun resetAutoIncrement(query: SupportSQLiteQuery): Int

}
