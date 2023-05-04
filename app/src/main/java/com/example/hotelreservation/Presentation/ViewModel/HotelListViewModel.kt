package com.example.hotelreservation.Presentation.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hotelreservation.DI.ServiceLocator
import com.example.hotelreservation.Presentation.Repository.Model.Hotel

class HotelListViewModel : ViewModel() {


    fun deleteHotel(hotel: Hotel) {
        ServiceLocator.getInstance().getRepository().deleteHotel(hotel)
    }

    fun getHotelList() : LiveData<List<Hotel>> {
        return ServiceLocator.getInstance().getRepository().getAllHotels()
    }

}
