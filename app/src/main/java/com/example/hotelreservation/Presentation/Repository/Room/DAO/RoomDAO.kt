package com.example.hotelreservation.Presentation.Repository.Room.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.hotelreservation.Presentation.Repository.Model.Room

@Dao
interface RoomDAO {

    @Insert
    fun addRoom(room: Room)

    @Update
    fun updateRoom(room: Room)

    @Delete
    fun deleteRoom(room: Room)

    @Query("SELECT * FROM room")
    fun getAllRooms(): LiveData<List<Room>>

    @Query("SELECT * FROM room WHERE room_id = :id")
    fun getRoomById(id: Int): LiveData<List<Room>>

    @Query("SELECT * FROM room WHERE room_hotel_id = :hotelId")
    fun getRoomsByHotel(hotelId: Int): LiveData<List<Room>>

    @Query("DELETE FROM room")
    fun deleteAllRooms()

    @RawQuery
    fun resetAutoIncrement(query: SupportSQLiteQuery): Int
}
