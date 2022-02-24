package com.saidov.news2022.app

import android.app.Application
import com.saidov.news2022.core.di.repositoryModule
import com.saidov.news2022.core.di.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by MUHAMMADJON SAIDOV on 25,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
//TODO: NewsApplication номаша бехтар мешавад просто App гуем. Ва дар уровени папкахои core, modules... баробар гузорем. Набояд папкаи app-ба истад
class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(org.koin.core.logger.Level.DEBUG)
            androidContext(this@NewsApplication)
            modules(listOf(repositoryModule, vmModule))
        }
    }
}