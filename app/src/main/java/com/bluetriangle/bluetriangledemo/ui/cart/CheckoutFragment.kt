package com.bluetriangle.bluetriangledemo.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bluetriangle.bluetriangledemo.databinding.FragmentCheckoutBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CheckoutFragment : Fragment() {

    private var _binding: FragmentCheckoutBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val checkoutViewModel = ViewModelProvider(this).get(CheckoutViewModel::class.java)

        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        checkoutViewModel.cart.observe(viewLifecycleOwner) {
            it?.let { cart ->
                binding.orderNumber.text = cart.confirmation
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}