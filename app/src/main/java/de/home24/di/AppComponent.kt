package de.home24.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import de.home24.BaseApp
import javax.inject.Singleton

/**
 * Created by suyashg on 19/07/18.
 */
@Singleton
@Component(
        modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuilder::class,
        AppModule::class
        ])
interface AppComponent : AndroidInjector<BaseApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: BaseApp): Builder

        fun network(network: NetworkModule): Builder

        fun build(): AppComponent
    }

    override fun inject(app: BaseApp)

}