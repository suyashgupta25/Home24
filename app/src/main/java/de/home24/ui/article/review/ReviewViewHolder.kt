package de.home24.ui.article.review

import de.home24.data.local.model.ArticleTemplate

/**
 * Created by suyashg on 24/07/18.
 *
 * this interface provides common way of binding views if multiple view types present
 */
interface ReviewViewHolder {
    fun bindViews(update: ArticleTemplate)
}