package com.example.investmentguidevtb.ui.practice.models

import java.io.Serializable

data class GameArticle(
    val author: String = "",
    val header: String = "Заголовок не найден",
    val body: String = "Содержание статьи не найдено",
    val image_url: String = ""
): Serializable
