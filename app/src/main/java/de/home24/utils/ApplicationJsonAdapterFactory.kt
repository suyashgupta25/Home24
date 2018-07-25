package de.home24.utils

import com.squareup.moshi.JsonAdapter
import se.ansman.kotshi.KotshiJsonAdapterFactory

/**
 * Created by suyashg on 21/07/18.
 */
@KotshiJsonAdapterFactory
abstract class ApplicationJsonAdapterFactory : JsonAdapter.Factory {
    companion object {
        val INSTANCE: ApplicationJsonAdapterFactory = KotshiApplicationJsonAdapterFactory()
    }
}