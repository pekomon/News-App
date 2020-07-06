package com.example.pekomon.newsapp.data.network.api

import com.example.pekomon.newsapp.BuildConfig
import com.example.pekomon.newsapp.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apikey")
        apikey: String = BuildConfig.APIKEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q")
        query: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apikey")
        apikey: String = BuildConfig.APIKEY
    ): Response<NewsResponse>
}
