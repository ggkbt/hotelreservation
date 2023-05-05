package com.example.hotelreservation.Presentation.View

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.hotelreservation.Presentation.Repository.Model.BookingInfo
import com.example.hotelreservation.Presentation.Repository.Model.Booking
import com.example.hotelreservation.Presentation.Repository.Model.Contact
import com.example.hotelreservation.Presentation.View.Adapters.RoomSpinnerAdapter
import com.example.hotelreservation.Presentation.ViewModel.BookingViewModel
import com.example.hotelreservation.R
import com.example.hotelreservation.databinding.BookingFragmentBinding
import java.util.*

class BookingFragment : Fragment() {
    private lateinit var binding: BookingFragmentBinding
    private var hotelId: Int? = null
    private var bookingInfo: BookingInfo? = null
    private var roomAdapter: RoomSpinnerAdapter? = null
    private lateinit var viewModel: BookingViewModel
    private var selectedRoomId: Int? = null
    private var editMode: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            hotelId = it.getInt("hotelId", -1)
            bookingInfo = it.getParcelable("bookingInfo")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = BookingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Contact>("contactFromProvider")
            ?.observe(viewLifecycleOwner) { contact ->
                contact?.let {
                    with(binding) {
                        guestName.setText(it.name)
                        guestPhone.setText(it.phoneNumber ?: "")
                        guestEmail.setText(it.email ?: "")
                    }
                }
            }
        viewModel = ViewModelProvider(this)[BookingViewModel::class.java]

        if (bookingInfo != null) {
            editMode = true
            with(binding) {
                hotelName.text = bookingInfo?.hotel?.name
                guestName.setText(bookingInfo?.booking?.guestName)
                guestEmail.setText(bookingInfo?.booking?.guestEmail)
                guestPhone.setText(bookingInfo?.booking?.guestPhone)
                checkInDate.text = bookingInfo?.booking?.checkInDate
                checkOutDate.text = bookingInfo?.booking?.checkOutDate
            }

            viewModel.isCheckInSelected = true
            viewModel.isCheckOutSelected = true
            bookingInfo?.hotel?.id?.let {
                fillRoomSpinner(it)
            }
        }

        if (hotelId != -1) {
            hotelId?.let {
                viewModel.getHotelById(it).observe(viewLifecycleOwner) { hotelList ->
                    binding.hotelName.text = hotelList[0].name
                }
                fillRoomSpinner(it)
            }
        }


        binding.checkInDate.setOnClickListener {
            showDatePickerDialog { year, month, dayOfMonth ->
                // Update button text with selected date
                binding.checkInDate.text = "$dayOfMonth/${month + 1}/$year"
                viewModel.isCheckInSelected = true
            }
        }

        binding.checkOutDate.setOnClickListener {
            showDatePickerDialog { year, month, dayOfMonth ->
                // Update button text with selected date
                binding.checkOutDate.text = "$dayOfMonth/${month + 1}/$year"
                viewModel.isCheckOutSelected = true
            }
        }

        binding.roomSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                selectedRoomId = roomAdapter?.getItem(position)?.id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }

        binding.saveButton.setOnClickListener {
            val guestName = binding.guestName.text.toString()
            val guestEmail = binding.guestEmail.text.toString()
            val guestPhone = binding.guestPhone.text.toString()
            if (guestName.isEmpty() || guestEmail.isEmpty() || guestPhone.isEmpty() || !viewModel.isCheckInSelected || !viewModel.isCheckOutSelected) {
                Toast.makeText(
                    requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            val booking = selectedRoomId?.let { selectedRoomId ->
                getHotelId()?.let { hotelId ->
                    getBookingId()?.let { bookingId ->
                        Booking(
                            id = bookingId,
                            guestName = binding.guestName.text.toString(),
                            guestEmail = binding.guestEmail.text.toString(),
                            guestPhone = binding.guestPhone.text.toString(),
                            hotelId = hotelId,
                            roomId = selectedRoomId,
                            checkInDate = binding.checkInDate.text.toString(),
                            checkOutDate = binding.checkOutDate.text.toString()
                        )
                    }
                }
            }

            if (booking != null && !editMode) {
                viewModel.addBooking(booking)
            }
            if (booking != null && editMode) {
                viewModel.updateBooking(booking)
            }
            Navigation.findNavController(it).navigate(R.id.bookingListFragment)
        }
        binding.openContacts.setOnClickListener {
            with(viewModel) {
                checkInDate = binding.checkInDate.text as String
                checkOutDate = binding.checkOutDate.text as String
                roomPosition = binding.roomSpinner.selectedItemPosition
                isCheckInSelected = isCheckInSelected
            }
            findNavController().navigate(BookingFragmentDirections.actionBookingFragmentToContactListFragment())
        }
        with(viewModel) {
            checkInDate?.let {
                binding.checkInDate.text = it
            }
            checkOutDate?.let {
                binding.checkOutDate.text = it
            }
        }
    }

    private fun getHotelId(): Int? {
        return if (editMode) {
            bookingInfo?.hotel?.id
        } else {
            hotelId
        }
    }

    private fun getBookingId(): Int? {
        return if (editMode) {
            bookingInfo?.booking?.id
        } else 0
    }

    private fun fillRoomSpinner(hotelId: Int) {
        viewModel.getRoomsByHotel(hotelId).observe(viewLifecycleOwner) { rooms ->
            roomAdapter = RoomSpinnerAdapter(
                requireContext(), android.R.layout.simple_spinner_item, rooms
            )
            roomAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.roomSpinner.adapter = roomAdapter
            bookingInfo?.let { info ->
                val roomIndex = rooms.indexOfFirst { it.id == info.room.id }
                binding.roomSpinner.setSelection(roomIndex)
            }
            viewModel.roomPosition?.let {
                binding.roomSpinner.setSelection(it)
            }
        }
    }

    private fun showDatePickerDialog(onDateSelected: (Int, Int, Int) -> Unit) {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            onDateSelected(year, month, dayOfMonth)
        }, currentYear, currentMonth, currentDayOfMonth)

        datePickerDialog.show()
    }


}