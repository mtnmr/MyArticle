package com.example.myarticle

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myarticle.model.Article
import com.example.myarticle.model.sampleData
import com.example.myarticle.ui.theme.MyArticleTheme
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MyArticleApp(){
    ArticleList(articleList = sampleData)
}

@Composable
fun ArticleList(
    articleList:List<Article>
){
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
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
                    text = SimpleDateFormat("MM/dd", Locale.JAPAN).format(article.date) ?: "",
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
            ArticleList(sampleData)
        }
    }
}