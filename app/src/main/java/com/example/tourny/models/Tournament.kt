package com.example.tourny.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tournament (
    @SerialName("t_game")
    val game: String,
    @SerialName("t_id")
    val id: Int,
    @SerialName("t_name")
    val name: String
)