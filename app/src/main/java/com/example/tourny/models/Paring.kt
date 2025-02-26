package com.example.tourny.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Paring(
    @SerialName("p_id")
    val id: Int,
    @SerialName("p_numberInTour")
    val numberInTour: Int,
    @SerialName("p_tourId")
    val tourId: Int,
    @SerialName("p_FirstUserId")
    val firstUserId: String,
    @SerialName("p_SecondUserId")
    val secondUserId: String,
    @SerialName("p_FirstUserScore")
    val firstUserScore: Int,
    @SerialName("p_SecondUserScore")
    val secondUserScore: Int,
    @SerialName("p_entered")
    val entered: Boolean
)
