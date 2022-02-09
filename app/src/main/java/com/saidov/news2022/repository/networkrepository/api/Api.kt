package com.saidov.news2022.repository.networkrepository.api

import com.saidov.news2022.modules.main.ui.model.NewsResponse
import com.saidov.news2022.other.Constants.Companion.API_KEY
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by MUHAMMADJON SAIDOV on 20,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
interface Api {

    @GET("v2/top-headlines")
    fun getBreakingNews(
        @Query("country")
        countryCode: String = "ru", //default to us
        @Query("category")
        category: String = "sports",
        @Query("page")  //to paginate the request
        pageNumber: Int= 1
    ) : Call<NewsResponse>


    @GET("v2/top-headlines")
    fun getNewsByCategory(
        @Query("country")
        countryCode: String?,
        @Query("category")
        category: String
    ) : Call<NewsResponse>


    @GET("v2/everything")

     fun search(

        @Query("q")
        searchQuery: String

    ): Call<NewsResponse> //return response

}