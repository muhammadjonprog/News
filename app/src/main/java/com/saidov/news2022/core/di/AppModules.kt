package com.saidov.news2022.core.di


import com.saidov.news2022.modules.main.ui.vm.MainViewModel
import com.saidov.news2022.repository.dbrepository.ISqlRepository
import com.saidov.news2022.repository.dbrepository.SqlRepositoryImpl
import com.saidov.news2022.repository.networkrepository.repository.INetworkRepository
import com.saidov.news2022.repository.networkrepository.repository.NetworkRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

/**
 * Created by MUHAMMADJON SAIDOV on 31,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */

val vmModule = module {
    viewModel {
        MainViewModel()
    }
}

val repositoryModule = module {
    single<ISqlRepository> {
        SqlRepositoryImpl(context = get())
    }
    single<INetworkRepository> {
        NetworkRepositoryImpl(get())
    }
}


