package de.home24.utils.ext

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.MutableLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import org.reactivestreams.Publisher

/**
 * Created by suyashg on 21/07/18.
 *
 * Conversion utility used to convert to Publisher from LiveDataStreams
 */
fun <T> Publisher<T>.toLiveData() = LiveDataReactiveStreams.fromPublisher(this) as LiveData<T>

fun <T> Publisher<T>.toMutableLiveData() = LiveDataReactiveStreams.fromPublisher(this) as MutableLiveData<T>

fun <T : Any?> Observable<T>.toLiveData(strategy: BackpressureStrategy = BackpressureStrategy.LATEST) = toFlowable(strategy).toLiveData()