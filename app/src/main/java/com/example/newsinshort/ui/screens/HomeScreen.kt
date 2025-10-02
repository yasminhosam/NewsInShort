package com.example.newsinshort.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsinshort.data.util.ResourceState
import com.example.newsinshort.ui.components.EmptyStateComponent
import com.example.newsinshort.ui.components.Loader
import com.example.newsinshort.ui.components.NewsList
import com.example.newsinshort.ui.components.NewsRowComponent
import com.example.newsinshort.ui.viewModel.NewsViewModel

const val TAG = "HomeScreen"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    newsViewModel: NewsViewModel = hiltViewModel()
) {
    // state object wraps the data (State<T> )
    val newsResponseState = newsViewModel.news.collectAsState()

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) { 100 }
    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        pageSize = PageSize.Fill,
        pageSpacing = 8.dp
    ) { page: Int ->

        when (val newsResponse = newsResponseState.value) {
            is ResourceState.Loading<*> -> {
                Log.d(TAG, "Inside Loading")
                Loader()
            }

            is ResourceState.Success<*> -> {
                val response = (newsResponse as ResourceState.Success).data
                Log.d(TAG, "Inside Success  ${response.status}= ${response.totalResults}")
                if(response.articles.isNotEmpty()){

                NewsRowComponent(page,response.articles.get(page))
                }
                else{
                    EmptyStateComponent()
                }
            }

            is ResourceState.Error<*> -> {
                val error = (newsResponse as ResourceState.Error)
                Log.d(TAG, "Inside Error ${error}")
            }
        }

    }

}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}