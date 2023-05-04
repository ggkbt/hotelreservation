package com.example.hotelreservation.Presentation.Repository.Model

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE

@Entity(tableName = "room",
    foreignKeys = [ForeignKey(entity = Hotel::class,
        parentColumns = ["hotel_id"],
        childColumns = ["room_hotel_id"],
        onDelete = CASCADE)],
    indices = [
        Index("room_hotel_id")
    ])
data class Room(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "room_id")
    val id: Int,
    @ColumnInfo(name = "room_hotel_id")
    val hotelId: Int,
    @ColumnInfo(name = "room_number")
    val roomNumber: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "price")
    val price: Double
)
