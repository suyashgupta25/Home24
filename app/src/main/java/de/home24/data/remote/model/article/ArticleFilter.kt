package de.home24.data.remote.model.article

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ArticleFilter(
        val id: String?,
        val priority: Int?,
        val title: String?,
        val values: List<ArticleFilterValue>? = listOf<ArticleFilterValue>(),
        @Json(name = "_metadata")
        val metadata: Metadata?
)