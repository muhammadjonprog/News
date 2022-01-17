package com.saidov.news2022.Common

import com.saidov.news2022.model.RetrofitClient
import com.saidov.news2022.model.RetrofitServices

/**
 * Created by MUHAMMADJON SAIDOV on 15,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 *
 */
 object Common {
    private const val  BASE_URL = "https://newsapi.org/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}
