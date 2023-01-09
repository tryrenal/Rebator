package com.redveloper.rebator.di.module

import com.redveloper.rebator.data.network.auth.AuthFirebase
import com.redveloper.rebator.data.network.auth.AuthFirebaseImpl
import com.redveloper.rebator.data.network.user.UserFirebase
import com.redveloper.rebator.data.network.user.UserFirebaseImpl
import org.koin.dsl.module

val networkModule = module {
    single<AuthFirebase> { AuthFirebaseImpl(get(), get(), get()) }
    single<UserFirebase> { UserFirebaseImpl(get(), get()) }
}