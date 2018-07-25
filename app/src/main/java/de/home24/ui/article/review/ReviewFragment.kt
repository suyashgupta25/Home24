package de.home24.ui.article.review

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.tripplanner.ui.base.BaseActivity
import dagger.android.support.AndroidSupportInjection
import de.home24.R
import de.home24.data.local.model.ArticleTemplate
import de.home24.databinding.FragmentReviewBinding
import de.home24.ui.base.BaseFragment
import de.home24.utils.AppConstants.Companion.REVIEW_GRID_SPAN
import javax.inject.Inject

/**
 * Created by suyashg on 24/07/18.
 */
class ReviewFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var adapter : ReviewAdapter? = null

    val viewModel: ReviewViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ReviewViewModel::class.java)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_review, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding(view)
    }

    private fun initBinding(view: View) {
        val binding = DataBindingUtil.bind<FragmentReviewBinding>(view)
        lifecycle.addObserver(viewModel)
        binding.let {
            it!!.viewModel = viewModel
            it.setLifecycleOwner(this)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUI()
        setupBackButtonInToolbar()
        registerObservers()
    }

    private fun setupUI() {
        //get data from the parcel
        val binding = view?.let { DataBindingUtil.bind<FragmentReviewBinding>(it) }
        val arrayList =
                arguments?.getParcelableArrayList<ArticleTemplate>(getString(R.string.param_review_bundle))
        val convert = ArrayList<ArticleTemplate>(arrayList)

        //default view to be set as List pattern
        var mLayoutManager = LinearLayoutManager(activity)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding?.rvReview?.layoutManager = mLayoutManager

        //default view to be set as List pattern
        binding?.rvReview?.itemAnimator = DefaultItemAnimator()
        adapter = ReviewAdapter(convert, activity?.applicationContext!!, ReviewAdapter.VIEW_TYPE_LIST)
        binding?.rvReview?.adapter = adapter

        //provide default behavior for list already rendered
        viewModel.gridBtnClicked.value = false
        viewModel.listBtnClicked.value = true
    }

    private fun setupBackButtonInToolbar() {
        val toolbar = view?.findViewById(R.id.toolbar) as Toolbar
        if (activity is BaseActivity) {
            val baseActivity = activity as BaseActivity
            baseActivity.setSupportActionBar(toolbar)
            if (baseActivity.getSupportActionBar() != null) {
                baseActivity.getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
                baseActivity.getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
            }
        }
    }

    private fun registerObservers() {
        val binding = view?.let { DataBindingUtil.bind<FragmentReviewBinding>(it) }
        viewModel.gridBtnClicked.observe(this, Observer {
            if (it!!) {
                adapter?.notifyRemoveEach()
                var mGLayoutManager = StaggeredGridLayoutManager(REVIEW_GRID_SPAN, StaggeredGridLayoutManager.VERTICAL)
                binding?.rvReview?.layoutManager = mGLayoutManager
                adapter?.setItemType(ReviewAdapter.VIEW_TYPE_GRID)
                adapter?.notifyAddEach()
            }
        })

        viewModel.listBtnClicked.observe(this, Observer {
            if (it!!) {
                adapter?.notifyRemoveEach()
                var mLayoutManager = LinearLayoutManager(activity)
                mLayoutManager.orientation = LinearLayoutManager.VERTICAL
                binding?.rvReview?.layoutManager = mLayoutManager
                adapter?.setItemType(ReviewAdapter.VIEW_TYPE_LIST)
                adapter?.notifyAddEach()
            }
        })
    }
}