package com.example.newsapp.presentation.di

import com.example.newsapp.data.api.NewsAPIService
import com.example.newsapp.data.repository.datasource.NewsRemoteDataSource
import com.example.newsapp.data.repository.datasourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(newsAPIService: NewsAPIService):NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(newsAPIService)
    }


}