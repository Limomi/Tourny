package com.example.tourny.ui.theme.screens.home

import androidx.lifecycle.ViewModel
import com.example.tourny.models.User
import com.example.tourny.network.api.ApiRepository

class UserHomeViewModel: ViewModel() {
    suspend fun GetUserInfo(loginUser: String): User{
        val userId: Int = loginUser.toInt()
        val apiRepository = ApiRepository()

        return (apiRepository.getSingleUser(userId))

    }

}