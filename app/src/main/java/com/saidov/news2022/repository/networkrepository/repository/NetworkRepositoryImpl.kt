package com.saidov.news2022.repository.networkrepository.repository

import android.content.Context
import com.saidov.news2022.modules.main.ui.model.NewsResponse
import retrofit2.Response

/**
 * Created by MUHAMMADJON SAIDOV on 31,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */

class NetworkRepositoryImpl(context: Context) : NetworkRepository {
    override suspend fun getNews(): Response<NewsResponse> {
        return NetworkService.api.getBreakingNews()
    }


    override suspend fun searchNews(searchQuery: String, pageNumber: Int) {
        NetworkService.api.searchForNews(searchQuery, pageNumber)
    }


}