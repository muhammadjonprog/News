package com.saidov.news2022.core.di


import com.saidov.news2022.modules.main.ui.vm.SharedViewModel
import com.saidov.news2022.repository.DataSourceRepository
import com.saidov.news2022.repository.dbrepository.LocalDataSource
import com.saidov.news2022.repository.networkrepository.repository.NetworkDataSource
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

/**
 * Created by MUHAMMADJON SAIDOV on 31,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */

val repositoryModule = module {

    single<LocalDataSource> {
        LocalDataSource.Base(context = get())
    }

    single<NetworkDataSource> {
        NetworkDataSource.Base()
    }

    single<DataSourceRepository> {
        DataSourceRepository(
            localDataSource = get(),
            networkRepository = get()
        )
    }
}
val vm = module {
    viewModel {
        SharedViewModel(get())
    }
}


