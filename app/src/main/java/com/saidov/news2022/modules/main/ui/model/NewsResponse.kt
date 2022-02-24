package com.saidov.news2022.modules.main.ui.model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

/**
 * Created by MUHAMMADJON SAIDOV on 18,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
data class NewsResponse(
    @SerializedName("articles")
    val articleModels: ArrayList<ArticleModel>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)
