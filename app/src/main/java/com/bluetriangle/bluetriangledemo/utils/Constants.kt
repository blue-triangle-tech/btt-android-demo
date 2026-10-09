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

/** How long the main thread is blocked per Order History scroll hitch. */
const val ORDER_HISTORY_SCROLL_HITCH_MS = 150L

/** Minimum gap between Order History scroll hitches, so scrolling stutters rather than freezes. */
const val ORDER_HISTORY_SCROLL_HITCH_INTERVAL_MS = 300L

/**
 * How long the main thread is blocked per Favourites scroll hang. Above the SDK's 750 ms hang
 * threshold so it is reported as a hang.
 */
const val FAVOURITES_SCROLL_HANG_MS = 1500L

/** Minimum gap between Favourites scroll hangs, so the list can still be scrolled in between. */
const val FAVOURITES_SCROLL_HANG_INTERVAL_MS = 1000L
