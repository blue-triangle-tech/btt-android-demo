package com.bluetriangle.bluetriangledemo.data

import android.content.Context
import android.util.Log
import org.json.JSONArray

private const val LOG_TAG = "ProductAssets"
private const val PRODUCTS_ASSET = "products.json"

/**
 * Reads the bundled product catalog from `assets/products.json`. The favourites screens are
 * backed by this catalog so they stay in sync with the products the demo ships with, without
 * depending on the store API being reachable.
 */
object ProductAssetsRepository {

    @Volatile
    private var cachedProducts: List<Product>? = null

    fun products(context: Context): List<Product> {
        cachedProducts?.let { return it }
        return synchronized(this) {
            cachedProducts ?: readProducts(context).also { cachedProducts = it }
        }
    }

    fun favourites(context: Context): List<FavouriteItem> =
        products(context).map { it.toFavouriteItem() }

    private fun readProducts(context: Context): List<Product> = try {
        val json = context.assets.open(PRODUCTS_ASSET).use { String(it.readBytes()) }
        val array = JSONArray(json)
        (0 until array.length()).map { index ->
            val item = array.getJSONObject(index)
            Product(
                id = item.getLong("id"),
                name = item.getString("name"),
                description = item.optString("description"),
                image = item.optString("image"),
                // The catalog stores price as a string, e.g. "1499.00".
                price = item.optString("price").toDoubleOrNull() ?: 0.0
            )
        }
    } catch (e: Exception) {
        Log.e(LOG_TAG, "Unable to read $PRODUCTS_ASSET", e)
        emptyList()
    }
}

private fun Product.toFavouriteItem() = FavouriteItem(
    id = id,
    name = name,
    category = categoryOf(name),
    price = price,
    image = image,
    // The catalog has no stock field, so derive a stable value that shows off both states.
    inStock = id % 4L != 0L
)

private fun categoryOf(name: String): String {
    val value = name.lowercase()
    return when {
        value.contains("watch") -> "Wearables"
        value.contains("wh-1000") || value.contains("headphone") -> "Audio"
        value.contains("macbook") || value.contains("laptop") ||
            value.contains("inbook") || value.contains("pavilion") ||
            value.contains("galaxy book") -> "Laptops"

        value.contains("iphone") || value.contains("oppo") || value.contains("huawei") ||
            value.contains("universe") -> "Phones"

        value.contains("serum") || value.contains("moisturizer") -> "Skincare"
        value.contains("perfume") || value.contains("scent") || value.contains("eau de") ||
            value.contains("oil") -> "Fragrances"

        value.contains("nike") || value.contains("adidas") -> "Footwear"
        value.contains("vacuum") || value.contains("airfryer") || value.contains("coffee") ||
            value.contains("hanger") || value.contains("holder") ||
            value.contains("handcraft") -> "Home"

        else -> "General"
    }
}
