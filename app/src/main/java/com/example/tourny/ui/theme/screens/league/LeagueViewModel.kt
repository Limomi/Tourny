package com.example.tourny.ui.theme.screens.league

import androidx.lifecycle.ViewModel
import com.example.tourny.models.League
import com.example.tourny.models.Tournament
import com.example.tourny.models.TournamentInLeague
import com.example.tourny.models.User
import com.example.tourny.models.UserInLeague
import com.example.tourny.models.onlyApp.UserWithScore
import com.example.tourny.network.api.ApiRepository

class LeagueViewModel:ViewModel() {
    suspend fun getLeagueInfo(leagueId: String): League{
        val apiRepository = ApiRepository()
        val league: League = apiRepository.getSingleLeague(leagueId.toInt())
        return league
    }

    suspend fun getUserLeague(leagueId: String):List<UserWithScore>{
        val apiRepository = ApiRepository()
        var userInLeague: List<UserInLeague> = emptyList()
        var users: List<User> = emptyList()
        val leagueUser: MutableList<UserWithScore> = mutableListOf()

        userInLeague = apiRepository.getUserInLeague()
        users = apiRepository.getUsers()
        
        userInLeague.forEach{userInLeague ->
            if (userInLeague.leagueId == leagueId.toInt()){
                leagueUser.add(
                    UserWithScore(
                    users[userInLeague.userId.toInt()].id,
                    users[userInLeague.userId.toInt()].lastname,
                    users[userInLeague.userId.toInt()].firstname,
                    users[userInLeague.userId.toInt()].patronymic,
                    userInLeague.score
                    )
                )
            }
        }
        return leagueUser
    }

    suspend fun getTournamentLeague(leagueId: String): List<Tournament>{
        val apiRepository = ApiRepository()
        var tournamentInLeague:List<TournamentInLeague> = emptyList()
        var tournaments: List<Tournament> = emptyList()
        val leagueTournament: MutableList<Tournament> = mutableListOf()

        tournamentInLeague = apiRepository.getTournamentInLeague()
        tournaments = apiRepository.getTournaments()

        tournamentInLeague.forEach {tournamentInLeague->
            if (tournamentInLeague.leagueId == leagueId.toInt()){
                leagueTournament.add(tournaments[tournamentInLeague.tournamentId])
            }
        }

        return leagueTournament
    }
}