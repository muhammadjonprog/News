package com.saidov.news2022.modules.main.ui.vm

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.saidov.news2022.app.NewsApplication
import com.saidov.news2022.modules.main.ui.model.Article
import com.saidov.news2022.modules.main.ui.model.NewsResponse
import com.saidov.news2022.repository.dbrepository.SqlRepository
import com.saidov.news2022.repository.networkrepository.repository.NetworkRepository
import com.saidov.news2022.repository.networkrepository.event.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by MUHAMMADJON SAIDOV on 22,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */

class MainViewModel(
    private val context: Application,
    private val networkRepository : NetworkRepository,
    private val sqlRepository : SqlRepository
) : ViewModel()  {

    private val mAllHistory = MutableLiveData<ArrayList<Article>>()
    var allHistory : LiveData<ArrayList<Article>> = mAllHistory

    private val mAllFavorite = MutableLiveData<ArrayList<Article>>()
    var allFavorite: LiveData<ArrayList<Article>> = mAllFavorite

    //private val mAllBreakingNews = MutableLiveData<Response<NewsResponse>>()
    private val mAllBreakingNews = MutableLiveData<Resource<NewsResponse>>()


    var breakingNewsResponse : NewsResponse? = null

    val breakingNews : MutableLiveData<Resource<NewsResponse>> = mAllBreakingNews

    fun loadHistory() {
        viewModelScope.launch {
            val response = sqlRepository.getArticleHistory()
            mAllHistory.postValue(response)
        }
    } //var breakingNews : MutableLiveData<Response<NewsResponse>> = mAllBreakingNews

    fun loadFavorite() {
        viewModelScope.launch {
            val response = sqlRepository.getArticleFavorite()
            mAllFavorite.postValue(response)
        }
    }

    fun saveHistory(article: Article) {
        viewModelScope.launch {
            sqlRepository.saveHistory(article)
        }
    }

    fun saveFavorite(article: Article) {
        viewModelScope.launch {
            sqlRepository.saveFavorite(article)
        }
    }

    fun removeHistory(article: Article) {
        viewModelScope.launch {
            val result = sqlRepository.deleteHistory(article.id)
            if (result) {
                loadHistory()
            }
        }
    }

    fun removeFavorite(article: Article) {
        viewModelScope.launch {
            val result = sqlRepository.deleteFavorite(article.id)
            if (result) {
                loadFavorite()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun newsApi() {
        viewModelScope.launch(Dispatchers.IO) {
            checkBreakingNewsCall()
//            val response = newsRepository.getBreakingNews()
//            mAllBreakingNews.postValue(response)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private suspend fun checkBreakingNewsCall() {
        breakingNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = networkRepository.getNews()
                breakingNews.postValue(handledBreakingNewsResponse(response))
            } else {
                breakingNews.postValue(Resource.Error("Нет соединения с интернетом"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> breakingNews.postValue(Resource.Error("Сбой в работе сети"))
                is SocketTimeoutException -> breakingNews.postValue(Resource.Error("Тайм аут!"))
                is ConnectException -> breakingNews.postValue(Resource.Error("Ошибка подключения!"))
                else -> breakingNews.postValue(Resource.Error("Ошибка преобразования"))
            }
        }
    }

    private fun handledBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
                }
//                else {
//                }
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}