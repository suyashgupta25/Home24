package de.home24.ui.article.review

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import javax.inject.Inject

/**
 * Created by suyashg on 24/07/18.
 */
class ReviewViewModel @Inject constructor() : ViewModel(), LifecycleObserver {

    val gridBtnClicked = MutableLiveData<Boolean>()
    val listBtnClicked = MutableLiveData<Boolean>()

    fun onGridButtonClicked() {
        gridBtnClicked.value = true
        listBtnClicked.value = false
    }

    fun onListButtonClicked() {
        listBtnClicked.value = true
        gridBtnClicked.value = false
    }
}