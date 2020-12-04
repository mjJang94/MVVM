package com.mj.mvvm.Module

import android.app.Application
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(connnectionModule())
        }
    }
}