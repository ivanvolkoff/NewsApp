package com.example.newsapp.data.repository

import com.example.newsapp.data.model.APIResponse
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.repository.datasource.NewsRemoteDataSource
import com.example.newsapp.data.util.Resource
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(private val newsRemoteDataSource: NewsRemoteDataSource):NewsRepository {

    override suspend fun getNewsHeadLines(country:String,page:Int): Resource<APIResponse> {
       return  responceToResult(newsRemoteDataSource.getTopHeadLines(country,page))
    }

    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Resource<APIResponse> {
        return responceToResult(
            newsRemoteDataSource.getSearchedNewsHeadlines(country,searchQuery,page)
        )
    }

    //converting responce to resource
    private fun responceToResult(responce: Response<APIResponse>):Resource<APIResponse>{
        if(responce.isSuccessful){
            //if successfull we return the body, which is type Resource
            responce.body()?.let {
                result ->
                return Resource.Success(result)
            }
        }
        //else we return error message
        return Resource.Error(responce.message())
    }

    override suspend fun saveNews(article: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNews(article: Article) {
        TODO("Not yet implemented")
    }

    override fun getSavedNews(): Flow<List<Article>> {
        TODO("Not yet implemented")
    }
}