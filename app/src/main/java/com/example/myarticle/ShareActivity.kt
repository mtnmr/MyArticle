package com.example.myarticle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myarticle.ui.ArticleViewModel
import com.example.myarticle.ui.theme.MyArticleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareActivity : AppCompatActivity() {

    private val viewModel: ArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = when(intent.action){
            Intent.ACTION_SEND -> {
                intent.getStringExtra(Intent.EXTRA_TEXT)
            }
            else -> ""
        }

        val title = when(intent.action){
            Intent.ACTION_SEND -> {
                intent.getStringExtra(Intent.EXTRA_SUBJECT)
            }
            else -> ""
        }

        if(url != "") {
            viewModel.addArticle(title.toString(), url as String, -1)
        }

        setContent {
            MyArticleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ShareScreen()
                }
            }

        }
    }
}

@Composable
fun ShareScreen(){
    Text(text = "Share finish")
}