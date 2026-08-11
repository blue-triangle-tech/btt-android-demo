package com.bluetriangle.bluetriangledemo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bluetriangle.bluetriangledemo.R
import com.bluetriangle.bluetriangledemo.data.FavouriteItem
import com.bluetriangle.bluetriangledemo.databinding.ListItemFavouriteBinding
import com.bluetriangle.bluetriangledemo.utils.loadImage

private const val COLOR_IN_STOCK = 0xFF2E7D32.toInt()
private const val COLOR_OUT_OF_STOCK = 0xFFC62828.toInt()

class FavouriteAdapter(context: Context) :
    ListAdapter<FavouriteItem, RecyclerView.ViewHolder>(FavouriteDiffCallback()) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavouriteViewHolder(ListItemFavouriteBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? FavouriteViewHolder)?.bind(getItem(position))
    }

    class FavouriteViewHolder(private val binding: ListItemFavouriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavouriteItem) {
            binding.apply {
                favouriteCategory.text = item.category
                favouriteName.text = item.name
                favouritePrice.text = String.format("$%.2f", item.price)
                favouriteImage.loadImage(item.image, R.drawable.ic_error, imageProgress)
                favouriteStock.setText(
                    if (item.inStock) R.string.favourite_in_stock else R.string.favourite_out_of_stock
                )
                favouriteStock.setTextColor(
                    if (item.inStock) COLOR_IN_STOCK else COLOR_OUT_OF_STOCK
                )
            }
        }
    }
}

private class FavouriteDiffCallback : DiffUtil.ItemCallback<FavouriteItem>() {

    override fun areItemsTheSame(oldItem: FavouriteItem, newItem: FavouriteItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FavouriteItem, newItem: FavouriteItem): Boolean {
        return oldItem == newItem
    }
}
