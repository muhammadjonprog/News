package com.saidov.news2022.modules.main.ui.vm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saidov.news2022.core.di.repositorySqlModule
import com.saidov.news2022.repository.dbrepository.SqlRepository
import com.saidov.news2022.repository.dbrepository.SqlRepositoryImpl
import com.saidov.news2022.repository.networkrepository.repository.NetworkRepository
import com.saidov.news2022.repository.networkrepository.repository.NetworkRepositoryImpl

class MainViewModelProviderFactory(val app: Application) : ViewModelProvider.Factory{

    private val networkRepository : NetworkRepository = NetworkRepositoryImpl(context = app)
    private val sqlRepository:SqlRepository = SqlRepositoryImpl(context = app)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(context = app, networkRepository = networkRepository,
            sqlRepository = sqlRepository) as T
    }
}