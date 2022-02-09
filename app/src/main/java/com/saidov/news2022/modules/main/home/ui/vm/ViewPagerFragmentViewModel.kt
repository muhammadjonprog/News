package com.saidov.news2022.modules.main.home.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saidov.news2022.core.viewmodel.BaseViewModel
import com.saidov.news2022.modules.main.ui.model.NewsResponse
import com.saidov.news2022.repository.networkrepository.event.Resource

/**
 * Created by MUHAMMADJON SAIDOV on 07,февраль,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
class ViewPagerFragmentViewModel : BaseViewModel() {
    private val mNewsResponse : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private val newsResponse : LiveData<Resource<NewsResponse>> = mNewsResponse

    fun getNewsByCategory(category: String){
        mNewsResponse.postValue(Resource.Loading())
        try {
            asyncRequest(mNewsResponse) {
                network.getApi().getNewsByCategory(category = category, countryCode = null)
            }
        } catch (t: Throwable) {
            when (t) {

            }
        }
    }

    fun searchByTitle(query : String){
        mNewsResponse.postValue(Resource.Loading())
        try {
            asyncRequest(mNewsResponse) {
                network.getApi().search(searchQuery = query)
            }
        } catch (t: Throwable) {
            when (t) {
            }
        }
    }
}