package de.home24.ui.common.listeners

/**
 *
 * Error retry listener used when any error occurs and we want to retry feature for its rectification
 */
interface ErrorRetryListener {
    fun retryClicked()
}