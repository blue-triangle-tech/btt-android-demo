package com.bluetriangle.bluetriangledemo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bluetriangle.bluetriangledemo.R
import com.bluetriangle.bluetriangledemo.data.Product
import com.bluetriangle.bluetriangledemo.databinding.GridItemProductBinding
import com.bluetriangle.bluetriangledemo.databinding.ListItemProductBinding
import com.bluetriangle.bluetriangledemo.utils.PRODUCTS_LAYOUT
import com.bluetriangle.bluetriangledemo.utils.ProductsLayout
import com.bluetriangle.bluetriangledemo.utils.dp
import com.bluetriangle.bluetriangledemo.utils.loadImage
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class ProductAdapter(
    context: Context,
    private val layout: ProductsLayout = PRODUCTS_LAYOUT,
    private val productClickListener: (product: Product) -> Unit
) :
    ListAdapter<Product, RecyclerView.ViewHolder>(ProductDiffCallback()) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (layout) {
            ProductsLayout.LIST -> ProductRowViewHolder(
                ListItemProductBinding.inflate(layoutInflater, parent, false)
            )

            ProductsLayout.GRID -> ProductCellViewHolder(
                GridItemProductBinding.inflate(layoutInflater, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = getItem(position)
        val productViewHolder = holder as? ProductViewHolder
        productViewHolder?.bind(product)
        productViewHolder?.itemView?.setOnClickListener { _ ->
            productClickListener(product)
        }
    }

    abstract class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(product: Product)
    }

    class ProductRowViewHolder(private val binding: ListItemProductBinding) :
        ProductViewHolder(binding.root) {
        override fun bind(product: Product) {
            binding.apply {
                productName.text = String.format("%d : %s", product.id, product.name)
                productPrice.text = String.format("$%.2f", product.price)
                productImage.loadImage(product.image, R.drawable.ic_error, imageProgress)
            }
        }
    }

    class ProductCellViewHolder(private val binding: GridItemProductBinding) :
        ProductViewHolder(binding.root) {
        override fun bind(product: Product) {
            binding.apply {
                productName.text = String.format("%d : %s", product.id, product.name)
                productPrice.text = String.format("$%.2f", product.price)
                productImage.loadImage(product.image, R.drawable.ic_error, imageProgress)
            }
        }
    }
}

private class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}
