package com.saidov.news2022.repository.networkrepository.repository

import com.saidov.news2022.other.Constants
import com.saidov.news2022.repository.networkrepository.api.NewsService
import com.saidov.news2022.repository.networkrepository.interceptor.ApiKeyInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by MUHAMMADJON SAIDOV on 31,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
interface NetworkDataSource {

    fun getNewsService(): NewsService

    class Base() : NetworkDataSource {

        private fun getClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            return OkHttpClient.Builder().addInterceptor(logging)
                .addNetworkInterceptor(ApiKeyInterceptor()).build()
        }

        override fun getNewsService(): NewsService {
            return Retrofit.Builder().baseUrl(Constants.BASE_URL).client(getClient())
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(NewsService::class.java)
        }
    }
}