package com.saidov.news2022.repository.networkrepository.repository

import com.saidov.news2022.modules.main.ui.model.NewsResponse
import com.saidov.news2022.repository.networkrepository.api.Api
import retrofit2.Call
import retrofit2.Response

/**
 * Created by MUHAMMADJON SAIDOV on 31,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
interface NetworkRepository {

    fun getApi(): Api

}