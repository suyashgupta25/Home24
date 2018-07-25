package de.home24.ui.article.selection

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tripplanner.ui.base.BaseActivity
import dagger.android.support.AndroidSupportInjection
import de.home24.R
import de.home24.data.local.model.ArticleTemplate
import de.home24.databinding.FragmentSelectionBinding
import de.home24.ui.article.review.ReviewFragment
import de.home24.ui.base.BaseFragment
import de.home24.utils.Result
import de.home24.utils.ext.addFragment
import de.home24.utils.ui.cards.CardSwipeView
import javax.inject.Inject

/**
 * Created by suyashg on 23/07/18.
 */
class SelectionFragment : BaseFragment() {

    private val TAG = SelectionFragment::class.qualifiedName

    private var adapter: SelectionAdapter? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: SelectionViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(SelectionViewModel::class.java)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_selection, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding(view)
    }

    private fun initBinding(view: View) {
        val binding = DataBindingUtil.bind<FragmentSelectionBinding>(view)
        lifecycle.addObserver(viewModel)
        binding?.like?.setOnClickListener { viewModel.addLikedItemToReviewListAndIncrementCount(binding?.csvArticles?.onClickRight()!!) }
        binding?.dislike?.setOnClickListener { viewModel.addItemToReviewList(binding?.csvArticles?.onClickLeft()!!) }
        initializeDeck(binding?.csvArticles)
        binding.let {
            it!!.viewModel = viewModel
            it.setLifecycleOwner(this)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        registerObservers()
        viewModel.callWebservice()
    }

    private fun registerObservers() {
        viewModel.articleList.observe(this, Observer { t: Result<MutableList<ArticleTemplate>>? ->
            when (t) {
                is Result.Loading -> {
                    showLoading()
                }
                is Result.Success -> {
                    showSuccessData(t.data)
                }
                is Result.Failure -> {
                    showError(t.message)
                }
                else -> {
                    Log.e(TAG, getString(R.string.err_selection_data_else))
                }
            }
        })
        viewModel.showEmptyDeckInfo.observe(this, Observer { value: Boolean? ->
            if (value!!) showError(Throwable(getString(R.string.err_no_data)))
        })

        viewModel.reviewBtnClicked.observe(this, Observer {
            if (activity is BaseActivity) {
                val bundle = Bundle()
                bundle.putParcelableArrayList(getString(R.string.param_review_bundle), ArrayList<ArticleTemplate>(viewModel.reviewList))
                activity?.addFragment((activity as BaseActivity).fragmentContainerId, ::ReviewFragment, bundle)
            }
        })
    }

    /**
     * Initialize Deck and Adapter for filling Deck
     */
    private fun initializeDeck(view: CardSwipeView?) {
        view?.cardSwipeViewListener = viewModel
    }

    private fun showLoading() {
        viewModel.isLoading.set(true)
    }

    private fun showSuccessData(data: MutableList<ArticleTemplate>) {
        val binding = view?.let { DataBindingUtil.bind<FragmentSelectionBinding>(it) }
        binding?.csvArticles?.resetFromStart()
        adapter = SelectionAdapter(activity!!.applicationContext, data)
        binding?.csvArticles?.adapter = adapter
        viewModel.onSuccessResponse(data.size)
    }

    private fun showError(message: Throwable) {
        viewModel.isLoading.set(false)
        viewModel.errorMsg.set(message.localizedMessage)
    }
}
