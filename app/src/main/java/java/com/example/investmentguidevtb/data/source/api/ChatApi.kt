package com.example.investmentguidevtb.data.source.api

import com.example.investmentguidevtb.ui.profile.models.ChatQuestionsResponse
import retrofit2.http.GET

interface ChatApi {
    @GET("questions")
    suspend fun getWeather(): ChatQuestionsResponse
}