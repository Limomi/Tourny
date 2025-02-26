package com.example.tourny.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class League (
    @SerialName("l_basicScore")
    val basicScore:Int,
    @SerialName("l_id")
    val leagueId:Int,
    @SerialName("l_name")
    val name: String,
    @SerialName("l_typeScore")
    val typeScore: String
)
