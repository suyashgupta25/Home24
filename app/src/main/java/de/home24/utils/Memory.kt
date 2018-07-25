package de.home24.utils

import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.support.annotation.FloatRange

/**
 * * Created by suyashg on 21/07/18.
 *
 * Calculates the cache size
 */
object Memory {

    fun calcCacheSize(context: Context, @FloatRange(from = 0.01, to = 1.0) size: Float): Long {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val largeHeap = context.applicationInfo.flags and ApplicationInfo.FLAG_LARGE_HEAP != 0
        val memoryClass = if (largeHeap) am.largeMemoryClass else am.memoryClass
        return (memoryClass * 1024L * 1024L * size).toLong()
    }
}