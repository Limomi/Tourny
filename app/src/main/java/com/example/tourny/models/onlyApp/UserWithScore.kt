package com.example.tourny.models.onlyApp



data class UserWithScore(
    val id: String,
    val lastname: String,
    val firstname: String,
    val patronymic: String?,
    val score: Int
)
