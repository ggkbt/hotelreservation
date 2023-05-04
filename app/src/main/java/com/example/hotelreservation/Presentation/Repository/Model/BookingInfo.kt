package com.example.hotelreservation.Presentation.Repository.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.room.Embedded
import kotlinx.parcelize.RawValue

@Parcelize
data class BookingInfo(
    @Embedded val booking: @RawValue Booking,
    @Embedded val hotel: @RawValue Hotel,
    @Embedded val room: @RawValue Room
) : Parcelable
