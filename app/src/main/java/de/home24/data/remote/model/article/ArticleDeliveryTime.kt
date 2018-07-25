package de.home24.data.remote.model.article

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ArticleDeliveryTime(
        val renderAs: String?,
        val amount: String?,
        val units: String?
)