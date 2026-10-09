package com.bluetriangle.bluetriangledemo.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluetriangle.bluetriangledemo.TrackedFragment
import com.bluetriangle.bluetriangledemo.adapters.OrderAdapter
import com.bluetriangle.bluetriangledemo.data.DummyProfileData
import com.bluetriangle.bluetriangledemo.databinding.FragmentOrderHistoryBinding
import com.bluetriangle.bluetriangledemo.utils.RECENT_ACTIVITY_FORWARD_DELAY_MS
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OrderHistoryFragment : TrackedFragment() {

    private var _binding: FragmentOrderHistoryBinding? = null

    private val binding get() = _binding!!

    private val args: OrderHistoryFragmentArgs by navArgs()

    // Forward only on first arrival, not when returning here from Favourites via Back.
    private var pendingForwardToFavourites = false

    override fun getPageName() = "OrderHistoryFragmentManualTimer"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pendingForwardToFavourites = args.forwardToFavourites && savedInstanceState == null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentOrderHistoryBinding.inflate(inflater, container, false)

        val orderAdapter = OrderAdapter(requireContext())
        binding.ordersList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = orderAdapter
        }
        orderAdapter.submitList(DummyProfileData.orders)

        if (pendingForwardToFavourites) {
            pendingForwardToFavourites = false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(RECENT_ACTIVITY_FORWARD_DELAY_MS)
                findNavController().navigate(
                    OrderHistoryFragmentDirections.actionOrderHistoryToFavourites()
                )
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
