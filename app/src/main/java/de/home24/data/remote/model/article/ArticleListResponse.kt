package de.home24.data.remote.model.article

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ArticleListResponse(
        val articlesCount: Long?,
        @Json(name = "_links")
        val links: ArticleLinks?,
        @Json(name = "_embedded")
        val embedded: ArticleEmbedded?
)