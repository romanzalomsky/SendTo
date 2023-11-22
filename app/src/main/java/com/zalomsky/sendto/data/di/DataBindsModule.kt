package com.zalomsky.sendto.data.di

import com.zalomsky.sendto.data.repository.AccountRepositoryImpl
import com.zalomsky.sendto.data.repository.AddressBookRepositoryImpl
import com.zalomsky.sendto.data.repository.ClientRepositoryImpl
import com.zalomsky.sendto.data.repository.StatisticsRepositoryImpl
import com.zalomsky.sendto.domain.repository.AccountRepository
import com.zalomsky.sendto.domain.repository.AddressBookRepository
import com.zalomsky.sendto.domain.repository.ClientRepository
import com.zalomsky.sendto.domain.repository.StatisticsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataBindsModule {

    @Binds
    fun provideAccountRepository(impl: AccountRepositoryImpl): AccountRepository

    @Binds
    fun addressBookRepository(impl: AddressBookRepositoryImpl): AddressBookRepository

    @Binds
    fun statisticsRepository(impl: StatisticsRepositoryImpl): StatisticsRepository

    @Binds
    fun clientRepository(impl: ClientRepositoryImpl): ClientRepository
}