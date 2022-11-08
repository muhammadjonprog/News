package com.saidov.news2022.repository

import com.saidov.news2022.modules.main.ui.model.ArticleModel
import com.saidov.news2022.repository.dbrepository.LocalDataSource
import com.saidov.news2022.repository.networkrepository.repository.NetworkDataSource

class DataSourceRepository(
    private val localDataSource: LocalDataSource, private val networkRepository: NetworkDataSource
) {

    fun saveHistory(articleModel: ArticleModel) {
        localDataSource.saveHistory(articleModel)
    }

    fun getArticleHistory() = localDataSource.getArticleHistory()

    fun getArticleFavorite() = localDataSource.getArticleFavorite()

    fun deleteHistory(id: Long) = localDataSource.deleteHistory(id)

    fun deleteFavorite(id: Long) = localDataSource.deleteFavorite(id)

    fun searchByHistory(query: String) = localDataSource.searchByHistory(query)

    fun searchByFavorite(query: String) = localDataSource.searchByFavorite(query)

    fun searchNews(query: String) = networkRepository.getNewsService().search(query)

    fun getNewsByCategory(countryCode: String?, category: String) =
        networkRepository.getNewsService().getNewsByCategory(countryCode, category)

}