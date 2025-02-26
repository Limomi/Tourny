package com.example.tourny.ui.theme.screens.createLeague

import androidx.lifecycle.ViewModel
import com.example.tourny.models.League
import com.example.tourny.models.UserInLeague
import com.example.tourny.network.api.ApiRepository

class CreateLeagueViewModel: ViewModel() {
    suspend fun createLeague(nameLeague:String, basicScoreLeague:String, typeScore: String):String{
        val apiRepository = ApiRepository()
        val leagues: List<League> = apiRepository.getLeagues()

        apiRepository.putLeague(League(basicScoreLeague.toInt(), leagues.size, nameLeague, typeScore), leagues.size)

        val userInLeague:List<UserInLeague> = apiRepository.getUserInLeague()

        apiRepository.putUserInLeague(UserInLeague(leagues.size, basicScoreLeague.toInt(), "000000"), userInLeague.size)

        return leagues.size.toString()
    }
}