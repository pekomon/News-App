package com.example.pekomon.newsapp.data.repository

import com.example.pekomon.newsapp.data.db.ArticleDatabase
import com.example.pekomon.newsapp.data.network.api.RetrofitInstance

class NewsRepository(
    val db: ArticleDatabase
) {

    suspend fun getBreakingNews(countryCode: String, page: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, page)
}