package com.example.newsinshort.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newsinshort.R
import com.example.newsinshort.data.entity.Article
import com.example.newsinshort.data.entity.NewsResponse
import com.example.newsinshort.ui.theme.Purple40

@Composable
fun Loader() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        CircularProgressIndicator(
            modifier = Modifier
                .size(60.dp)
                .padding(10.dp),
            color = Purple40
        )
    }
}

@Composable
fun NewsList(response: NewsResponse) {
    LazyColumn(
        modifier = Modifier
    ) {
        items(response.articles) { article ->
            NormalTextComponent(textValue = article.title ?: "NA")

        }
    }
}

@Composable
fun NormalTextComponent(textValue: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        text = textValue,
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal
        )
    )
}

@Composable
fun HeadingTextComponent(textValue: String,centerAlignment: Boolean=false) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        text = textValue,
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.Monospace,
            color = Purple40
        ),
        textAlign = if(centerAlignment) TextAlign.Center else TextAlign.Start
    )
}

@Composable
fun NewsRowComponent(page: Int, article: Article) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(Color.White),

        ) {
        //if we are in PREVIEW mode, show a simple placeholder
        if (LocalInspectionMode.current) {
            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = "Placeholder Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.Crop // Good for making the image fill the space
            )

        } else {

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                model = article.urlToImage,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.image),
                error = painterResource(id = R.drawable.image)

            )
            Spacer(modifier = Modifier.size(20.dp))
            HeadingTextComponent(textValue = article.title ?: "")
            Spacer(modifier = Modifier.size(10.dp))
            NormalTextComponent(textValue = article.description ?: "")
            Spacer(modifier = Modifier.weight(1f))
            AuthorDetailComponent(article.author, article.source?.name)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun NewsRowComponentPreview() {
    val article = Article(
        author = "Mss Y",
        title = "Hello Dummy news article",
        content = null,
        description = null,
        publishedAt = null,
        source = null,
        url = null,
        urlToImage = null,
    )
    NewsRowComponent(1, article)
}

@Composable
fun AuthorDetailComponent(authorName: String?, sourceName: String?) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp )
    ) {
        authorName?.also {
            Text(it)
        }
        Spacer(modifier = Modifier.weight(1f))
        sourceName?.also {

            Text(it)
        }
    }
}

@Composable
fun EmptyStateComponent(){
    Column(
       modifier = Modifier
           .fillMaxSize()
           .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id=R.drawable.newspaper), contentDescription =null)
        Spacer(modifier = Modifier.size(10.dp))
        HeadingTextComponent("No news now \n" +
                "Please check in later", centerAlignment = true)
    }
}
@Composable
@Preview
fun EmptyStateComponentPreview(){
    EmptyStateComponent()
}