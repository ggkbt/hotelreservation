package com.example.hotelreservation.Presentation.Repository.Mock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.hotelreservation.Presentation.Repository.Model.BookingInfo
import com.example.hotelreservation.Presentation.Repository.Model.Booking
import com.example.hotelreservation.Presentation.Repository.Model.Hotel
import com.example.hotelreservation.Presentation.Repository.Model.Room
import com.example.hotelreservation.Presentation.Repository.RepositoryInterface

class MockBase : RepositoryInterface {

    private var allHotels = MutableLiveData<List<Hotel>>()
    private var allBookings = MutableLiveData<List<Booking>>()
    private var allRooms = MutableLiveData<List<Room>>()
    private var listAllHotels: List<Hotel> = listOf(
        Hotel(1, "Hotel 1", "Street 1", 10),
        Hotel(2, "Hotel 2", "Street 2", 15),
        Hotel(3, "Hotel 3", "Street 3", 20)
    )
    var listAllBookings: List<Booking> = listOf(
        Booking(1, "John Smith", "john.smith@gmail.com", "555-555-5555", 1, 1, "20 January 2023", "25 January 2023"),
        Booking(2, "Jane Doe", "jane.doe@gmail.com", "555-555-5556", 2, 2, "10 March 2023", "15 March 2023")
    )
    private var listAllRooms: List<Room> = listOf(
        Room(1, 1, "1", "Standard", 1300.0),
        Room(2, 1, "2", "Deluxe", 2500.0),
        Room(3, 2, "1", "Standard", 1000.0),
        Room(4, 2, "2", "Deluxe", 1000.0),
        Room(5, 3, "1", "Standard", 1000.0),
        Room(6, 3, "2", "Deluxe", 1000.0)
    )

    init {
        allHotels = MutableLiveData<List<Hotel>>(listAllHotels)
        allBookings = MutableLiveData<List<Booking>>(listAllBookings)
        allRooms = MutableLiveData<List<Room>>(listAllRooms)
    }

    override fun getAllHotels(): MutableLiveData<List<Hotel>> {
        return allHotels
    }

    override fun getAllBookings(): MutableLiveData<List<Booking>> {
        return allBookings
    }


    override fun addHotel(hotel: Hotel) {
        listAllHotels = listAllHotels.plus(hotel)
        allHotels.value = listAllHotels
    }

    override fun deleteHotel(hotel: Hotel) {
        listAllHotels = listAllHotels.minus(hotel)
        allHotels.value = listAllHotels
    }

//    override fun getHotelById(hotelId: Int): MutableLiveData<Hotel> {
//        var specificHotel = MutableLiveData<Hotel>()
//        for(hotel in listAllHotels) {
//            if(hotel.id == hotelId) {
//                specificHotel = MutableLiveData<Hotel>(hotel)
//            }
//        }
//        return specificHotel
//    }

    override fun addBooking(booking: Booking) {
        listAllBookings = listAllBookings.plus(booking)
        allBookings.value = listAllBookings
    }

    override fun updateBooking(booking: Booking) {
        //
    }

    override fun getBookingById(bookingId: Int): LiveData<List<Booking>> {
        return Transformations.map(allBookings) { bookings ->
            bookings.filter { it.id == bookingId }
        }
    }

    override fun deleteBooking(booking: Booking) {
        listAllBookings = listAllBookings.minus(booking)
        allBookings.value = listAllBookings
    }

    override fun addRoom(room: Room) {
        //
    }

    override fun deleteRoom(room: Room) {
        //
    }

    override fun getRoomById(roomId: Int): LiveData<List<Room>> {
        return Transformations.map(allRooms) { rooms ->
            rooms.filter { it.id == roomId }
        }
    }

    override fun getBookingInfo(): LiveData<List<BookingInfo>> {
        return Transformations.map(allBookings) { bookings ->
            val hotelsById = allHotels.value?.associateBy { it.id } ?: emptyMap()
            val roomsById = allRooms.value?.associateBy { it.id } ?: emptyMap()
            bookings.mapNotNull { booking ->
                hotelsById[booking.hotelId]?.let { hotel ->
                    roomsById[booking.roomId]?.let { room ->
                        BookingInfo(booking, hotel, room)
                    }
                }
            }
        }
    }



    override fun getRoomsByHotel(hotelId: Int): MutableLiveData<List<Room>> {
        var listOfHotelRooms: List<Room> = listOf()
        for(room in listAllRooms) {
            if(room.hotelId == hotelId) {
                listOfHotelRooms = listOfHotelRooms.plus(room)
            }
        }
        return MutableLiveData<List<Room>>(listOfHotelRooms)
    }

    override fun getHotelById(hotelId: Int): LiveData<List<Hotel>> {
        return Transformations.map(allHotels) { hotels ->
            hotels.filter { it.id == hotelId }
        }
    }

    override fun deleteAllBookings() {
        //
    }

    override fun deleteAllHotels() {
        //
    }

    override fun deleteAllRooms() {
        //
    }

//    override fun getHotelNameAndAddress(hotelId: Int): MutableLiveData<Hotel> {
//        var specificHotel = MutableLiveData<Hotel>()
//        for(hotel in listAllHotels) {
//            if(hotel.id == hotelId) {
//                specificHotel = MutableLiveData<Hotel>(hotel)
//            }
//        }
//        return specificHotel
//    }
//
//    override fun getRoomById(roomId: Int): LiveData<Room> {
//        var specificRoom = MutableLiveData<Room>()
//        for(room in listAllRooms) {
//            if(room.id == roomId) {
//                specificRoom = MutableLiveData<Room>(room)
//            }
//        }
//        return specificRoom
//    }


}
