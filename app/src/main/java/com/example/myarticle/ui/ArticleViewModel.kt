package com.example.myarticle.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myarticle.model.Article
import com.example.myarticle.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val repository: ArticleRepository): ViewModel() {

    val allArticles:StateFlow<List<Article>> = repository.getAllArticles().stateIn(viewModelScope, SharingStarted.Eagerly, listOf())

    fun addArticle(title:String, link:String){
        val article = Article(title = title, link = link, date = Date())
        insertArticle(article = article)
    }

    private fun insertArticle(article: Article){
        viewModelScope.launch {
            repository.insertArticle(article)
        }
    }
}