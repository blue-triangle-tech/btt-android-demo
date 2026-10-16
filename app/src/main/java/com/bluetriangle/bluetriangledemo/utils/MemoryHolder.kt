package com.bluetriangle.bluetriangledemo.utils

import android.util.Log
import java.util.LinkedList

object MemoryHolder {

    private var memoryBank:LinkedList<MemoryBlock>? = null

    class MemoryBlock(size:Int = 30 * 1024 * 1024) {
        private val memory = ByteArray(size)
        init {
            Log.d("MemoryBlock","Allocated Memory Block of Size: ${memory.size} * 4 bytes")
        }
    }

    fun allocateMemory(size:Int= 30 * 1024 * 1024) {
        if(memoryBank == null) {
            memoryBank = LinkedList()
        }
        memoryBank?.add(MemoryBlock(size))
    }

    fun deallocateMemory() {
        if(memoryBank == null) return
        if(!memoryBank.isNullOrEmpty()) {
            memoryBank?.removeFirst()
        }
    }

    fun clearMemory():Boolean {
        if(memoryBank.isNullOrEmpty()) return false
        memoryBank?.clear()
        memoryBank = null
        Runtime.getRuntime().gc()
        return true
    }
}