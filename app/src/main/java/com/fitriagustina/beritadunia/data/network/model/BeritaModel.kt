package com.fitriagustina.beritadunia.data.network.model

import com.fitriagustina.beritadunia.data.room.entity.BeritaEntity
import com.google.gson.annotations.SerializedName

//menyimpan hasil respon dari rest api
data class BeritaModel(
    @SerializedName("status") val status: String,
    @SerializedName("articles") val articles: List<BeritaEntity>
) {
    data class Article(
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("author") val author: String,
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("url") val url: String,
        @SerializedName("urlToImage") val urlToImage: String,
        @SerializedName("publishedAt") val publishedAt: String,
        @SerializedName("content") val content: String
    )
}
