package com.example.hotelreservation.DI

import android.app.Application
import android.util.Log
import com.example.hotelreservation.Presentation.Repository.Mock.MockBase
import com.example.hotelreservation.Presentation.Repository.RepositoryInterface
import com.example.hotelreservation.Presentation.Repository.Room.ReservationRepository

class ServiceLocator private constructor() {
    private var reservationRepository: RepositoryInterface? = null
    companion object {
        private var instance: ServiceLocator? = null

        fun getInstance(): ServiceLocator {
            //Log.d("TAG123", "Creating instance (or created) " + instance.toString())
            if (instance == null) {
                instance = ServiceLocator()
            }
            //Log.d("TAG123", "Instance created " + instance.toString())
            return instance!!
        }
    }


    fun initBase(application: Application) {
        //Log.d("TAG123", "reservationRepository in initBase before int " + reservationRepository.toString())
        if (reservationRepository == null) {
            reservationRepository = ReservationRepository(application)
            Log.d("TAG123", "INIT BASE " + reservationRepository.toString())
        }
        //Log.d("TAG123", "reservationRepository in iniBase after if " + reservationRepository.toString())
    }

    fun getRepository(): RepositoryInterface {
        //Log.d("TAG123", "reservationRepository in getRepository before if " + reservationRepository.toString())
        if (reservationRepository == null) {
            reservationRepository = MockBase()
            Log.d("TAG123", "GET REPO CREATED MOCK BASE " + reservationRepository.toString())
        }
        //Log.d("TAG123", "reservationRepository in getRepository after if " + reservationRepository.toString())
        return reservationRepository as RepositoryInterface
    }



}
