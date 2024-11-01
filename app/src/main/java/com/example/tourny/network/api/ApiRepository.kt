package com.example.tourny.network.api

import com.example.tourny.models.Tournament
import com.example.tourny.network.api.ApiClient.httpClientAndroid
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiRepository {
    suspend fun getTournaments(): List<Tournament> = httpClientAndroid.get(ApiRoutes.SEARCH_TOURNAMENT).body()
}