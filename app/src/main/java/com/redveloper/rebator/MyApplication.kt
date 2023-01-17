package com.redveloper.rebator

import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.redveloper.rebator.di.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                appModule,
                firebaseModule,
                repositoryModule,
                viewModelModule,
                networkModule,
                appUseCaseModule,
                preferenceModule,
                retrofitModule("https://dev.farizdotid.com/api/daerahindonesia/")
            )
        }
    }
}