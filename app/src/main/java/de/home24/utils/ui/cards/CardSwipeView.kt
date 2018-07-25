package de.home24.utils.ui.cards

import android.annotation.TargetApi
import android.content.Context
import android.database.DataSetObserver
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Adapter
import android.widget.FrameLayout
import de.home24.R

/**
 * Created by suyashg on 21/07/18.
 *
 * CardSwipeView creates a view of cards in a deck format and provides gestures to interact with it
 */
class CardSwipeView : FrameLayout {

    companion object {
        private const val DEFAULT_MAX_VISIBLE_CARDS = 3
        private const val DEFAULT_ROTATION_ANGLE = 30f
        private const val DEFAULT_SCALE_DIFF = 0.04f
    }

    private var maxVisibleCards = DEFAULT_MAX_VISIBLE_CARDS
    private var cardPositionOffsetX = resources.getDimensionPixelSize(R.dimen.default_card_spacing)
    private var cardPositionOffsetY = resources.getDimensionPixelSize(R.dimen.default_card_spacing)
    private var cardRotationDegrees = DEFAULT_ROTATION_ANGLE
    private var dyingViews = hashSetOf<View>()
    private var activeViews = linkedSetOf<View>()
    private var dataSetObservable: DataSetObserver? = null
    var isNeedCircleLoading = false

    var adapter: Adapter? = null
        set(value) {
            dataSetObservable?.let { field?.unregisterDataSetObserver(it) }
            removeAllViews()
            field = value
            field?.registerDataSetObserver(createDataSetObserver())
            dataSetObservable?.onChanged()
        }
    private var adapterPosition = 0
    private var deckMap = hashMapOf<View, CardOperator>()
    var cardSwipeViewListener: CardSwipeViewListener? = null
    internal var parentWidth = 0
    private var swipeEnabled = true

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    /**
     * Init the swipe view with attr-set
     */
    private fun init(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CardSwipeView)
        val cardLayoutId = a.getResourceId(R.styleable.CardSwipeView_card_layout, -1)
        maxVisibleCards = a.getInt(R.styleable.CardSwipeView_max_visible_cards, DEFAULT_MAX_VISIBLE_CARDS)
        cardPositionOffsetX = a.getDimensionPixelSize(R.styleable.CardSwipeView_card_offsetX, resources.getDimensionPixelSize(R.dimen.default_card_spacing))
        cardPositionOffsetY = a.getDimensionPixelSize(R.styleable.CardSwipeView_card_offsetY, resources.getDimensionPixelSize(R.dimen.default_card_spacing))
        cardRotationDegrees = a.getFloat(R.styleable.CardSwipeView_card_rotate_angle, DEFAULT_ROTATION_ANGLE)

        a.recycle()

