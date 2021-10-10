package com.example.investmentguidevtb.data.source.api

import com.example.investmentguidevtb.ui.practice.models.GameArticle
import retrofit2.Response
import retrofit2.http.GET

interface ArticleApi {
    @GET(".")
    suspend fun downloadAllArticles(): Response<List<GameArticle>>
}