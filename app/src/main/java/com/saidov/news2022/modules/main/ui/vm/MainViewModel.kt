package com.saidov.news2022.modules.main.ui.vm

import androidx.lifecycle.*
import com.saidov.news2022.core.viewmodel.BaseViewModel
import com.saidov.news2022.modules.main.settings.model.SettingsCategoryModel
import com.saidov.news2022.modules.main.ui.model.Article
import com.saidov.news2022.modules.main.ui.model.NewsResponse
import com.saidov.news2022.repository.networkrepository.event.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * Created by MUHAMMADJON SAIDOV on 22,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */

class MainViewModel() : BaseViewModel() {

    private val mAllHistory = MutableLiveData<ArrayList<Article>>()
    var allHistory: LiveData<ArrayList<Article>> = mAllHistory

    private val mAllFavorite = MutableLiveData<ArrayList<Article>>()
    var allFavorite: LiveData<ArrayList<Article>> = mAllFavorite

    private val mAllBreakingNews = MutableLiveData<Resource<NewsResponse>>()
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = mAllBreakingNews

    private val mSettingsCategory = MutableLiveData<ArrayList<SettingsCategoryModel>>().apply {
        val list: ArrayList<SettingsCategoryModel> = ArrayList()
        val sport = SettingsCategoryModel("sport", "ru", "ru", "Спорт", true)
        list.add(sport)
        val business = SettingsCategoryModel("business", "ru", "ru", "Бизнес", true)
        list.add(business)
        val science = SettingsCategoryModel("science", "ru", "ru", "Наука", true)
        list.add(science)
        val politics = SettingsCategoryModel("politics", "ru", "ru", "Политика", true)
        list.add(politics)
        value = list
    }
    var settingsCategory: MutableLiveData<ArrayList<SettingsCategoryModel>> = mSettingsCategory

    private val mNewsMutableHash = HashMap<String, MutableLiveData<Response<Article>>>()
    val newsLiveDataMap: Map<String, LiveData<Response<Article>>> = mNewsMutableHash

    fun addToHash(text: String) {
        mNewsMutableHash.put(text, MutableLiveData())
    }

    fun removeFromHash(text: String) {
        mNewsMutableHash.remove(text)
    }

    fun loadHistory() {
        viewModelScope.launch {
            val response = db.getArticleHistory()
            mAllHistory.postValue(response)
        }
    }

    fun loadFavorite() {
        viewModelScope.launch {
            val response = db.getArticleFavorite()
            mAllFavorite.postValue(response)
        }
    }

    fun saveHistory(article: Article) {
        viewModelScope.launch {
            db.saveHistory(article)
        }
    }

    fun saveFavorite(article: Article) {
        viewModelScope.launch {
            db.saveFavorite(article)
        }
    }

    fun removeHistory(article: Article) {
        viewModelScope.launch {
            val result = db.deleteHistory(article.id)
            if (result) {
                loadHistory()
            }
        }
    }

    fun removeFavorite(article: Article) {
        viewModelScope.launch {
            val result = db.deleteFavorite(article.id)
            if (result) {
                loadFavorite()
            }
        }
    }

    fun newsByCategory(category: String, code: String) {
        viewModelScope.launch {
            Dispatchers.IO
            breakingNews.postValue(Resource.Loading())
            try {
                asyncRequest(breakingNews) {
                    network.getApi().getNewsByCategory(countryCode = code, category = category)
                }
            } catch (t: Throwable) {
            }
        }
    }
//    private fun handledBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
//        if (response.isSuccessful) {
//            response.body()?.let { resultResponse ->
//                if (breakingNewsResponse == null) {
//                    breakingNewsResponse = resultResponse
//                }
//
//                return Resource.Success(breakingNewsResponse ?: resultResponse)
//            }
//        }
//        return Resource.Error(response.message())
//    }
}