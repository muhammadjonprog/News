package com.saidov.news2022.network

import com.saidov.news2022.model.Article
import com.saidov.news2022.model.NewsResponse
import com.saidov.news2022.utils.Constants.Companion.API_KEY
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by MUHAMMADJON SAIDOV on 20,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
interface ApiService {
    /* here we define single request that we can execute from the code
      */
    //we use Api interface to access api for request
    //function to get all breaking news from the api
    // we need to specify the type of http request -GET here
    //and we return the responses from the API
//    companion object{
//        private const val API_KEY = "9a08716b46e54472ae8e71c450b67d2c"
//    }
    @GET("v2/top-headlines")

    //function
    //async
    //coroutine
    suspend fun getBreakingNews(
        //request parameters to function
        @Query("country")
        countryCode: String = "ru", //default to us

        @Query("category")
        category: String = "sports",

        @Query("page")  //to paginate the request
        pageNumber: Int= 1,

        @Query("apiKey")
        apiKey: String= API_KEY

    ):NewsResponse //return response


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
    ):NewsResponse//return response




    @GET("/v2/top-headlines?country=ru&category=sports&apiKey=9a08716b46e54472ae8e71c450b67d2c")
    suspend fun getNews():NewsResponse

    @GET("/v2/top-headlines?country=ru&category=sports&apiKey=9a08716b46e54472ae8e71c450b67d2c")
    //function
    //async
    //coroutine
    suspend fun getN():Call<NewsResponse>
}