package com.example.myarticle.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myarticle.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article")
    fun getAllArticle():Flow<List<Article>>

    @Query("SELECT * FROM article WHERE title LIKE '%' || :text || '%'")
    fun searchArticles(text:String):Flow<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateArticle(article: Article)

    @Delete
    suspend fun deleteArticle(article: Article)
}