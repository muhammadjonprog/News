package com.saidov.news2022.modules.main.ui.model

import com.google.gson.annotations.SerializedName
/**
 * Created by MUHAMMADJON SAIDOV on 15,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 *
 */
//TODO:SourceModel шавад бехтар номи класс
data class SourceModel(
    @SerializedName("id")
    val id: Any,
    @SerializedName("name")
    var name: String
    )


