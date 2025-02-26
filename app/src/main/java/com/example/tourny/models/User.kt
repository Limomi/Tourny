package com.example.tourny.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("u_id")
    val id: String,
    @SerialName("u_lastname")
    val lastname: String,
    @SerialName("u_firstname")
    val firstname: String,
    @SerialName("u_patronymic")
    val patronymic: String?,
    @SerialName("u_password")
    val password: String,
)
