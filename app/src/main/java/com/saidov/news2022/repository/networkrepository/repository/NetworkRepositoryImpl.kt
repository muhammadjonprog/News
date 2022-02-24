package com.saidov.news2022.repository.networkrepository.repository

import android.content.Context
import com.saidov.news2022.other.Constants.Companion.BASE_URL
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

class NetworkRepositoryImpl() : INetworkRepository {

    private fun getClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient
            .Builder()
            .addInterceptor(logging)
            .addNetworkInterceptor(ApiKeyInterceptor())
            .build()
        return client
    }

    override fun getApi(): NewsService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsService::class.java)
        return retrofit
    }
}