package de.home24.data.remote.model.article

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ArticleLink(
        val href: String?
)