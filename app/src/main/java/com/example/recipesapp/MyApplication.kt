package com.example.recipesapp

import android.app.Application
import com.example.di.di.appModule
import com.example.di.di.favoriteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(appModule,favoriteModule)
        }
    }
}