package de.home24.data.remote.model.article

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ArticleFilterValue(
        val id: String?,
        val priority: Int?,
        val title: String?,
        val colorCode: String?,
        val colorImage: String?,
        @Json(name = "_metadata")
        val metadata: Metadata?
)