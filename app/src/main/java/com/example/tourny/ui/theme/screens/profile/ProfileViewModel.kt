package com.example.tourny.ui.theme.screens.profile

import androidx.lifecycle.ViewModel
import com.example.tourny.models.League
import com.example.tourny.models.Tournament
import com.example.tourny.models.User
import com.example.tourny.models.UserInLeague
import com.example.tourny.models.UserInTournament
import com.example.tourny.models.onlyApp.LeagueWithScore
import com.example.tourny.network.api.ApiRepository

class ProfileViewModel: ViewModel() {
    suspend fun GetUserInfo(UserId: String?): User{
        val userId: Int = UserId!!.toInt()
        val apiRepository = ApiRepository()

        return (apiRepository.getSingleUser(userId))

    }

    suspend fun GetUserLeagues(UserId: String?): List<LeagueWithScore>{
        val apiRepository = ApiRepository()
        var userInLeague: List<UserInLeague> = emptyList()
        var leagues: List<League> = emptyList()
        val userScoreLeague: MutableList<LeagueWithScore> = mutableListOf()

        userInLeague = apiRepository.getUserInLeague()
        leagues = apiRepository.getLeagues()

        userInLeague.forEach{userLeag ->
            if (userLeag.userId == UserId){
                userScoreLeague.add(LeagueWithScore(userLeag.score, leagues[userLeag.leagueId].leagueId, leagues[userLeag.leagueId].name))
            }
        }

        return userScoreLeague
    }

    suspend fun GetTournament(UserId: String?): List<Tournament>{
        val apiRepository = ApiRepository()
        var userInTournament:List<UserInTournament> = emptyList()
        var tournament:List<Tournament> = emptyList()
        val userTournament: MutableList<Tournament> = mutableListOf()

        userInTournament = apiRepository.getUserInTournament()
        tournament = apiRepository.getTournaments()

        userInTournament.forEach { userInTournament ->
            if(userInTournament.userId == UserId){
                userTournament.add(tournament[userInTournament.tournamentId])
            }
        }

        return userTournament
    }
}