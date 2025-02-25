package com.example.tourny.models.onlyApp

import com.example.tourny.models.Paring

data class Tour (
    val id: Int,
    val tourCount: String,
    val closed: Boolean,
    val parings: MutableList<ParingWithName>
)