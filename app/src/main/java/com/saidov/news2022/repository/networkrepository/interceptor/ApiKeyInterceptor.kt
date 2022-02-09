package com.saidov.news2022.repository.networkrepository.interceptor

import android.content.Context
import com.saidov.news2022.other.Constants.Companion.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by MUHAMMADJON SAIDOV on 04,февраль,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */

class ApiKeyInterceptor(context: Context) : BaseInterceptor(context) {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain
            .request()
            .newBuilder()
            .addHeader("Authorization", API_KEY)
            .build()

        val response = chain.proceed(newRequest)
        when (response.code) {
            200 -> {
                return response
            }
            else -> {
                throw Exception(response.body.toString())
            }
        }
    }
}