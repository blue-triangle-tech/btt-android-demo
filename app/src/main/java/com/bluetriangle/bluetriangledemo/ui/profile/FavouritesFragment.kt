package com.bluetriangle.bluetriangledemo.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.bluetriangle.bluetriangledemo.TrackedFragment
import com.bluetriangle.bluetriangledemo.adapters.FavouriteAdapter
import com.bluetriangle.bluetriangledemo.data.ProductAssetsRepository
import com.bluetriangle.bluetriangledemo.databinding.FragmentFavouritesBinding

private const val FAVOURITES_COLUMN_COUNT = 2

class FavouritesFragment : TrackedFragment() {

    private var _binding: FragmentFavouritesBinding? = null

    private val binding get() = _binding!!

    override fun getPageName() = "FavouritesFragmentManualTimer"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)

        val favouriteAdapter = FavouriteAdapter(requireContext())
        binding.favouritesList.apply {
            layoutManager = GridLayoutManager(context, FAVOURITES_COLUMN_COUNT)
            adapter = favouriteAdapter
        }
        favouriteAdapter.submitList(ProductAssetsRepository.favourites(requireContext()))

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
