package com.saidov.news2022.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saidov.news2022.modules.main.ui.model.NewsResponse
import com.saidov.news2022.repository.dbrepository.ISqlRepository
import com.saidov.news2022.repository.networkrepository.event.Resource
import com.saidov.news2022.repository.networkrepository.repository.INetworkRepository
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
    protected val network: INetworkRepository by inject()
    protected val db: ISqlRepository by inject()

    //ToDo: Ин хам нагз, лекин бо корутины боз осонтар кардаги асинх запросхоя. Бафикрам рефторифт аз версияи 2.6 корутины поддержка мекунад
    protected fun <T> asyncRequest(
        liveData: MutableLiveData<Resource<T>>,
        request: () -> Call<T>) {
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
                        is IOException -> liveData.value = Resource.Error("Нет подключение к интернету!")
                        is SocketTimeoutException -> liveData.value = Resource.Error("Тайм аут!")
                        is ConnectException -> liveData.value = Resource.Error("Ошибка подключения!")
                    }
                }
            })


    }
}