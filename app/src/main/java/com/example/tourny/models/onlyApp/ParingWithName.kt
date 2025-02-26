package com.example.tourny.models.onlyApp

data class ParingWithName(
    val id: Int,
    val numberInTour:Int,
    val tourId: Int,
    val firstUserId: String,
    val secondUserId: String,
    val firstUserLastname: String,
    val secondUserLastname:String,
    val firstUserFirstname: String,
    val secondUserFirstname: String,
    val firstUserPatronymic: String?,
    val secondUserPatronymic: String?,
    var firstUserScore: String,
    var secondUserScore: String,
    val entered: Boolean
)
