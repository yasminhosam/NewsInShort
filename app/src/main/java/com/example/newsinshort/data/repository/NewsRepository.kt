package com.example.newsinshort.data.repository

import android.util.Log
import com.example.newsinshort.data.datasource.NewsDataSource
import com.example.newsinshort.data.entity.NewsResponse
import com.example.newsinshort.data.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsDataSource: NewsDataSource
) {

//    suspend fun getNewsHeadline(country:String):Response<NewsResponse>{
//        return newsDataSource.getNewsHeadline(country)
//
//    }

     fun getNewsHeadline(country:String):Flow<ResourceState<NewsResponse>>{
        return flow{
            emit(ResourceState.Loading())
            val  response=newsDataSource.getNewsHeadline(country)
            if(response.isSuccessful && response.body() !=null){
                emit(ResourceState.Success(response.body()!!))
            }else{
                val errorMsg = response.errorBody()?.string()
                Log.e("NewsRepository", "API Error: $errorMsg") // Log the specific error
                emit(ResourceState.Error(errorMsg ?: "Something went wrong"))
            }
        }.catch { e->
            Log.e("NewsRepository", "Error fetching news: ${e.message}")
            emit(ResourceState.Error(e?.localizedMessage ?:"Some error in flow"))
        }
    }
}