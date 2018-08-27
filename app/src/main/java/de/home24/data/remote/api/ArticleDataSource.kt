package de.home24.data.remote.api

import de.home24.data.local.model.ArticleTemplate
import de.home24.data.remote.model.article.Article
import de.home24.data.remote.model.article.ArticleListResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class ArticleDataSource @Inject constructor(private val articleService: ArticleService) {

    /**
     * Processes the data received from server according to UI requirements
     * since we need only title and name of article so its filtered here.
     */
    fun getArticles(): Observable<MutableList<ArticleTemplate>>? {
        return articleService.getArticles()
                .flatMap { listitem: ArticleListResponse ->
                    Observable.fromIterable(listitem.embedded?.articles)
                            .map { item: Article ->
                                ArticleTemplate(item?.title, item.media?.get(0)?.uri)
                            }.toList().toObservable()
                }
    }
}