package com.saidov.news2022.model

import com.google.gson.annotations.SerializedName
/**
 * Created by MUHAMMADJON SAIDOV on 15,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 *
 */
data class Source(
    @SerializedName("id")
    val id: Any,
    @SerializedName("name")
    val name: String
    )


