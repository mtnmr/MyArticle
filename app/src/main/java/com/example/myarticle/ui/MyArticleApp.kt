package com.example.myarticle.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myarticle.R
import com.example.myarticle.model.Article
import com.example.myarticle.model.sampleData
import com.example.myarticle.ui.theme.MyArticleTheme
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MyArticleApp(
    viewModel: ArticleViewModel = viewModel(),
    onClickItem: (String) -> Unit,
){
    val articleList by viewModel.allArticles.collectAsState()
    var openDialog by remember { mutableStateOf(false) }
    var currentArticle by remember { mutableStateOf(Article(id = -1)) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    currentArticle = Article(id = -1)
                    openDialog = true
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "add article button")
            }
        }
    ){ innerPadding ->
        ArticleList(
            articleList = articleList,
            onClickItem = onClickItem,
            modifier = Modifier.padding(innerPadding),
            onClickDelete = {article -> viewModel.deleteArticle(article) },
            onClickEdit = { article ->
                currentArticle = article
                openDialog = true
            }
        )

        if(openDialog){
            SaveDialog(
                article = currentArticle,
                onSaveButtonClicked = { title, link, id ->
                    viewModel.addArticle(title, link, id)
                    openDialog = false
                },
                onCancelButtonClicked = { openDialog = false }
            )
        }
    }
}

@Composable
fun ArticleList(
    articleList:List<Article>,
    onClickItem: (String) -> Unit,
    onClickDelete:(Article) -> Unit,
    onClickEdit:(Article) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ){
        items(
            articleList,
            key =  {item: Article ->  item.id}
        ){ article ->
            ArticleListItem(
                article = article,
                onClickItem = onClickItem,
                onClickDelete = onClickDelete,
                onClickEdit = onClickEdit
            )
        }
    }
}

@Composable
fun ArticleListItem(
    article: Article,
    onClickItem:(String) -> Unit,
    onClickDelete:(Article) -> Unit,
    onClickEdit:(Article) -> Unit
){
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { showMenu = true },
                    onTap = { onClickItem(article.link) }
                )
            }
            .fillMaxWidth(),
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

    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { showMenu = false }
    ) {
        DropdownMenuItem(
            onClick = { onClickDelete(article) }
        ) {
           Text(text = stringResource(id = R.string.delete_article))
        }

        DropdownMenuItem(
            onClick = { onClickEdit(article)}
        ) {
            Text(text = stringResource(id = R.string.edit_article))
        }
    }
}

@Composable
fun SaveAlertDialog(
    onSaveButtonClicked:() -> Unit,
    onCancelButtonClicked:() -> Unit
){
    AlertDialog(
        onDismissRequest = {  },
        title = {
            Text(text = stringResource(id = R.string.save_dialog_title))
        },
        text ={
            TextField(
                value = "",
                onValueChange = {},
                label = { Text(text = "title") }
            )
        },
        confirmButton = {
            TextButton(
                onClick = onSaveButtonClicked
            ) {
                Text(text = stringResource(id = R.string.save_article))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancelButtonClicked
            ) {
                Text(text = stringResource(id = R.string.save_article_cancel))
            }
        }
    )
}

@Composable
fun SaveDialog(
    article: Article,
    onSaveButtonClicked:(String, String, Int) -> Unit,
    onCancelButtonClicked:() -> Unit
){
    var title by remember { mutableStateOf(article.title) }
    var link by remember { mutableStateOf(article.link) }

    Dialog(onDismissRequest = {  }) {
        Surface() {
            Column(
                modifier = Modifier.padding(10.dp)
            ){
                Text(
                    text = stringResource(id = R.string.save_dialog_title),
                )

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(text = stringResource(id = R.string.save_article_title))},
                    singleLine = true,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
                )

                OutlinedTextField(
                    value = link,
                    onValueChange = { link = it },
                    label = { Text(text = stringResource(id = R.string.save_article_link))},
                    singleLine = true,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onCancelButtonClicked,
                    ) {
                        Text(text = stringResource(id = R.string.save_article_cancel))
                    }

                    TextButton(
                        onClick = { onSaveButtonClicked(title, link, article.id) },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(text = stringResource(id = R.string.save_article))
                    }
                }
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
            ArticleListItem(article = sampleData[0], onClickItem = {}, onClickDelete = {}, onClickEdit = {})
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
            ArticleList(articleList = sampleData, onClickItem = {}, onClickDelete = {}, onClickEdit = {})
        }
    }
}

@Preview
@Composable
fun SaveDialogPreview(){
    MyArticleTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            SaveDialog(
                article = Article(id = -1),
                onSaveButtonClicked = {_,_,_ -> },
                onCancelButtonClicked = {},
            )
        }
    }
}