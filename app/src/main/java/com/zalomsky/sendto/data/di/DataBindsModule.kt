package com.zalomsky.sendto.data.di

import com.zalomsky.sendto.data.repository.AccountRepositoryImpl
import com.zalomsky.sendto.domain.repository.AccountRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataBindsModule {

    @Binds
    fun provideAccountRepository(impl: AccountRepositoryImpl): AccountRepository
}