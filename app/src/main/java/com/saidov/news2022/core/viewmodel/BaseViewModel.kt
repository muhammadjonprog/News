package com.saidov.news2022.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saidov.news2022.repository.networkrepository.repository.NetworkService
import com.saidov.news2022.repository.networkrepository.api.Api
import com.saidov.news2022.repository.networkrepository.event.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

/**
 * Created by MUHAMMADJON SAIDOV on 30,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
abstract class BaseViewModel : ViewModel(), KoinComponent