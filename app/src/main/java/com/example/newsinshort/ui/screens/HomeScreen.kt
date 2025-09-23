package com.example.newsinshort.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsinshort.data.util.ResourceState
import com.example.newsinshort.ui.components.Loader
import com.example.newsinshort.ui.components.NewsList
import com.example.newsinshort.ui.viewModel.NewsViewModel

const val TAG="HomeScreen"
@Composable
fun HomeScreen(
    newsViewModel:NewsViewModel= hiltViewModel()
){
    // state object wraps the data (State<T> )
    val newsResponseState=newsViewModel.news.collectAsState()
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        when(val newsResponse=newsResponseState.value){
            is ResourceState.Loading<*> ->{
                Log.d(TAG,"Inside Loading")
                Loader()
            }
            is ResourceState.Success<*> ->{
                val response=(newsResponse as ResourceState.Success).data
                Log.d(TAG,"Inside Success  ${response.status}= ${response.totalResults}")
                NewsList(response)
            }
            is ResourceState.Error<*> ->{
                val error =(newsResponse as ResourceState.Error)
                Log.d(TAG,"Inside Error ${error}")
            }
        }
    }
}
@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}