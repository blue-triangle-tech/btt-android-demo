package com.bluetriangle.bluetriangledemo.utils

import com.bluetriangle.analytics.Timer
import com.bluetriangle.bluetriangledemo.data.Cart

/**
 * Custom timer that reports a Purchase Confirmation to Blue Triangle with the
 * actual cart details. Submitted once the checkout call has succeeded.
 */
object PurchaseConfirmationTimer {
    fun submit(cart: Cart, orderNumber: String) {
        val items = cart.items.orEmpty()
        val timer = Timer()
        timer.start()
        timer.setPageName("Confirmation")
        timer.setCartValue(cart.total)
        timer.setOrderNumber(orderNumber)
        timer.setCartCount(items.size)
        timer.setCartCountCheckout(items.sumOf { it.quantity }.toInt())
        timer.setOrderTime(System.currentTimeMillis()) // optional
        timer.submit()
    }
}
