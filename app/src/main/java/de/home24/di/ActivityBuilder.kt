package de.home24.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.home24.ui.article.ArticleActivity
import de.home24.ui.home.HomeActivity

/**
 * Created by suyashg on 19/07/18.
 */
@Module
internal abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun contributeHomeInjector(): HomeActivity

    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun contributeArticleInjector(): ArticleActivity

}