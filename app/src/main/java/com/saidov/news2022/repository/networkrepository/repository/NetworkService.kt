package com.saidov.news2022.repository.networkrepository.repository

import com.saidov.news2022.other.Constants.Companion.BASE_URL
import com.saidov.news2022.repository.networkrepository.api.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by MUHAMMADJON SAIDOV on 15,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
class NetworkService {

//    private val BASE_URL = "https://newsapi.org"
//
//    // HttpLoggingInterceptor выводит подробности сетевого запроса в логи
//    private val loggingInterceptor = run {
//        val httpLoggingInterceptor = HttpLoggingInterceptor()
//        httpLoggingInterceptor.apply {
//            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        }
//    }
//
//    private val baseInterceptor: Interceptor = invoke { chain ->
//        val newUrl = chain
//            .request()
//            .url
//            .newBuilder()
//            .build()
//
//        val request = chain
//            .request()
//            .newBuilder()
//            .url(newUrl)
//            .build()
//
//        return@invoke chain.proceed(request)
//    }
//
//    private val client: OkHttpClient = OkHttpClient
//        .Builder()
//        .addInterceptor(loggingInterceptor)
//        .addInterceptor(baseInterceptor)
//        .build()
//
//    fun retrofitService(): Api {
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
//            .build()
//            .create(Api::class.java)
//    }

    companion object {
        private val retrofit by lazy {

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient
                .Builder()
                .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        val api by lazy {
            retrofit.create(Api::class.java)
        }
    }
}
