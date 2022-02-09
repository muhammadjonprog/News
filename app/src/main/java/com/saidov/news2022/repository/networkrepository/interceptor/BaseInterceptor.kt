package com.saidov.news2022.repository.networkrepository.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.saidov.news2022.repository.networkrepository.exeption.InternetConnException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by MUHAMMADJON SAIDOV on 06,февраль,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */

abstract class BaseInterceptor(val context: Context) : Interceptor {

    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (hasInternetConnection()) {
            return chain.proceed(chain.request())
        }
        throw  InternetConnException("Internet no connection")
    }
}