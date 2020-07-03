package com.example.pekomon.newsapp.data.model

import com.example.pekomon.newsapp.data.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)