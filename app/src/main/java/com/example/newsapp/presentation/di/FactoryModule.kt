package com.example.newsapp.presentation.di

import android.app.Application
import com.example.newsapp.domain.usecases.GetNewsHeadlinesUseCase
import com.example.newsapp.domain.usecases.GetSearchedNewsUseCase
import com.example.newsapp.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class FactoryModule {
    @Singleton
    @Provides
    fun providesNewsViewModelFactory(
        application:Application,
        getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
        getSearchedNewsUseCase: GetSearchedNewsUseCase
    ):NewsViewModelFactory{
        return NewsViewModelFactory(application,getNewsHeadlinesUseCase,getSearchedNewsUseCase)
    }



}