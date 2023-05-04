package com.example.hotelreservation.Presentation.Repository.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hotel")
data class Hotel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "hotel_id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "address")
    val address: String,
    @ColumnInfo(name = "room_count")
    val roomCount: Int
)