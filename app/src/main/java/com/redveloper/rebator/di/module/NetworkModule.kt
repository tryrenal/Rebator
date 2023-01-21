package com.redveloper.rebator.di.module

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.redveloper.rebator.data.network.address.AddressApiService
import com.redveloper.rebator.data.network.address.AddressApiServiceImpl
import com.redveloper.rebator.data.network.auth.AuthFirebase
import com.redveloper.rebator.data.network.auth.AuthFirebaseImpl
import com.redveloper.rebator.data.network.seller.AppSellerFirebase
import com.redveloper.rebator.data.network.seller.AppSellerFirebaseImpl
import com.redveloper.rebator.data.network.user.UserFirebase
import com.redveloper.rebator.data.network.user.UserFirebaseImpl
import com.redveloper.rebator.domain.repository.api.AddressApi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single<AuthFirebase> { AuthFirebaseImpl(get(), get(), get()) }
    single<UserFirebase> { UserFirebaseImpl(get(), get()) }
    single<AddressApi> { AddressApiServiceImpl(get()) }
    single<AppSellerFirebase> { AppSellerFirebaseImpl(get()) }
}

fun retrofitModule(baseUrl: String) = module{

    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    factory { get<Retrofit>().create(AddressApiService::class.java) }
}