package com.example.tourny.network.api

import com.example.tourny.models.Tournament
import com.example.tourny.network.api.ApiClient.client
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiRepository {
    suspend fun getTournaments(): List<Tournament> = client.get(ApiRoutes.SEARCH_TOURNAMENT).body()?: emptyList<Tournament>()
//    suspend fun getTournaments(): List<Tournament> = client.get(ApiRoutes.SEARCH_TOURNAMENT).body()

//        val getTournaments: List<Tournament> = client.get(ApiRoutes.SEARCH_TOURNAMENT).body()
//    suspend fun getTournaments(): HttpResponse = client.request(ApiRoutes.SEARCH_TOURNAMENT) {
//        method = HttpMethod.Get
//    }
}