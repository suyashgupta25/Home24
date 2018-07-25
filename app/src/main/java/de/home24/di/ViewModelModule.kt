package de.home24.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import de.home24.ui.article.review.ReviewViewModel
import de.home24.ui.article.selection.SelectionViewModel
import de.home24.ui.home.homescreen.HomeViewModel

/**
 * Created by suyashg on 19/07/18.
 */
@Module//(includes = [RepositoryModule::class])
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectionViewModel::class)
    fun bindSelectionViewModel(viewModel: SelectionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReviewViewModel::class)
    fun bindReviewViewModel(viewModel: ReviewViewModel): ViewModel
}