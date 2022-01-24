package com.saidov.news2022.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saidov.news2022.model.NewsResponse
import com.saidov.news2022.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by MUHAMMADJON SAIDOV on 22,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
class NewsViewModel() : ViewModel() {
    var  breakingNews : MutableLiveData<NewsResponse> = MutableLiveData()

    fun getNewsObserver() : MutableLiveData<NewsResponse>{
        return breakingNews
    }

    fun makeApiCall(category: String){
        viewModelScope.launch(Dispatchers.IO) {
           //val retro = RetrofitInstance.getRetrofit().create(RetroService::class.java)
            val retro = ApiClient.api
            val response = retro.getBreakingNews("ru",category)
            breakingNews.postValue(response)
        }
    }
    fun make(){
        viewModelScope.launch(Dispatchers.IO) {
            //  var retro = RetrofitInstance.getRetrofit().create(RetroService::class.java)
            val retro = ApiClient.api
            val call = retro.getN()
            //val response = retro.getNews()
            //val response = retro.getNewsCategory(category)
            //breakingNews.postValue(response)
            call.enqueue(object : Callback<NewsResponse?> {
                override fun onResponse(
                    call: Call<NewsResponse?>,
                    response: Response<NewsResponse?>) {

                    breakingNews.postValue(response.body())
                }

                override fun onFailure(call: Call<NewsResponse?>, t: Throwable) {

                }
            })
        }
    }


}