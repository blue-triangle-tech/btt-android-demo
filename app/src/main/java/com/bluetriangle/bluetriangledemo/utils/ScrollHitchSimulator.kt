package com.bluetriangle.bluetriangledemo.utils

import android.os.SystemClock

/**
 * Deliberately blocks the main thread while a list scrolls, producing hitches for the SDK to
 * detect. Call [onScroll] from a scroll callback on the main thread.
 */
class ScrollHitchSimulator(
    private val hitchMs: Long = ORDER_HISTORY_SCROLL_HITCH_MS,
    private val intervalMs: Long = ORDER_HISTORY_SCROLL_HITCH_INTERVAL_MS
) {
    private var lastHitchAt = 0L

    fun onScroll() {
        val now = SystemClock.uptimeMillis()
        if (now - lastHitchAt < intervalMs) return
        Thread.sleep(hitchMs)
        lastHitchAt = SystemClock.uptimeMillis()
    }
}
