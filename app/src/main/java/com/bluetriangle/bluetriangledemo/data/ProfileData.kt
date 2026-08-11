package com.bluetriangle.bluetriangledemo.data

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserProfile(
    val name: String,
    val email: String,
    val phone: String,
    val memberSince: String,
    val loyaltyTier: String,
) : Parcelable {
    @IgnoredOnParcel
    val initials: String
        get() = name.split(" ")
            .mapNotNull { it.firstOrNull()?.uppercaseChar() }
            .take(2)
            .joinToString("")
}

@Parcelize
data class Order(
    val id: String,
    val placedOn: String,
    val itemSummary: String,
    val itemCount: Int,
    val total: Double,
    val status: String,
    val image: String,
) : Parcelable

@Parcelize
data class FavouriteItem(
    val id: Long,
    val name: String,
    val category: String,
    val price: Double,
    val image: String,
    val inStock: Boolean,
) : Parcelable

/**
 * Static sample content for the Profile tab. The demo app has no account backend, so the
 * profile, order history and favourites screens are all served from here.
 */
object DummyProfileData {

    val profile = UserProfile(
        name = "BTT SDK Developer",
        email = "dummy@bluetriangle.com",
        phone = "+1 (415) 555-0148",
        memberSince = "March 2021",
        loyaltyTier = "Gold Member"
    )

    // The demo host only serves the handful of images below, so sample rows reuse them.
    private const val IMAGE_HEADPHONES =
        "https://trackerdemo.github.io/hybrid-demo-info/images/sony%20wh-1000xm5.jpg"
    private const val IMAGE_MACBOOK =
        "https://trackerdemo.github.io/hybrid-demo-info/images/apple%20macbook%20air%20(m3,%202024).jpg"
    private const val IMAGE_WATCH =
        "https://trackerdemo.github.io/hybrid-demo-info/images/apple%20watch%20series%209%20(gps,%2045mm).jpg"
    private const val IMAGE_IPHONE =
        "https://trackerdemo.github.io/hybrid-demo-info/images/iphone_x.png"
    private const val IMAGE_SURFACE =
        "https://trackerdemo.github.io/hybrid-demo-info/images/microsoft_surface.jpg"
    private const val IMAGE_PERFUME =
        "https://trackerdemo.github.io/hybrid-demo-info/images/fog_scent_xpressio_perfume.jpg"

