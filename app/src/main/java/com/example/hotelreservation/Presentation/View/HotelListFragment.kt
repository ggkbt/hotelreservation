package com.example.hotelreservation.Presentation.View

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelreservation.Presentation.Repository.Model.Hotel
import com.example.hotelreservation.Presentation.View.Adapters.HotelListAdapter
import com.example.hotelreservation.Presentation.ViewModel.HotelListViewModel
import com.example.hotelreservation.databinding.HotelListFragmentBinding

class HotelListFragment : Fragment() {
    private lateinit var binding: HotelListFragmentBinding
    private lateinit var viewModel: HotelListViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HotelListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[HotelListViewModel::class.java]
        viewModel.getHotelList().observe(viewLifecycleOwner) { hotels ->
            Log.d("TAG123", hotels.toString())
            setUpRecyclerView(hotels)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setUpRecyclerView(hotels: List<Hotel>) {
        val adapter = HotelListAdapter(hotels)
        val layoutManager = LinearLayoutManager(context)
        binding.hotelList.layoutManager = layoutManager
        binding.hotelList.adapter = adapter
    }

}