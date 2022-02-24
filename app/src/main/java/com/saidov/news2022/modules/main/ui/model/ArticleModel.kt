package com.saidov.news2022.modules.main.ui.model

import com.google.gson.annotations.SerializedName

/**
 * Created by MUHAMMADJON SAIDOV on 15,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
//TODO:ArticleModel шавад бехтар номи класс
data class Article(
    var id: Long,
    @SerializedName("source") var source: Source?,
    @SerializedName("author") val author: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("url") val url: String?,
    @SerializedName("urlToImage") val urlToImage: String,
    @SerializedName("publishedAt") val publishedAt: String?,
    @SerializedName("content") val content: String? )