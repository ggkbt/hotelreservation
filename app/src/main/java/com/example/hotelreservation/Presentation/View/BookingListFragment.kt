package com.example.hotelreservation.Presentation.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelreservation.Presentation.Repository.Model.BookingInfo
import com.example.hotelreservation.Presentation.View.Adapters.BookingListAdapter
import com.example.hotelreservation.Presentation.ViewModel.BookingListViewModel
import com.example.hotelreservation.databinding.BookingListFragmentBinding

class BookingListFragment : Fragment() {
    private lateinit var binding: BookingListFragmentBinding
    private lateinit var viewModel: BookingListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BookingListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[BookingListViewModel::class.java]
        viewModel.getBookingsWithHotels().observe(viewLifecycleOwner) { bookingsAndHotels ->
            setUpRecyclerView(bookingsAndHotels)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setUpRecyclerView(bookingInfo: List<BookingInfo>) {
        val adapter = BookingListAdapter(bookingInfo)
        val layoutManager = LinearLayoutManager(context)
        binding.bookingList.layoutManager = layoutManager
        binding.bookingList.adapter = adapter
        adapter.setOnItemClickListener { info ->
            val action = BookingListFragmentDirections.actionBookingListFragmentToBookingFragment(info)
            findNavController().navigate(action)
        }
        adapter.setOnItemCloseListener { info ->
            viewModel.deleteBooking(info.booking)
        }
    }

}