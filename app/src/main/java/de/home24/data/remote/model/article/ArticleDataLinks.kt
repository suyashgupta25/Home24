package de.home24.data.remote.model.article

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ArticleDataLinks(
        val webShopUrl: String?,
        val self: ArticleLink?
)