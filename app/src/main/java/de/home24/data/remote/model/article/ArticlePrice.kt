package de.home24.data.remote.model.article

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ArticlePrice(
        val amount: String?,
        val currency: String?,
        val recommendedRetailPrice: Boolean?
)