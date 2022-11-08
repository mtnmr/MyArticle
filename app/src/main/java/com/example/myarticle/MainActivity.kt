package com.example.myarticle

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.myarticle.ui.ArticleViewModel
import com.example.myarticle.ui.MyArticleApp
import com.example.myarticle.ui.theme.MyArticleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callbackOnLinkClicked : (String) -> Unit = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            try{
                startActivity(intent)
            }catch(e:Exception){
                Toast.makeText(this, "アクセスできません", Toast.LENGTH_SHORT).show()
                Log.d("onClickItem", "intent uri failure: $e")
            }
        }

        setContent {
            MyArticleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyArticleApp(
                        viewModel,
                        onClickItem = callbackOnLinkClicked
                    )
                }
            }
        }
    }
}