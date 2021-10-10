package com.example.investmentguidevtb.ui.practice.models

import java.io.Serializable

data class GameEndFeedback(
    val goalResultPicture: String?,
    val goalResultDescription: String,
    val riskResultPicture: String?,
    val riskResultDescription: String
): Serializable {
}