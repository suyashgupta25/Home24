package de.home24.data.remote.api

import de.home24.data.remote.Endpoints
import de.home24.data.remote.model.article.ArticleListResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface ArticleService {

    /**
     * End point which fetches list of articles along with total count on the server. Also we can
     */
    @GET(Endpoints.DOMAIN_ARTICLES_DATA)
    fun getArticles(): Observable<ArticleListResponse>
}