package com.example.hotelreservation.DI

import android.app.Application
import com.example.hotelreservation.Presentation.Repository.Mock.MockBase
import com.example.hotelreservation.Presentation.Repository.RepositoryInterface
import com.example.hotelreservation.Presentation.Repository.Room.ReservationRepository

class ServiceLocator private constructor() {
    private var reservationRepository: RepositoryInterface? = null
    companion object {
        private var instance: ServiceLocator? = null

        fun getInstance(): ServiceLocator {
            if (instance == null) {
                instance = ServiceLocator()
            }
            return instance!!
        }
    }


    fun initBase(application: Application) {
        if (reservationRepository == null) {
            reservationRepository = ReservationRepository(application)
        }
    }

    fun getRepository(): RepositoryInterface {
        if (reservationRepository == null) {
            reservationRepository = MockBase()
        }
        return reservationRepository as RepositoryInterface
    }



}
