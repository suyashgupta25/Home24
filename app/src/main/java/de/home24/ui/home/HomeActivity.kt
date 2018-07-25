package de.home24.ui.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.tripplanner.ui.base.BaseActivity
import de.home24.R
import de.home24.databinding.ActivityHomeBinding
import de.home24.ui.home.homescreen.HomeFragment
import de.home24.utils.ext.setFragment

/**
 * Created by suyashg on 20/07/18.
 */
class HomeActivity : BaseActivity() {

    val binding: ActivityHomeBinding by lazy {
        DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentContainerId = binding.flMainContent.id
        setFragment(fragmentContainerId, ::HomeFragment)
    }
}
