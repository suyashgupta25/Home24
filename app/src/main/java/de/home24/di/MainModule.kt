package de.home24.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.home24.ui.article.review.ReviewFragment
import de.home24.ui.article.selection.SelectionFragment
import de.home24.ui.home.homescreen.HomeFragment

/**
 * Created by suyashg on 21/06/18.
 */
@Module
internal abstract class MainModule {
    @ContributesAndroidInjector
    internal abstract fun contributeHomeFragmentInjector(): HomeFragment

    @ContributesAndroidInjector
    internal abstract fun contributeSelectionFragmentInjector(): SelectionFragment

    @ContributesAndroidInjector
    internal abstract fun contributeReviewFragmentInjector(): ReviewFragment
}