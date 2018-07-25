package de.home24.ui.home.homescreen

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import de.home24.R
import de.home24.databinding.FragmentHomeBinding
import de.home24.ui.article.ArticleActivity
import de.home24.ui.base.BaseFragment
import javax.inject.Inject

/**
 * Created by suyashg on 20/07/18.
 */
class HomeFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DataBindingUtil.bind<FragmentHomeBinding>(view)
        lifecycle.addObserver(homeViewModel)
        binding.let {
            it!!.viewModel = homeViewModel
            it.setLifecycleOwner(this)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel.startBtnClicked.observe(this, Observer {
            val intent = Intent(activity, ArticleActivity::class.java)
            startActivity(intent)
        })
    }
}