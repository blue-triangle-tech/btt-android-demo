package com.bluetriangle.bluetriangledemo.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluetriangle.bluetriangledemo.TrackedFragment
import com.bluetriangle.bluetriangledemo.adapters.OrderAdapter
import com.bluetriangle.bluetriangledemo.data.DummyProfileData
import com.bluetriangle.bluetriangledemo.databinding.FragmentOrderHistoryBinding

class OrderHistoryFragment : TrackedFragment() {

    private var _binding: FragmentOrderHistoryBinding? = null

    private val binding get() = _binding!!

    override fun getPageName() = "OrderHistoryFragmentManualTimer"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentOrderHistoryBinding.inflate(inflater, container, false)

        val orderAdapter = OrderAdapter(requireContext())
        binding.ordersList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = orderAdapter
        }
        orderAdapter.submitList(DummyProfileData.orders)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
