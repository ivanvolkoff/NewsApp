package com.example.newsapp.domain.usecases

import com.example.newsapp.data.model.APIResponse
import com.example.newsapp.data.util.Resource
import com.example.newsapp.domain.repository.NewsRepository

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(country:String,page:Int): Resource<APIResponse>{
        //here we can implement some business logic here
        return newsRepository.getNewsHeadLines(country,page)
    }

}