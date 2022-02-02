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

//    @GET("v2/top-headlines")
//
//    suspend fun getBreakingNews(
//
//        @Query("country")
//        countryCode: String = "ru", //default to us
//
//        @Query("category")
//        category: String = "sports",
//
//        @Query("page")  //to paginate the request
//        pageNumber: Int= 1,
//
//        @Query("apiKey")
//        apiKey: String= API_KEY
//
//    ): ResponseWrapper<NewsResponse> //return response

    @GET("v2/top-headlines")

    suspend fun getBreakingNews(

        @Query("country")
        countryCode: String = "ru", //default to us

        @Query("category")
        category: String = "sports",

        @Query("page")  //to paginate the request
        pageNumber: Int= 1,

        @Query("apiKey")
        apiKey: String= API_KEY
    ) : Response<NewsResponse>

//
//    @GET("5dcc12d554000064009c20fc")
//    suspend fun getUsers(
//        @Query("page") page: Int
//    ): ResponseWrapper<Users>



    //search
    //https://newsapi.org/v2/top-headlines?country=ru&category=business&q=Китай&apiKey=9a08716b46e54472ae8e71c450b67d2c
    @GET("v2/everything")

    suspend fun searchForNews(
        //request parameters to function
        @Query("q")
        searchQuery: String,
        @Query("page")  //to paginate the request
        pageNumber: Int= 1,
        @Query("apiKey")
        apiKey: String= API_KEY
    ): Response<NewsResponse> //return response

    @GET("/v2/top-headlines?country=ru&apiKey=9a08716b46e54472ae8e71c450b67d2c")

    suspend fun getNewsCategory(
        @Query("category")
        category: String
    ): NewsResponse//return response

    @GET("/v2/top-headlines?country=ru&category=sports&apiKey=9a08716b46e54472ae8e71c450b67d2c")
    suspend fun getN():Call<NewsResponse>
}