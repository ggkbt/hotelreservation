package com.example.hotelreservation.Presentation.Repository.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val name: String,
    val phoneNumber: String?,
    val email: String?
) : Parcelable