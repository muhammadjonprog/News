package com.saidov.news2022.vm

import android.app.Application
import androidx.lifecycle.*
import com.saidov.news2022.model.Article
import com.saidov.news2022.model.NewsResponse
import com.saidov.news2022.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
/**
 * Created by MUHAMMADJON SAIDOV on 22,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
class NewsViewModel(context: Application) : AndroidViewModel(context) {
    var newsRepository: NewsRepository = NewsRepository(context)
    private val mAllHistory = MutableLiveData<ArrayList<Article>>()
    var allHistory: LiveData<ArrayList<Article>> = mAllHistory

    private val mAllFavorite = MutableLiveData<ArrayList<Article>>()
    var allFavorite: LiveData<ArrayList<Article>> = mAllFavorite

    private val mAllBreakingNews = MutableLiveData<NewsResponse>()
    var breakingNews: MutableLiveData<NewsResponse> = mAllBreakingNews

    fun getNewsApi(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mAllBreakingNews.postValue(newsRepository.getBreakingNews())
        }
    }

    fun getHistory() {
        viewModelScope.launch {
            val response = newsRepository.getArticleHistory()
            mAllHistory.postValue(response)
        }
    }

    fun getFavorite() {
        viewModelScope.launch {
            val response = newsRepository.getArticleFavorite()
            mAllFavorite.postValue(response)
        }
    }


    fun insertHistory(article: Article) {
        viewModelScope.launch {
            newsRepository.insertNewsHistory(article)
        }
    }

    fun insertFavorite(article: Article) {
        viewModelScope.launch {
            newsRepository.insertNewsFavorites(article)
        }
    }

    fun deleteHistory(article: Article) {
        viewModelScope.launch {
          val result = newsRepository.deleteArticleHistory(article.id)
            if (result) {
                getHistory()
            }
        }
    }

    fun deleteFavorite(article: Article) {
        viewModelScope.launch {
        val result = newsRepository.deleteArticleFavorite(article.id)
            if (result) {
                getFavorite()
            }
        }
    }
}