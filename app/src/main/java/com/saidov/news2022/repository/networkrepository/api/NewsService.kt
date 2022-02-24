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
//ToDO: Номи ин класса желательно Service гуем бехтар, яъне NewsService
interface NewsService {

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
    ): Call<NewsResponse>

}