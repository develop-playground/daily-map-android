package com.dev.playground.domain.di

import com.dev.playground.domain.usecase.location.GetAddressUseCase
import com.dev.playground.domain.usecase.login.GetTokenUseCase
import com.dev.playground.domain.usecase.login.RemoveKakaoTokenUseCase
import com.dev.playground.domain.usecase.login.RequestLoginUseCase
import com.dev.playground.domain.usecase.login.SetTokenUseCase
import com.dev.playground.domain.usecase.memory.DeleteMemoryUseCase
import com.dev.playground.domain.usecase.memory.GetMemoryListUseCase
import com.dev.playground.domain.usecase.memory.PostMemoryUseCase
import com.dev.playground.domain.usecase.photo.DeletePhotoUseCase
import com.dev.playground.domain.usecase.photo.UploadPhotoUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetTokenUseCase(get(), get(named(IO))) }
    factory { SetTokenUseCase(get(), get(named(IO))) }

    factory { RemoveKakaoTokenUseCase(get(), get(named(IO))) }
    factory { RequestLoginUseCase(get(), get(named(IO))) }
    factory { GetUserUseCase(get()) }

    factory { GetMemoryListUseCase(get(), get(named(IO))) }
    factory { PostMemoryUseCase(get(), get(named(IO))) }
    factory { DeleteMemoryUseCase(get(), get(named(IO))) }

    factory { UploadPhotoUseCase(get(), get(named(IO))) }
    factory { DeletePhotoUseCase(get(), get(named(IO))) }

    factory { GetAddressUseCase(get(), get(named(IO))) }
}