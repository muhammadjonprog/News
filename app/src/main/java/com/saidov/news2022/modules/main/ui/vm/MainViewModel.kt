package com.saidov.news2022.modules.main.ui.vm

import android.util.Log
import androidx.lifecycle.*
import com.saidov.news2022.core.viewmodel.BaseViewModel
import com.saidov.news2022.modules.main.settings.model.SettingsCategoryModel
import com.saidov.news2022.modules.main.ui.model.Article
import com.saidov.news2022.modules.main.ui.model.NewsResponse
import com.saidov.news2022.other.Constants.Companion.SEARCH_DELAY
import com.saidov.news2022.repository.networkrepository.event.Resource
import kotlinx.coroutines.*

/**
 * Created by MUHAMMADJON SAIDOV on 22,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */

class MainViewModel() : BaseViewModel() {
    private val tag = "A"
    private val mAllHistory = MutableLiveData<ArrayList<Article>>()
    var allHistory: LiveData<ArrayList<Article>> = mAllHistory


    private val mAllFavorite = MutableLiveData<ArrayList<Article>>()
    var allFavorite: LiveData<ArrayList<Article>> = mAllFavorite

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
        val technology = SettingsCategoryModel("technology", "ru", "ru", "Технологии", true)
        list.add(technology)
        val entertainment = SettingsCategoryModel("entertainment", "ru", "ru", "Развлечение", true)
        list.add(entertainment)
        value = list
    }
    var settingsCategory: MutableLiveData<ArrayList<SettingsCategoryModel>> = mSettingsCategory

    private val mNewsMutableHash = HashMap<String, MutableLiveData<Resource<NewsResponse>>>()
    val newsLiveDataMap: Map<String, LiveData<Resource<NewsResponse>>> = mNewsMutableHash

    fun addToHash(key: String) {
        if (mNewsMutableHash[key] == null) {
            mNewsMutableHash[key] = MutableLiveData<Resource<NewsResponse>>()
        }
    }

    fun removeFromHash(key: String) {
        if (mNewsMutableHash[key] != null) {
            mNewsMutableHash.remove(key)
        }
    }

    fun newsByCategory(category: String, code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val livedata = mNewsMutableHash[category]
            livedata?.let {
                asyncRequest(livedata) {
                    network.getApi().getNewsByCategory(countryCode = code, category = category)
                }
            }
        }
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

    fun searchByHistory(query: String) {
        viewModelScope.launch {
            val response = db.searchByHistory(query)
            mAllHistory.postValue(response)
        }
    }

    fun searchByFavorite(query: String) {
        viewModelScope.launch {
            val response = db.searchByFavorite(query)
            mAllFavorite.postValue(response)
        }
    }

    fun searchByTitle(queryInTitle: String, key: String) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(SEARCH_DELAY)
            val livedata = mNewsMutableHash[key]
            Log.d("livedata", livedata.toString())
            livedata?.let {
                asyncRequest(livedata) {
                    network.getApi().search(searchQuery = queryInTitle)
                }
            }
        }

    }


    override fun onCleared() {
        super.onCleared()
        Log.e(tag, "VM cleared")
    }

    init {
        Log.e(tag, "VM created")
    }
}
