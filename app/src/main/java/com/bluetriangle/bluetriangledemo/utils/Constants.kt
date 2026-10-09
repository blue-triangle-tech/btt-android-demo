package com.bluetriangle.bluetriangledemo.utils

const val ADD_TO_CART_LIMIT = 3
const val DEFAULT_SITE_ID = "sdkdemo26621z" //"sdkdemo26621z"
const val MANUAL_TIMER_SEGMENT = "AndroidManualTimer"

enum class ProductsLayout { LIST, GRID }

/** How the products screen lays its items out. Flip this to switch the whole screen. */
val PRODUCTS_LAYOUT = ProductsLayout.GRID

/** Columns used when [PRODUCTS_LAYOUT] is [ProductsLayout.GRID]. */
const val PRODUCTS_GRID_SPAN_COUNT = 2

/**
 * Delay before "Recent Activity" auto-forwards from Order History to Favourites. Kept under the
 * SDK's 2 second screen grouping window so both screens are reported as one group.
 */
const val RECENT_ACTIVITY_FORWARD_DELAY_MS = 1000L
