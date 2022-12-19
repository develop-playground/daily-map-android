package com.dev.playground.data.di

import com.dev.playground.data.repository.*
import com.dev.playground.domain.repository.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single<SharedPreferencesRepository> { SharedPreferencesRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<MemoryRepository> { MemoryRepositoryImpl(get()) }
    single<PhotoRepository> { PhotoRepositoryImpl(get()) }
    single<LocationRepository> {
        LocationRepositoryImpl(
            service = get(),
            apiKeyId = get(named(X_NCP_APIGW_API_KEY_ID)),
            apiKey = get(named(X_NCP_APIGW_API_KEY))
        )
    }
}