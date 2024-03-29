package com.saidov.news2022.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saidov.news2022.repository.dbrepository.LocalDataSource
import com.saidov.news2022.repository.networkrepository.event.Resource
import com.saidov.news2022.repository.networkrepository.repository.NetworkDataSource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by MUHAMMADJON SAIDOV on 30,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */

abstract class BaseViewModel : ViewModel(), KoinComponent {

    protected fun <T> asyncRequest(
        liveData: MutableLiveData<Resource<T>>, request: () -> Call<T>
    ) {
        liveData.postValue(Resource.Loading())
        request.invoke().enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        liveData.value = Resource.Success(it)
                    }
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                when (t) {
                    is SocketTimeoutException -> liveData.value = Resource.Error("Тайм аут!")
                    is ConnectException -> liveData.value = Resource.Error("Ошибка подключения!")
                    is IOException -> liveData.value =
                        Resource.Error("Нет подключение к интернету!")
                }
            }
        })
    }
}