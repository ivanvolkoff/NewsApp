package com.example.newsapp.data.repository.datasource

import com.example.newsapp.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource{
    suspend fun getTopHeadLines(country:String,page:Int):Response<APIResponse>
    suspend fun getSearchedNewsHeadlines(country: String,searchedQuery: String,page: Int):Response<APIResponse>
}