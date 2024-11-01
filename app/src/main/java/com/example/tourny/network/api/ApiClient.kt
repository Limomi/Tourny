package com.example.tourny.network.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json

object ApiClient {

    val httpClientAndroid = HttpClient(Android){
        install(ContentNegotiation){
            Json {
                prettyPrint = true
                isLenient = true
//                useAlternativeNames = true
                ignoreUnknownKeys = true
                encodeDefaults = false
            }
        }

        install(HttpTimeout){
            requestTimeoutMillis = 15000L
            connectTimeoutMillis = 15000L
            socketTimeoutMillis = 15000L
        }

        install(Logging){
            level =LogLevel.ALL
        }

        install(DefaultRequest){
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}