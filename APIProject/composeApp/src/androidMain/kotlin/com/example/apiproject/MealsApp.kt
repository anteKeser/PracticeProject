package com.example.apiproject

import android.app.Application
import com.example.apiproject.di.initKoin
import org.koin.android.ext.koin.androidContext

class MealsApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MealsApp)
        }
    }
}
