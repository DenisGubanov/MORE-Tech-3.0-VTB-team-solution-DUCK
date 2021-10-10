package com.example.investmentguidevtb.data.source.api

import com.example.investmentguidevtb.ui.practice.models.GameSituation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PracticeApi {
    @GET(".")
    suspend fun getSolution(
        @Query("difficulty")
        difficulty: String,
        @Query("vtb")
        vtb: String,
        @Query("capitalDiff")
        capitalDiff: String,
        @Query("risk")
        risk: String
    ): Response<GameSituation>
}