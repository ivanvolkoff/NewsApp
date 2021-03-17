package com.example.newsapp.data.repository.datasourceImpl

import com.example.newsapp.data.api.NewsAPIService
import com.example.newsapp.data.model.APIResponse
import com.example.newsapp.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(private val newsAPIService: NewsAPIService):NewsRemoteDataSource {

    override suspend fun getTopHeadLines(country:String,page:Int): Response<APIResponse> {

        return newsAPIService.getTopHeadLines(country,page)

    }
}