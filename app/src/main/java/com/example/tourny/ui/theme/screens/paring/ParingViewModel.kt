package com.example.tourny.ui.theme.screens.paring

import androidx.lifecycle.ViewModel
import com.example.tourny.models.Paring
import com.example.tourny.models.User
import com.example.tourny.models.onlyApp.ParingWithName
import com.example.tourny.network.api.ApiRepository

class ParingViewModel: ViewModel() {

    suspend fun loadParing(paringId: String): ParingWithName{
        val apiRepository = ApiRepository()
        val paring: Paring = apiRepository.getSingleParing(paringId.toInt())
        val allUser: List<User> = apiRepository.getUsers()

        return ParingWithName(paring.id, paring.numberInTour, paring.tourId, paring.firstUserId, paring.secondUserId, allUser[paring.firstUserId.toInt()].lastname, allUser[paring.secondUserId.toInt()].lastname, allUser[paring.firstUserId.toInt()].firstname, allUser[paring.secondUserId.toInt()].firstname, allUser[paring.firstUserId.toInt()].patronymic, allUser[paring.secondUserId.toInt()].patronymic, paring.firstUserScore.toString(), paring.secondUserScore.toString(), paring.entered)
    }

    suspend fun uploadScore(paring: ParingWithName, firstUserScore: String, seoondUserScore:String){
        val apiRepository = ApiRepository()
        apiRepository.updateParing(Paring(paring.id, paring.numberInTour, paring.tourId, paring.firstUserId, paring.secondUserId, firstUserScore.toInt(), seoondUserScore.toInt(), true))
    }
}