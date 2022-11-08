package com.example.myarticle.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myarticle.model.Article
import com.example.myarticle.model.sampleData
import com.example.myarticle.ui.theme.MyArticleTheme
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MyArticleApp(
    viewModel: ArticleViewModel = viewModel()
){
    val articleList by viewModel.allArticles.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val article = Article(title = "sample", link = "https://sample.link", date = Date())
                    viewModel.insertArticle(article)
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "add article button")
            }
        }
    ){ innerPadding ->
        ArticleList(
            articleList = articleList,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ArticleList(
    articleList:List<Article>,
    modifier: Modifier = Modifier
){
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ){
        items(articleList){ article ->
            ArticleListItem(article = article)
        }
    }
}

@Composable
fun ArticleListItem(
    article: Article
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp,
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.h3
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = article.link,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = article.date?.let { SimpleDateFormat("MM/dd", Locale.JAPAN).format(it) }
                        ?: "",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

    }
}

@Preview
@Composable
fun ArticleListItemPreview(){
    MyArticleTheme() {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.background
        ) {
            ArticleListItem(article = sampleData[0])
        }
    }
}

@Preview
@Composable
fun ArticleListPreview(){
    MyArticleTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            ArticleList(articleList = sampleData)
        }
    }
}