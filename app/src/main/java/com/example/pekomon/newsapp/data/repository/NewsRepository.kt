package com.example.pekomon.newsapp.data.repository

import com.example.pekomon.newsapp.data.db.ArticleDatabase
import com.example.pekomon.newsapp.data.model.Article
import com.example.pekomon.newsapp.data.network.api.RetrofitInstance

class NewsRepository(
    val db: ArticleDatabase
) {

    suspend fun getBreakingNews(countryCode: String, page: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, page)

    suspend fun searchNews(query: String, page: Int) =
        RetrofitInstance.api.searchNews(query, page)

    suspend fun upsertArticle(article: Article) = db.articleDao().upsertArticle(article)

    fun getSavedNews() = db.articleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.articleDao().deleteArticle(article)
}