package de.home24.data.remote.model.article

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ArticleEmbedded(

        val articles: List<Article>? = listOf<Article>(),
        val filters: List<ArticleFilter>? = listOf<ArticleFilter>()

)