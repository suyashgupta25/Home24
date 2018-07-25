package de.home24.utils

/**
 * Created by suyashg on 22/07/18.
 */
class AppConstants {

    companion object {
        const val EMPTY = ""
        const val ZERO = 0

        //Network settings
        const val CONNECTION_TIMEOUT = 10L
        const val READ_TIMEOUT = 30L
        const val WRITE_TIMEOUT = 10L

        const val MEM_CACHE_SUFFIX = "OkCache"
        const val MEM_CACHE_SIZE = .25f

        const val REVIEW_GRID_SPAN = 2//two columns
    }
}