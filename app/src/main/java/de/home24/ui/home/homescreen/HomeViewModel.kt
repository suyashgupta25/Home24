package de.home24.ui.home.homescreen

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import javax.inject.Inject

/**
 * Created by suyashg on 20/07/18.
 */
class HomeViewModel @Inject constructor(): ViewModel(), LifecycleObserver {

    val startBtnClicked = MutableLiveData<Boolean>()

    fun startButtonClicked() {
        startBtnClicked.value = true
    }

}