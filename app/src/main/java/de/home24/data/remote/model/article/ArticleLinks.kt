package de.home24.data.remote.model.article

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ArticleLinks(
        val next: ArticleLink?,
        val prev: ArticleLink?,
        val self: ArticleLink?,
        val articles: List<ArticleLink>? = listOf<ArticleLink>()
)