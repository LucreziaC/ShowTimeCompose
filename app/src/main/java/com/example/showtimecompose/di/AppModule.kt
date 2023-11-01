package com.example.showtimecompose.di

import com.example.showtimecompose.network.ShowsRepositoryImpl
import com.example.showtimecompose.network.ShowsRepository
import com.example.showtimecompose.network.api.ApiHelper
import com.example.showtimecompose.network.api.ApiHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindsRemoteDataSource(remoteDataSource: ApiHelperImpl): ApiHelper

    @Binds
    @Singleton
    abstract fun bindsRepository(repository: ShowsRepositoryImpl) : ShowsRepository

}

