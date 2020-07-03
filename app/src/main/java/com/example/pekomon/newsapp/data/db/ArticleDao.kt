package com.example.pekomon.newsapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pekomon.newsapp.data.model.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertArticle(article: Article): Long

    @Query("select * from article")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}