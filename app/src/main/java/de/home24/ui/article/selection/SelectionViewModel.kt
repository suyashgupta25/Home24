package de.home24.ui.article.selection

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import de.home24.data.local.model.ArticleTemplate
import de.home24.data.remote.api.ArticleDataSource
import de.home24.ui.common.listeners.ErrorRetryListener
import de.home24.utils.AppConstants
import de.home24.utils.AppConstants.Companion.ZERO
import de.home24.utils.Result
import de.home24.utils.defaultErrorHandler
import de.home24.utils.ui.cards.CardSwipeViewListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by suyashg on 23/07/18.
 */
class SelectionViewModel @Inject constructor(private val articleDataSource: ArticleDataSource) : ViewModel(),
        LifecycleObserver, ErrorRetryListener, CardSwipeViewListener {

    // Disposable
    private val disposable: CompositeDisposable = CompositeDisposable()

    // Observables for the UI elements
    val isLoading = ObservableBoolean(true)
    val errorMsg = ObservableField<String>(AppConstants.EMPTY)
    val selectedArticle = ObservableField<Int>(ZERO)
    val totalArticle = ObservableField<Int>(ZERO)
    val isReviewButtonEnabled = ObservableField<Boolean>(false)

    //checks when to action on click on review button
    val reviewBtnClicked = MutableLiveData<Boolean>()
    //checks whether deck is empty
    val showEmptyDeckInfo = MutableLiveData<Boolean>()
    //articles list which in deck of cards
    val articleList = MutableLiveData<Result<MutableList<ArticleTemplate>>>()

    //reviewed items for the review screen
    val reviewList = mutableListOf<ArticleTemplate>()

    /**
     * this method calls the web service and fetches the ArticleTemplate requried for UI
     */
    fun callWebservice() {
        isLoading.set(true)
        errorMsg.set(AppConstants.EMPTY)
        disposable.add(articleDataSource.getArticles()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())?.map { Result.success(it) }
                ?.doOnError(defaultErrorHandler())
                ?.onErrorReturn { Result.failure(it) }
                ?.startWith(Result.loading())
                ?.subscribe { item ->
                    articleList.postValue(item)
                }!!
        )
    }

    /**
     * get article based on position from Livedata
     */
    fun getItemFromLiveData(position: Int):ArticleTemplate? {
        val value = articleList.value
        when(value) {
            is Result.Success -> {
                return value.data.get(position)
            }
        }
        return null
    }

    /**
     * increment liked article count
     */
    private fun incrementSelectedArticle(position: Int) {
        selectedArticle.set(selectedArticle.get()!!.inc())
    }

    /**
     * add liked item to review list and increment count
     */
    fun addLikedItemToReviewListAndIncrementCount(position: Int) {
        val item = getItemFromLiveData(position)
        item?.isLiked = true
        addItemToReviewList(item)
        incrementSelectedArticle(position)
    }

    /**
     * add disliked item to review list
     */
    fun addItemToReviewList(position: Int) {
        val item = getItemFromLiveData(position)
        addItemToReviewList(item)
    }

    /**
     * add item to review list
     */
    fun addItemToReviewList(item:ArticleTemplate?) {
        item?.let { reviewList.add(it) }
    }

    /**
     * disliked item
     */
    override fun onCardSwipedLeft(position: Int) {
        addItemToReviewList(position)
    }

    /**
     * liked item
     */
    override fun onCardSwipedRight(position: Int) {
        addLikedItemToReviewListAndIncrementCount(position)
    }

    /**
     * fetching articles is success
     */
    fun onSuccessResponse(size:Int?) {
        isLoading.set(false)
        errorMsg.set(AppConstants.EMPTY)
        selectedArticle.set(ZERO)
        totalArticle.set(size)
    }

    /**
     * deck is empty now
     */
    override fun onEmptyDeck() {
        showEmptyDeckInfo.postValue(true)
        isReviewButtonEnabled.set(true)
    }

    /**
     * retry the selection of articles
     */
    override fun retryClicked() {
        showEmptyDeckInfo.postValue(false)
        isReviewButtonEnabled.set(false)
        selectedArticle.set(ZERO)
        articleList.postValue(Result.loading())
        reviewList.clear()
        callWebservice()
    }

    /**
     * take action for review button clicked
     */
    fun onReviewButtonClicked() {
        reviewBtnClicked.value = true
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}