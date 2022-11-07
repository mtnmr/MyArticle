package com.example.myarticle.di

import com.example.myarticle.repository.ArticleRepository
import com.example.myarticle.repository.ArticleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsRepository(repository:ArticleRepositoryImpl):ArticleRepository
}