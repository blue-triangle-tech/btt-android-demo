package com.bluetriangle.bluetriangledemo.utils

import android.os.Handler
import android.os.Looper

fun postDelayed(delayMillis: Long, runnable: () -> Unit) {
    Handler().postDelayed(runnable, delayMillis)
}

fun postDelayedUI(delayMillis: Long, runnable: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(runnable, delayMillis)
}

fun triggerANRWarning(multiple: Boolean = true) {
    Thread.sleep(6000)

    if (multiple) {
        postDelayedUI(4000) {
            Thread.sleep(6000)
        }

        postDelayedUI(14000) {
            Thread.sleep(6000)
        }
    }
}

fun triggerFatalANR() {
    Thread.sleep(25000)
}

fun triggerMemoryWarning() {
    val totalMemory = Runtime.getRuntime().maxMemory()
    val memoryThreshold = (90 * totalMemory) /100
    val usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
    var availableMemory = memoryThreshold - usedMemory
    if (availableMemory <= 0L)
        availableMemory = (totalMemory - memoryThreshold) / 4

    MemoryHolder.allocateMemory(availableMemory.toInt())
}