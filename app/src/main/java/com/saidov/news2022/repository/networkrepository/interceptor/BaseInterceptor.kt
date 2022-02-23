package com.saidov.news2022.repository.networkrepository.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by MUHAMMADJON SAIDOV on 06,февраль,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */

abstract class BaseInterceptor(val context: Context) : Interceptor {
    //ToDO: Interceptor ин хар як запросба кор мекунад. Тафтиши интернет пеш аз фиристодани хар як запрос ин нодуруст. Бехтараш мо дар хамон onFailure catch карда гирим
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
        throw  IOException("Internet no connection")
    }
}