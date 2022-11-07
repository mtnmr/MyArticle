package com.example.myarticle.repository

import com.example.myarticle.database.ArticleDao
import com.example.myarticle.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articleDao: ArticleDao
): ArticleRepository {
    override fun getAllArticles(): Flow<List<Article>> {
        return articleDao.getAllArticle()
    }

    override suspend fun insertArticle(article:Article) {
        articleDao.insertArticle(article)
    }

    override suspend fun updateArticle(article:Article) {
        articleDao.updateArticle(article)
    }

    override suspend fun deleteArticle(article:Article) {
        articleDao.deleteArticle(article)
    }
}