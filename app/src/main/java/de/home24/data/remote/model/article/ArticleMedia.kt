package de.home24.data.remote.model.article

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ArticleMedia(

        val uri: String?,
        val mimeType: String?,
        val type: String?,
        val priority: Int?,
        val size: String?

)