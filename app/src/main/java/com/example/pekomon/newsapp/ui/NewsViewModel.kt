package com.example.pekomon.newsapp.ui

import androidx.lifecycle.ViewModel
import com.example.pekomon.newsapp.data.repository.NewsRepository

class NewsViewModel(
    val newsRepository: NewsRepository
): ViewModel(){

}