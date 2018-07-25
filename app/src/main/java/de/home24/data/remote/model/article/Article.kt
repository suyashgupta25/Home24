package de.home24.data.remote.model.article

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class Article(
        val sku: String?,
        val title: String?,
        val description: String?,
        val prevPrice: ArticlePrice?,
        val manufacturePrice: String?,
        val price: ArticlePrice?,
        val delivery: ArticleDelivery?,
        val brand: ArticleBrand?,
        val media: List<ArticleMedia>? = listOf<ArticleMedia>(),
        val assemblyService: String?,
        val availability: String?,
        val url: String?,
        val energyClass: String?,
        @Json(name = "_metadata")
        val metadata: Metadata?,
        @Json(name = "_links")
        val links: ArticleDataLinks?
)