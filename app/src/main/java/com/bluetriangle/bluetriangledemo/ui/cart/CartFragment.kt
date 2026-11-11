package com.bluetriangle.bluetriangledemo.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bluetriangle.bluetriangledemo.TrackedFragment
import com.bluetriangle.bluetriangledemo.adapters.CartItemAdapter
import com.bluetriangle.bluetriangledemo.databinding.FragmentCartBinding
import com.bluetriangle.bluetriangledemo.layout.WebCheckOutActivity
import com.bluetriangle.bluetriangledemo.utils.AlertDialogState
import com.bluetriangle.bluetriangledemo.utils.AlertView
import com.bluetriangle.bluetriangledemo.utils.showAlert
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : TrackedFragment(), AlertView {

    private var _binding: FragmentCartBinding? = null

    override fun getPageName() = "CartFragmentManualTimer"

    private val binding get() = _binding!!

    private lateinit var cartViewModel: CartViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        cartViewModel.errorHandler.alertView = this

        val cartItemAdapter = CartItemAdapter(requireContext(), {
            cartViewModel.removeCartItem(it)
        }, {
            cartViewModel.reduceQuantity(it)
        }) {
            cartViewModel.increaseQuantity(it)
        }

        binding.productsList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = cartItemAdapter
        }

        cartViewModel.cart.observe(viewLifecycleOwner) { cart ->
            cartItemAdapter.submitList(cart?.items?: emptyList())
            binding.webCheckoutButton.isVisible = !cart?.items.isNullOrEmpty()
        }

        binding.checkoutButton.setOnClickListener {
            cartViewModel.handleCheckoutCrash()
            val checkoutId = cartViewModel.submitPurchaseConfirmation()
            findNavController().navigate(CartFragmentDirections.actionCartToCheckout(checkoutId))
        }

        binding.webCheckoutButton.setOnClickListener {
            val cart = cartViewModel.cart.value ?: return@setOnClickListener
            startActivity(
                Intent(requireContext(), WebCheckOutActivity::class.java)
                    .putExtra(WebCheckOutActivity.CART_ID, cart.id)
            )
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        cartViewModel.refreshCart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showAlert(alertDialogState: AlertDialogState) {
        requireContext().showAlert(alertDialogState)
    }
}