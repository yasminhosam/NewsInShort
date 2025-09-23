package com.example.newsinshort.data.datasource

import com.example.newsinshort.data.entity.NewsResponse


interface NewsDataSource {
 suspend fun getNewsHeadline(country:String):retrofit2.Response<NewsResponse>
}
