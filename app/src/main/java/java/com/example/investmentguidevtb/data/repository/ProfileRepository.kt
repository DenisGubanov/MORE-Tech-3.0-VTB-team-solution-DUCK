package com.example.investmentguidevtb.data.repository

import com.example.investmentguidevtb.data.source.api.ChatApi
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val chatApi: ChatApi
) {

    suspend fun getQuestions() = chatApi.getWeather()
}