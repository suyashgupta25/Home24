package de.home24.data.remote.model.article

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
class ArticleBrand (
        val id: String?,
        val title: String?,
        val logo: List<String>? = listOf<String>(),
        val description: String?
)