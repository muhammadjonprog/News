package com.saidov.news2022

import android.app.Application
import com.saidov.news2022.core.di.repositoryModule
import com.saidov.news2022.core.di.vm
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by MUHAMMADJON SAIDOV on 25,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(org.koin.core.logger.Level.DEBUG)
            androidContext(this@App)
            modules(listOf(repositoryModule, vm))
        }
    }
}