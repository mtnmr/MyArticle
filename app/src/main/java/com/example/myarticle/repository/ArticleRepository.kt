package com.example.myarticle.repository

import com.example.myarticle.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getAllArticles():Flow<List<Article>>

    suspend fun insertArticle(article:Article)

    suspend fun updateArticle(article:Article)

    suspend fun deleteArticle(article:Article)
}