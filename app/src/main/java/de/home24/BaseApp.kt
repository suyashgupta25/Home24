package de.home24

import android.content.Context
import android.support.multidex.MultiDex
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import de.home24.di.NetworkModule
import de.home24.di.DaggerAppComponent

/**
 * Created by suyashg on 19/07/18.
 */
open class BaseApp : DaggerApplication() {

    lateinit var androidInjector: AndroidInjector<out DaggerApplication>

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this);
        androidInjector = DaggerAppComponent.builder()
                .application(this)
                .network(networkModule())
                .build()
    }

    public override fun applicationInjector(): AndroidInjector<out DaggerApplication> = androidInjector

    protected open fun networkModule(): NetworkModule = NetworkModule()

}