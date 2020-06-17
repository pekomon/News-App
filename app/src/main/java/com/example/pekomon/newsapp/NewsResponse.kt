package com.example.pekomon.newsapp

import com.example.pekomon.newsapp.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)