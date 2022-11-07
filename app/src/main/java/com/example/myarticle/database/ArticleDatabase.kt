package com.example.myarticle.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myarticle.model.Article
import com.example.myarticle.model.DateConverter

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class ArticleDatabase:RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}