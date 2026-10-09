package com.bluetriangle.bluetriangledemo.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bluetriangle.bluetriangledemo.R
import com.bluetriangle.bluetriangledemo.TrackedFragment
import com.bluetriangle.bluetriangledemo.data.DummyProfileData
import com.bluetriangle.bluetriangledemo.data.ProductAssetsRepository
import com.bluetriangle.bluetriangledemo.databinding.FragmentProfileBinding

class ProfileFragment : TrackedFragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    override fun getPageName() = "ProfileFragmentManualTimer"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val profile = DummyProfileData.profile
        binding.apply {
            profileInitials.text = profile.initials
            profileName.text = profile.name
            profileEmail.text = profile.email
            profileTier.text = profile.loyaltyTier
            //profilePhoneValue.text = profile.phone
            profileMemberSinceValue.text = profile.memberSince

            orderHistorySubtitle.text =
                getString(R.string.profile_orders_placed, DummyProfileData.orders.size)
            favouritesSubtitle.text = getString(
                R.string.profile_saved_items,
                ProductAssetsRepository.favourites(requireContext()).size
            )

            orderHistoryCard.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileToOrderHistory())
            }
            favouritesCard.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileToFavourites())
            }
            // Opens Order History, which then forwards to Favourites within the SDK's screen
            // grouping window, so both launches are reported as a single group.
            recentActivityCard.setOnClickListener {
                findNavController().navigate(
                    ProfileFragmentDirections.actionProfileToOrderHistory(forwardToFavourites = true)
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
