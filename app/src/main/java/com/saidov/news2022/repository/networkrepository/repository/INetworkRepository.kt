package com.saidov.news2022.repository.networkrepository.repository

import com.saidov.news2022.repository.networkrepository.api.NewsService

/**
 * Created by MUHAMMADJON SAIDOV on 31,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
interface INetworkRepository {

    fun getApi(): NewsService

}