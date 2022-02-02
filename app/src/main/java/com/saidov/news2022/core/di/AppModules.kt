package com.saidov.news2022.core.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.saidov.news2022.modules.main.ui.vm.MainViewModel
import com.saidov.news2022.other.Constants.Companion.BASE_URL
import com.saidov.news2022.repository.dbrepository.SqlRepositoryImpl
import com.saidov.news2022.repository.networkrepository.api.Api
import com.saidov.news2022.repository.networkrepository.repository.NetworkRepositoryImpl
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by MUHAMMADJON SAIDOV on 31,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 *
 */
val viewModelModule = module {
    viewModel {
        MainViewModel(get(), get(), get())
    }
}

val repositorySqlModule = module {
    single {
        SqlRepositoryImpl(get())
    }
}

val repositoryNetworkModule = module {
    single {
        NetworkRepositoryImpl(get())
    }
}

val apiModule = module {
    fun provideUseApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }
    single { provideUseApi(get()) }
}


val retrofitModule = module {

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()

        return okHttpClientBuilder.build()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()
    }

    single { provideGson() }
    single { provideHttpClient() }
    single { provideRetrofit(get(), get()) }
}