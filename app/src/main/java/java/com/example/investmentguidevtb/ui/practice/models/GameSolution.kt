package com.example.investmentguidevtb.ui.practice.models

data class GameSolution (
    val text: String = "",
    val feedback: String? = "",
    val article: GameArticle?,
    val delta: GameDelta
    )