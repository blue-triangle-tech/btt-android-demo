package com.bluetriangle.bluetriangledemo.utils

const val ADD_TO_CART_LIMIT = 3
const val DEFAULT_SITE_ID = "sdkdemo26621z" //"sdkdemo26621z"
const val MANUAL_TIMER_SEGMENT = "AndroidManualTimer"

enum class ProductsLayout { LIST, GRID }

/** How the products screen lays its items out. Flip this to switch the whole screen. */
val PRODUCTS_LAYOUT = ProductsLayout.GRID

/** Columns used when [PRODUCTS_LAYOUT] is [ProductsLayout.GRID]. */
const val PRODUCTS_GRID_SPAN_COUNT = 2