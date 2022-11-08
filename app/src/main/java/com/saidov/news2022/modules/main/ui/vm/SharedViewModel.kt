package com.saidov.news2022.modules.main.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.saidov.news2022.core.viewmodel.BaseViewModel
import com.saidov.news2022.modules.main.settings.model.SettingsCategoryModel
import com.saidov.news2022.modules.main.ui.model.ArticleModel
import com.saidov.news2022.modules.main.ui.model.NewsResponse
import com.saidov.news2022.other.Constants.Companion.SEARCH_DELAY
import com.saidov.news2022.repository.DataSourceRepository
import com.saidov.news2022.repository.networkrepository.event.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.collections.set

/**
 * Created by MUHAMMADJON SAIDOV on 22,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */

class SharedViewModel(private val dataSourceRepository: DataSourceRepository) : BaseViewModel() {
    private val _allHistory = MutableLiveData<ArrayList<ArticleModel>>()
    var allHistory: LiveData<ArrayList<ArticleModel>> = _allHistory

    private val _allFavorite = MutableLiveData<ArrayList<ArticleModel>>()
    var allFavorite: LiveData<ArrayList<ArticleModel>> = _allFavorite

    private val _news = HashMap<String, MutableLiveData<Resource<NewsResponse>>>()
    val news: Map<String, LiveData<Resource<NewsResponse>>> = _news

    private val _toolbarTitle = MutableLiveData<String>()
    val toolbarTitle: LiveData<String> = _toolbarTitle

    private val _toolbarBackVisibility = MutableLiveData<Boolean>()
    val toolbarBackVisibility: LiveData<Boolean> = _toolbarBackVisibility

    private val _settingsCategory = MutableLiveData<ArrayList<SettingsCategoryModel>>().apply {
        val list: ArrayList<SettingsCategoryModel> = ArrayList<SettingsCategoryModel>().apply {
            add(SettingsCategoryModel("sport", "ru", "ru", "Спорт", true))
            add(SettingsCategoryModel("business", "ru", "ru", "Бизнес", true))
            add(SettingsCategoryModel("science", "ru", "ru", "Наука", true))
            add(SettingsCategoryModel("politics", "ru", "ru", "Политика", true))
            add(SettingsCategoryModel("technology", "ru", "ru", "Технологии", true))
            add(SettingsCategoryModel("entertainment", "ru", "ru", "Развлечение", true))
        }
        value = list
    }

    var settingsCategory: MutableLiveData<ArrayList<SettingsCategoryModel>> = _settingsCategory

    fun setToolbarName(title: String) {
        _toolbarTitle.value = title
    }

    fun setToolBarBackVisibility(status: Boolean) {
        _toolbarBackVisibility.value = status
    }

    fun addToHash(key: String) {
        if (_news[key] == null) _news[key] = MutableLiveData<Resource<NewsResponse>>()
    }

    fun removeFromHash(key: String) {
        if (_news[key] != null) _news.remove(key)
    }

    fun loadHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            _allHistory.postValue(dataSourceRepository.getArticleHistory())
        }
    }

    fun loadFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            _allFavorite.postValue(dataSourceRepository.getArticleFavorite())
        }
    }

    fun saveHistory(articleModel: ArticleModel) {
        viewModelScope.launch(Dispatchers.IO) {
            dataSourceRepository.saveHistory(articleModel)
        }
    }

    fun saveFavorite(articleModel: ArticleModel) {
        viewModelScope.launch(Dispatchers.IO) {
            dataSourceRepository.saveHistory(articleModel)
        }
    }

    fun removeHistory(articleModel: ArticleModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = dataSourceRepository.deleteHistory(articleModel.id)
            if (result) loadHistory()
        }
    }

    fun removeFavorite(articleModel: ArticleModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = dataSourceRepository.deleteFavorite(articleModel.id)
            if (result) loadFavorite()
        }
    }

    fun searchByHistory(query: String) {
        viewModelScope.launch {
            _allHistory.postValue(dataSourceRepository.searchByHistory(query))
        }
    }

    fun searchByFavorite(query: String) {
        viewModelScope.launch {
            _allFavorite.postValue(dataSourceRepository.searchByFavorite(query))
        }
    }

    fun newsByCategory(category: String, code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val livedata = _news[category]
            livedata?.let {
                asyncRequest(livedata) {
                    dataSourceRepository.getNewsByCategory(code, category)
                }
            }
        }
    }

    fun searchByTitle(queryInTitle: String, key: String) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(SEARCH_DELAY)
            val livedata = _news[key]
            livedata?.let {
                asyncRequest(livedata) {
                    dataSourceRepository.searchNews(queryInTitle)
                }
            }
        }

    }
}
