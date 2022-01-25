package com.saidov.news2022.vm

import android.app.Application
import androidx.lifecycle.*
import com.saidov.news2022.model.Article
import com.saidov.news2022.model.NewsResponse
import com.saidov.news2022.network.ApiClient
import com.saidov.news2022.repository.NewsRepository
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
class NewsViewModel(context: Application) : AndroidViewModel(context) {
   lateinit var  newsRepository : NewsRepository
    var  allHistory : LiveData<List<Article>> = MutableLiveData()
    var  breakingNews : MutableLiveData<NewsResponse> = MutableLiveData()

   init {
       newsRepository = NewsRepository(context)
      // allHistory = newsRepository.getArticleHistory()
   }


    fun getNewsObserver() : MutableLiveData<NewsResponse>{
        return breakingNews
    }

//    fun getHistoryObserver() : LiveData<List<Article>> {
//        return allHistory
//    }
    fun getHistoryObserver() = newsRepository.getArticleHistory()


    fun insertHistory(article: Article)= viewModelScope.launch(Dispatchers.IO) {
        newsRepository.insertNewsHistory(article)
    }

    fun insertFavorite(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        newsRepository.insertNewsFavorites(article)
    }

    fun makeApiCall(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            //newsRepository.getBreakingNews()
            val retro = ApiClient.api
            val response = retro.getBreakingNews("ru",category)
            breakingNews.postValue(response)
        }
    }

    fun make() {
        viewModelScope.launch(Dispatchers.IO) {
            val retro = ApiClient.api
            val call = retro.getN()
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