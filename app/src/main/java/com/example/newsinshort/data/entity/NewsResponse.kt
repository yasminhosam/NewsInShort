package com.example.newsinshort.data.entity

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)