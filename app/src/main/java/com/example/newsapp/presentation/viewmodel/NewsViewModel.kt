package com.example.newsapp.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.APIResponse
import com.example.newsapp.data.util.Resource
import com.example.newsapp.domain.usecases.GetNewsHeadlinesUseCase
import com.example.newsapp.domain.usecases.GetSearchedNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class NewsViewModel(
    private val app:Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase
) : AndroidViewModel(app) {
    val newsHeadLines: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getNewsHeadLines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        newsHeadLines.postValue(Resource.Loading())
        try{
            if(isNetworkAvailable(app)) {

                val apiResult = getNewsHeadlinesUseCase.execute(country, page)
                newsHeadLines.postValue(apiResult)
            }else{
                newsHeadLines.postValue(Resource.Error("Internet is not available"))
            }

        }catch (e:Exception){
            newsHeadLines.postValue(Resource.Error(e.message.toString()))
        }

    }



    private fun isNetworkAvailable(context: Context?):Boolean{
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false

    }

    //search
    val searchedList: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun searchedNews(country: String,searchQuery:String,page: Int) = viewModelScope.launch{

        searchedList.postValue(Resource.Loading())
        try{
            if(isNetworkAvailable(app)){
                val response = getSearchedNewsUseCase.execute(
                    country,
                    searchQuery,
                    page
                )

                searchedList.postValue(response)
            }else{
                searchedList.postValue(Resource.Error("No internet connection"))
            }
        }catch (e:Exception){
            searchedList.postValue(Resource.Error(e.message.toString()))
        }

    }

































}