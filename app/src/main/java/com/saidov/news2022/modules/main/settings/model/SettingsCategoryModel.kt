package com.saidov.news2022.modules.main.settings.model

import com.google.gson.annotations.SerializedName
import com.saidov.news2022.modules.main.ui.model.Article
import java.util.ArrayList

/**
 * Created by MUHAMMADJON SAIDOV on 06,февраль,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
data class SettingsCategoryModel(
    @SerializedName("category") val category: String,
    @SerializedName("country") val country: String,
    @SerializedName("language") val language: String,
    @SerializedName("name") val name: String,

    var isChecked : Boolean
)