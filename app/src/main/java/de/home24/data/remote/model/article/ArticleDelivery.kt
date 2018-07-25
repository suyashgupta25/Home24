package de.home24.data.remote.model.article

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
class ArticleDelivery(
        val time: ArticleDeliveryTime?,
        val type: String?,
        val terms: String?,
        val deliveredBy: String?,
        val text: String?,
        val typeLabelLink: String?,
        val price: ArticlePrice?,
        val details: List<ArticleDeliveryDetails>? = listOf<ArticleDeliveryDetails>()
)