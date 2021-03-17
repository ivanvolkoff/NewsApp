package com.example.newsapp.domain.usecases

import com.example.newsapp.data.model.Article
import com.example.newsapp.domain.repository.NewsRepository

class SaveNewsUseCase (private val newsRepository: NewsRepository){

    suspend fun execute(article: Article) = newsRepository.saveNews(article)

}