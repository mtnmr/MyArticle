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

    fun addArticle(title:String, link:String, id:Int){
        if(id == -1){
            val article = Article(title = title, link = link, date = Date())
            insertArticle(article = article)
        }else{
            val article = Article(id = id, title = title, link = link, date = Date())
            updateArticle(article = article)
        }

    }

    private fun insertArticle(article: Article){
        viewModelScope.launch {
            repository.insertArticle(article)
        }
    }

    fun deleteArticle(article: Article){
        viewModelScope.launch {
            repository.deleteArticle(article)
        }
    }

    private fun updateArticle(article: Article){
        viewModelScope.launch {
            repository.updateArticle(article)
        }
    }
}