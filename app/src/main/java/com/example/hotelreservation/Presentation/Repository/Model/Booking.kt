package com.example.hotelreservation.Presentation.Repository.Model

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE

@Entity(tableName = "booking",
    foreignKeys = [ForeignKey(
        entity = Room::class,
        parentColumns = ["room_id"],
        childColumns = ["booking_room_id"],
        onDelete = CASCADE
    ),
        ForeignKey(
            entity = Hotel::class,
            parentColumns = ["hotel_id"],
            childColumns = ["booking_hotel_id"],
            onDelete = CASCADE
        )],
    indices = [
        Index("booking_hotel_id"),
        Index("booking_room_id")
    ]
)
data class Booking(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "booking_id")
    val id: Int,
    @ColumnInfo(name = "guest_name")
    val guestName: String,
    @ColumnInfo(name = "guest_email")
    val guestEmail: String,
    @ColumnInfo(name = "guest_phone")
    val guestPhone: String,
    @ColumnInfo(name = "booking_hotel_id")
    val hotelId: Int,
    @ColumnInfo(name = "booking_room_id")
    val roomId: Int,
    @ColumnInfo(name = "check_in_date")
    val checkInDate: String,
    @ColumnInfo(name = "check_out_date")
    val checkOutDate: String
)
