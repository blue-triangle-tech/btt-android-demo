package com.bluetriangle.bluetriangledemo.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bluetriangle.bluetriangledemo.R
import com.bluetriangle.bluetriangledemo.data.Order
import com.bluetriangle.bluetriangledemo.databinding.ListItemOrderBinding
import com.bluetriangle.bluetriangledemo.utils.loadImage

private const val COLOR_DELIVERED = 0xFF2E7D32.toInt()
private const val COLOR_CANCELLED = 0xFFC62828.toInt()
private const val COLOR_IN_PROGRESS = 0xFFEF6C00.toInt()

class OrderAdapter(context: Context) :
    ListAdapter<Order, RecyclerView.ViewHolder>(OrderDiffCallback()) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OrderViewHolder(ListItemOrderBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? OrderViewHolder)?.bind(getItem(position))
    }

    class OrderViewHolder(private val binding: ListItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            binding.apply {
                orderId.text = order.id
                orderSummary.text = order.itemSummary
                orderMeta.text = String.format(
                    "%s  •  %d item(s)  •  $%.2f",
                    order.placedOn,
                    order.itemCount,
                    order.total
                )
                orderImage.loadImage(order.image, R.drawable.ic_error, imageProgress)

                val statusColor = when (order.status) {
                    "Delivered" -> COLOR_DELIVERED
                    "Cancelled", "Returned" -> COLOR_CANCELLED
                    else -> COLOR_IN_PROGRESS
                }
                orderStatus.text = order.status
                orderStatus.setTextColor(statusColor)
                orderStatus.backgroundTintList =
                    android.content.res.ColorStateList.valueOf(
                        Color.argb(
                            38,
                            Color.red(statusColor),
                            Color.green(statusColor),
                            Color.blue(statusColor)
                        )
                    )
            }
        }
    }
}

private class OrderDiffCallback : DiffUtil.ItemCallback<Order>() {

    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}
