package com.example.hotelreservation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hotelreservation.DI.ServiceLocator
import com.example.hotelreservation.Presentation.Repository.Model.Hotel
import com.example.hotelreservation.Presentation.Repository.Model.Room
import com.example.hotelreservation.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        ServiceLocator.getInstance().initBase(application)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNav.setupWithNavController(navController)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_hotels -> {
                    navController.navigate(R.id.hotelListFragment)
                    true
                }
                R.id.navigation_bookings -> {
                    navController.navigate(R.id.bookingListFragment)
                    true
                }
                else -> false
            }
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.hotelListFragment -> {
                    bottomNav.menu.findItem(R.id.navigation_hotels).isChecked = true
                }
                R.id.bookingListFragment -> {
                    bottomNav.menu.findItem(R.id.navigation_bookings).isChecked = true
                }

            }
        }
        val listOfHotels = listOf(
            Hotel(1, "Marriott Hotel", "123 Main St", 15),
            Hotel(2, "Hilton Hotel", "456 Park Ave", 20),
            Hotel(3, "Westin Hotel", "789 Broadway", 25),
            Hotel(4, "Holiday Inn", "321 Elm St", 10)
        )
//        for (hotel in listOfHotels) {
//            ServiceLocator.getInstance().getRepository().deleteHotel(hotel)
//        }
//
//        for (hotel in listOfHotels) {
//            ServiceLocator.getInstance().getRepository().addHotel(hotel)
//        }


//        Log.d("TAG123", ServiceLocator.getInstance().getRepository().getAllHotels().toString())

//        ServiceLocator.getInstance().getRepository().deleteAllHotels()
        //ServiceLocator.getInstance().getRepository().deleteAllRooms()


//        val rooms = mutableListOf<Room>()
//        val roomTypes = listOf("Standard", "Deluxe", "Suite")
//        val random = Random()
//
//        for (hotelId in 1..4) {
//            for (roomNumber in 1..10) {
//                val type = roomTypes[random.nextInt(roomTypes.size)]
//                val price = when (type) {
//                    "Standard" -> BigDecimal.valueOf((random.nextInt(11) + 10).toLong(), 2) * BigDecimal.valueOf(100)
//                    "Deluxe" -> BigDecimal.valueOf((random.nextInt(10) + 21).toLong(), 2) * BigDecimal.valueOf(100)
//                    else -> BigDecimal.valueOf((random.nextInt(10) + 31).toLong(), 2) * BigDecimal.valueOf(100)
//                }
//                val room = Room(0, hotelId, "Room $roomNumber", type, price.toDouble())
//                ServiceLocator.getInstance().getRepository().addRoom(room)
//                rooms.add(room)
//            }
//        }



    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}

