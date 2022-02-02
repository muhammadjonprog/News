package com.saidov.news2022.app

import android.app.Application
import com.saidov.news2022.BuildConfig.DEBUG
import com.saidov.news2022.core.di.*
import org.koin.android.BuildConfig.DEBUG
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import java.util.logging.Level

/**
 * Created by MUHAMMADJON SAIDOV on 25,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
//        startKoin {
//            androidLogger(org.koin.core.logger.Level.DEBUG)
//            androidContext(this@NewsApplication)
//            modules(listOf(repositorySqlModule, repositoryNetworkModule,
//                viewModelModule, retrofitModule, apiModule))
//        }
    }
}