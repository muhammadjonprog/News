package com.saidov.news2022.model


import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by MUHAMMADJON SAIDOV on 15,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 *
 */
interface RetrofitServices {
    @GET("v2/top-headlines?country=us&apiKey=9a08716b46e54472ae8e71c450b67d2c")
    fun getNewsList(): Call<MutableList<Article>>
}