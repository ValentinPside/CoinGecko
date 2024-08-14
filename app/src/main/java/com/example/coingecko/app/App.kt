package com.example.coingecko.app

import android.app.Application
import com.example.coingecko.di.AppComponent
import com.example.coingecko.di.DaggerAppComponent

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }
}