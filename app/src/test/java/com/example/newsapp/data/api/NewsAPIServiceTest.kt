package com.example.newsapp.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset

class NewsAPIServiceTest {

    private lateinit var service: NewsAPIService
    private lateinit var server: MockWebServer


    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPIService::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }


    private fun enqueMockResponse(fileName: String) {

        val inputStrem = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStrem.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)

    }

    @Test
    fun getTopHeadLines_sentReequest_recievedExpected() {
        runBlocking {
            enqueMockResponse("newsresponce.json")
            val responceBody = service.getTopHeadLines("us", 1).body()
            val request = server.takeRequest()
            assertThat(responceBody).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=75ed444caa1f433ebafc5c957d515afa")

        }
    }

    @Test
    fun getTopHeadlines_recievedResponce_correctPageSize() {
        runBlocking {
            enqueMockResponse("newsresponce.json")
            val responceBody = service.getTopHeadLines("us", 1).body()
            val articlesList = responceBody!!.articles
            assertThat(articlesList?.size).isEqualTo(20)

        }
    }

    @Test
    fun getTopHeadlines_recievedResponce_correctContent() {
        runBlocking {
            enqueMockResponse("newsresponce.json")
            val responceBody = service.getTopHeadLines("us", 1).body()
            val articlesList = responceBody!!.articles!!
            val article = articlesList[1]
            assertThat(article.author).isEqualTo( "Morgan McFall-Johnsen")
            assertThat(article.title).isEqualTo( "Watch NASA test-fire its new moon rocket's core stage on Thursday - Business Insider")

        }
    }



}