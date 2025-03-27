package com.example.recipesapp

import android.app.Application
import com.example.core.di.appModule
import com.example.core.di.favoriteModule
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