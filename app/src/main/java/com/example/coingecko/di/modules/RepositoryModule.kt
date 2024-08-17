package com.example.coingecko.di.modules

import com.example.coingecko.data.network.RepositoryImpl
import com.example.coingecko.domain.Repository
import dagger.Module
import dagger.Provides

@Module
object RepositoryModule {

    @Provides
    fun provideRepository(impl: RepositoryImpl): Repository = impl

}