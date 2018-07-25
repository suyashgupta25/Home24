package de.home24.di

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Created by suyashg on 19/07/18.
 */
@Target(allowedTargets = [AnnotationTarget.FUNCTION])
@Retention(value = AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)