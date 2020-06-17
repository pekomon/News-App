package com.example.pekomon.newsapp.api

import com.example.pekomon.newsapp.NewsResponse
import com.example.pekomon.newsapp.util.Consts.APIKEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "fi",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apikey")
        apikey: String = APIKEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q")
        query: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apikey")
        apikey: String = APIKEY
    ): Response<NewsResponse>
}
