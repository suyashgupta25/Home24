package de.home24.di

import dagger.Module

/**
 * Created by suyashg on 19/07/18.
 */
@Module(includes = [NetworkModule::class, ViewModelModule::class])
internal object AppModule {
    //If required
}