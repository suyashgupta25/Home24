package de.home24

import android.content.Context
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import de.home24.di.DaggerAppComponent
import de.home24.di.NetworkModule

/**
 * Created by suyashg on 19/07/18.
 */
open class BaseApp : DaggerApplication() {

    lateinit var androidInjector: AndroidInjector<out DaggerApplication>

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        androidInjector = DaggerAppComponent.builder()
                .application(this)
                .network(networkModule())
                .build()
    }

    public override fun applicationInjector(): AndroidInjector<out DaggerApplication> = androidInjector

    protected open fun networkModule(): NetworkModule = NetworkModule()

}