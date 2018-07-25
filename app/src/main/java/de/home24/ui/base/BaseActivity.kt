package com.tripplanner.ui.base

import dagger.android.support.DaggerAppCompatActivity

/**
 * Created by suyashg on 20/07/18.
 *
 * Base class for activity extensibility and reusability
 */
abstract class BaseActivity : DaggerAppCompatActivity() {
    //common for all activities
    var fragmentContainerId: Int = 0

}