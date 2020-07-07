package com.example.pekomon.newsapp.data.model

import com.example.pekomon.newsapp.data.model.Article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)