        if (isInEditMode) {
            LayoutInflater.from(context).inflate(cardLayoutId, this, true)
        }
    }

    /**
     * Add card to visible stack
     */
    private fun addCardToDeck() {
        if (adapterPosition < adapter?.count ?: 0) {
            val newCard = adapter?.getView(adapterPosition, null, this)
            initializeCardPosition(newCard)
            newCard?.let {
                addView(it, 0)
                deckMap.put(it, CardOperator(this, it, adapterPosition++, cardCallback))
            }
        } else if (isNeedCircleLoading) {
            adapterPosition = 0
            addCardToDeck()
        }
    }

    /**
     * Set up card offset
     * @param view - new card
     */
    private fun initializeCardPosition(view: View?) {
        val childCount = childCount - dyingViews.size

        scaleView(view, 0f, childCount)
        view?.translationY = (cardPositionOffsetY * childCount).toFloat()
        setZTranslations(childCount)
    }

    /**
     * Check top card
     */
    private fun checkTopCard() {
        val childCount = childCount
        setZTranslations(childCount)
        if (childCount - 1 - dyingViews.size < 0) {
            cardSwipeViewListener?.onEmptyDeck()
        } else {
            val topCard = getChildAt(childCount - 1 - dyingViews.size)
            if (deckMap.containsKey(topCard)) {
                deckMap[topCard]?.let { cardSwipeViewListener?.onNewTopCard(it.adapterPosition) }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setZTranslations(childCount: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            (0 until childCount).map {
                getChildAt(it).translationZ = (it * 10).toFloat()
            }
        }
    }

    fun getMaxCardWidth(cardView: View): Float = cardView.height * Math.tan(Math.toRadians(cardRotationDegrees.toDouble())).toFloat()

    /**
     * Check if swipeable
     */
    fun canSwipe(card: View): Boolean {
        return (swipeEnabled && (activeViews.isEmpty() || activeViews.contains(card))
                && indexOfChild(card) >= childCount - 2)
    }

    /**
     * Update the position based on progress
     */
    private fun updateDeckCardsPosition(progress: Float) {
        val visibleChildCount = Math.min(childCount, maxVisibleCards + 1)
        var childCount = Math.min(childCount, maxVisibleCards)
        var cardsWillBeMoved = 0

        var cardView: View

        (0 until visibleChildCount).map {
            cardView = getChildAt(it)
            if (deckMap.containsKey(cardView) && deckMap[cardView]?.isBeingDragged != true) {
                cardsWillBeMoved++
            }
            scaleView(cardView, progress, childCount - it - 1)
        }
        if (progress != 0.0f) {
            for (i in 0 until cardsWillBeMoved) {
                cardView = getChildAt(i)
                cardView.translationY = (cardPositionOffsetY * Math.min(cardsWillBeMoved, visibleChildCount - i - 1) - cardPositionOffsetY * Math.abs(progress))
            }
        }
    }

    private fun scaleView(view: View?, progress: Float, childCount: Int) {
        val currentScale = 1f - (childCount * DEFAULT_SCALE_DIFF)
        val nextScale = 1f - ((childCount - 1) * DEFAULT_SCALE_DIFF)
        val scale = currentScale + (nextScale - currentScale) * Math.abs(progress)
        if (scale <= 1f) {
            view?.scaleX = scale
            view?.scaleY = scale
        }
    }

    private fun updateCardView(card: View, sideProgress: Float) {
        val rotation = cardRotationDegrees.toInt() * sideProgress
        card.rotation = rotation
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            dataSetObservable?.onChanged()
        }
    }

    override fun addView(child: View, index: Int) {
        child.alpha = 0f
        super.addView(child, index)
        child.animate().alpha(1f).duration = 300
    }

    override fun removeAllViews() {
        super.removeAllViews()
        deckMap.clear()
        activeViews.clear()
        dyingViews.clear()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        parentWidth = (this.parent as View).measuredWidth
    }

    private fun createDataSetObserver(): DataSetObserver {
        dataSetObservable = object : DataSetObserver() {
            override fun onChanged() {
                super.onChanged()
                addCards()
            }

            override fun onInvalidated() {
                super.onInvalidated()
                adapterPosition = 0
                removeAllViews()
                addCards()
            }

            private fun addCards() {
                val childCount = childCount - dyingViews.size
                for (i in childCount until maxVisibleCards) {
                    addCardToDeck()
                }
                checkTopCard()
            }
        }
        return dataSetObservable as DataSetObserver
    }

    private var cardCallback: CardCallback = object : CardCallback {
        override fun onCardActionDown(adapterPosition: Int, card: View) {
            activeViews.add(card)
        }

        override fun onCardDrag(adapterPosition: Int, card: View, sideProgress: Float) {
            updateCardView(card, sideProgress)
            cardSwipeViewListener?.onCardDrag(adapterPosition, card, sideProgress)
        }

        override fun onCardOffset(adapterPosition: Int, card: View, offsetProgress: Float) {
            updateDeckCardsPosition(offsetProgress)
        }

        override fun onCardActionUp(adapterPosition: Int, card: View, isCardNeedRemove: Boolean) {
            if (isCardNeedRemove) {
                activeViews.remove(card)
            }
        }

        override fun onCardSwipedLeft(adapterPosition: Int, card: View, notify: Boolean) {
            dyingViews.add(card)
            dataSetObservable?.onChanged()
            if (notify) {
                cardSwipeViewListener?.onCardSwipedLeft(adapterPosition)
            }
        }

        override fun onCardSwipedRight(adapterPosition: Int, card: View, notify: Boolean) {
            dyingViews.add(card)
            dataSetObservable?.onChanged()
            if (notify) {
                cardSwipeViewListener?.onCardSwipedRight(adapterPosition)
            }
        }

        override fun onCardOffScreen(adapterPosition: Int, card: View) {
            dyingViews.remove(card)
            deckMap.remove(card)
            removeView(card)
        }

        override fun onCardSingleTap(adapterPosition: Int, card: View) {
            cardSwipeViewListener?.onCardSingleTap(adapterPosition)
        }

        override fun onCardDoubleTap(adapterPosition: Int, card: View) {
            cardSwipeViewListener?.onCardDoubleTap(adapterPosition)
        }

        override fun onCardLongPress(adapterPosition: Int, card: View) {
            cardSwipeViewListener?.onCardLongPress(adapterPosition)
        }

        override fun onCardMovedOnClickRight(adapterPosition: Int, card: View, notify: Boolean) {
            activeViews.remove(card)
            dyingViews.add(card)
            dataSetObservable?.onChanged()
            findPositionAfterClick()
            if (notify) {
                cardSwipeViewListener?.onClickRight(adapterPosition)
            }
        }

        override fun onCardMovedOnClickLeft(adapterPosition: Int, card: View, notify: Boolean) {
            activeViews.remove(card)
            dyingViews.add(card)
            dataSetObservable?.onChanged()
            findPositionAfterClick()
            if (notify) {
                cardSwipeViewListener?.onClickLeft(adapterPosition)
            }
        }
    }

    fun onClickRight(): Int? {
        val childCount = childCount
        val topCard = getChildAt(childCount - 1 - dyingViews.size)
        topCard?.let {
            activeViews.add(it)
            val cardOperator: CardOperator? = deckMap[it]
            //Log.d("position","Oposition="+cardOperator?.adapterPosition)
            cardOperator?.onClickRight()
            it.rotation = 10f
            return cardOperator?.adapterPosition
        }
        return 0
    }

    fun onClickLeft(): Int?  {
        val childCount = childCount
        val topCard = getChildAt(childCount - 1 - dyingViews.size)
        topCard?.let {
            activeViews.add(it)
            val cardOperator: CardOperator? = deckMap[it]
            cardOperator?.onClickLeft()
            it.rotation = -10f
            return cardOperator?.adapterPosition
        }
        return 0
    }

    private fun findPositionAfterClick() {
        var childCount = Math.min(childCount, maxVisibleCards)
        (0 until childCount).map {
            val view = getChildAt(it)
            scaleView(view, 0f, childCount - it - 1)
            view?.translationY = (cardPositionOffsetY * (childCount - it - 1)).toFloat()
        }
    }

    fun resetFromStart() {
        adapterPosition = 0
    }

}