    val orders = listOf(
        Order(
            id = "ORD-10248",
            placedOn = "12 Jul 2026",
            itemSummary = "Sony WH-1000XM5",
            itemCount = 1,
            total = 399.0,
            status = "Delivered",
            image = IMAGE_HEADPHONES
        ),
        Order(
            id = "ORD-10244",
            placedOn = "08 Jul 2026",
            itemSummary = "Fog Scent Xpressio Perfume 50ml",
            itemCount = 2,
            total = 26.0,
            status = "Out for delivery",
            image = IMAGE_PERFUME
        ),
        Order(
            id = "ORD-10239",
            placedOn = "02 Jul 2026",
            itemSummary = "Apple Watch Series 9 (GPS, 41mm)",
            itemCount = 1,
            total = 399.0,
            status = "Processing",
            image = IMAGE_WATCH
        ),
        Order(
            id = "ORD-10231",
            placedOn = "28 Jun 2026",
            itemSummary = "Apple MacBook Air (M3, 2024) + 1 more",
            itemCount = 2,
            total = 1528.0,
            status = "Delivered",
            image = IMAGE_MACBOOK
        ),
        Order(
            id = "ORD-10225",
            placedOn = "21 Jun 2026",
            itemSummary = "Microsoft Surface Laptop 4 (16GB)",
            itemCount = 1,
            total = 1699.0,
            status = "Returned",
            image = IMAGE_SURFACE
        ),
        Order(
            id = "ORD-10211",
            placedOn = "14 Jun 2026",
            itemSummary = "iPhone X (64GB)",
            itemCount = 1,
            total = 799.0,
            status = "Delivered",
            image = IMAGE_IPHONE
        ),
        Order(
            id = "ORD-10199",
            placedOn = "05 Jun 2026",
            itemSummary = "Apple Watch Series 9 (GPS, 45mm)",
            itemCount = 1,
            total = 429.0,
            status = "Out for delivery",
            image = IMAGE_WATCH
        ),
        Order(
            id = "ORD-10182",
            placedOn = "27 May 2026",
            itemSummary = "Sony WH-1000XM4 + 2 more",
            itemCount = 3,
            total = 611.0,
            status = "Delivered",
            image = IMAGE_HEADPHONES
        ),
        Order(
            id = "ORD-10154",
            placedOn = "19 May 2026",
            itemSummary = "iPhone X",
            itemCount = 1,
            total = 899.0,
            status = "Cancelled",
            image = IMAGE_IPHONE
        ),
        Order(
            id = "ORD-10141",
            placedOn = "30 Apr 2026",
            itemSummary = "Apple MacBook Air (M3, 2024) 15-inch",
            itemCount = 1,
            total = 1299.0,
            status = "Delivered",
            image = IMAGE_MACBOOK
        ),
        Order(
            id = "ORD-10102",
            placedOn = "02 Apr 2026",
            itemSummary = "Microsoft Surface Laptop 4 + 2 more",
            itemCount = 3,
            total = 1611.0,
            status = "Delivered",
            image = IMAGE_SURFACE
        ),
        Order(
            id = "ORD-10093",
            placedOn = "18 Mar 2026",
            itemSummary = "Sony WH-1000XM5",
            itemCount = 1,
            total = 399.0,
            status = "Returned",
            image = IMAGE_HEADPHONES
        ),
        Order(
            id = "ORD-10077",
            placedOn = "14 Feb 2026",
            itemSummary = "Fog Scent Xpressio Perfume",
            itemCount = 4,
            total = 52.0,
            status = "Delivered",
            image = IMAGE_PERFUME
        ),
        Order(
            id = "ORD-10061",
            placedOn = "26 Jan 2026",
            itemSummary = "Apple Watch Series 9 (GPS, 45mm) + 1 more",
            itemCount = 2,
            total = 828.0,
            status = "Delivered",
            image = IMAGE_WATCH
        ),
        Order(
            id = "ORD-10042",
            placedOn = "09 Jan 2026",
            itemSummary = "Microsoft Surface Laptop 4",
            itemCount = 1,
            total = 1499.0,
            status = "Cancelled",
            image = IMAGE_SURFACE
        ),
        Order(
            id = "ORD-10018",
            placedOn = "12 Dec 2025",
            itemSummary = "iPhone X (256GB) + 3 more",
            itemCount = 4,
            total = 1247.0,
            status = "Delivered",
            image = IMAGE_IPHONE
        )
    )

    /** Preview-only sample. At runtime favourites come from [ProductAssetsRepository]. */
    val sampleFavourites = listOf(
        FavouriteItem(
            id = 23,
            name = "Sony WH-1000XM5",
            category = "Audio",
            price = 399.0,
            image = IMAGE_HEADPHONES,
            inStock = true
        ),
        FavouriteItem(
            id = 24,
            name = "Apple MacBook Air (M3, 2024)",
            category = "Laptops",
            price = 1099.0,
            image = IMAGE_MACBOOK,
            inStock = true
        ),
        FavouriteItem(
            id = 22,
            name = "Apple Watch Series 9 (GPS, 45mm)",
            category = "Wearables",
            price = 429.0,
            image = IMAGE_WATCH,
            inStock = false
        ),
        FavouriteItem(
            id = 2,
            name = "iPhone X",
            category = "Phones",
            price = 899.0,
            image = IMAGE_IPHONE,
            inStock = true
        )
    )
}
