package com.example.myonlinemarket

import android.app.Application
import com.example.myonlinemarket.di.dataBaseModule
import com.example.myonlinemarket.di.netModule
import com.example.myonlinemarket.di.repositoryModule
import com.example.myonlinemarket.di.useCaseModule
import com.example.myonlinemarket.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

open class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            modules(viewModelModule, netModule, dataBaseModule, useCaseModule,repositoryModule)
        }
    }

}