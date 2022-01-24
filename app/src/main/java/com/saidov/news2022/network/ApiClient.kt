package com.saidov.news2022.network

import com.saidov.news2022.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by MUHAMMADJON SAIDOV on 15,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
 class ApiClient {
        companion object{
            private val  retrofit by lazy {
                val logging= HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client= OkHttpClient.Builder().addInterceptor(logging).build()
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            }
            val api by lazy {
                retrofit.create(ApiService::class.java)
            }
        }
}
