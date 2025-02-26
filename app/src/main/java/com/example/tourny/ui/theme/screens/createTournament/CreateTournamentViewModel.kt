package com.example.tourny.ui.theme.screens.createTournament

import androidx.lifecycle.ViewModel
import com.example.tourny.models.League
import com.example.tourny.models.Tournament
import com.example.tourny.models.TournamentInLeague
import com.example.tourny.models.onlyApp.LeagueForCreateTournament
import com.example.tourny.network.RegistretedUser
import com.example.tourny.network.api.ApiRepository

class CreateTournamentViewModel:ViewModel() {

//    suspend fun GetLeagues(): List<LeagueForCreateTournament>{
//        val apiRepository = ApiRepository()
//        var leagues:List<League> = emptyList()
//        var leagueForCreateTournament:MutableList<LeagueForCreateTournament> = mutableListOf()
//        leagues = apiRepository.getLeagues()
//
//        leagues.forEach {league->
//            leagueForCreateTournament.add(LeagueForCreateTournament(league.leagueId, league.name, league.typeScore,false,false))
//        }
//
//        return leagueForCreateTournament
//    }

    suspend fun GetLeagues():List<League>{
        val apiRepository = ApiRepository()
        var leagues:List<League> = apiRepository.getLeagues()
        return leagues
    }
    suspend fun CreateTournament(tournamentName: String, tournamentTourCount: Int, tournamentTypeParings: String , leagues: List<LeagueForCreateTournament>, sortLeagueId: Int): Int{
        val apiRepository = ApiRepository()

        val tournaments: List<Tournament> = apiRepository.getTournaments()
        val creatyTournament: Tournament = Tournament(RegistretedUser.id, tournaments.size, tournamentName, tournamentName[0] + "w" + "3" + tournamentName[1], tournamentTourCount, false, tournamentTypeParings)

        apiRepository.putTournament(creatyTournament)

        var tournamentInLeagueId: Int = apiRepository.getTournamentInLeague().size

        leagues.forEach {league->
            if (league.leagueId == sortLeagueId){
                apiRepository.putTournamentInLeague(TournamentInLeague(league.changeScore, league.leagueId, true, creatyTournament.id), tournamentInLeagueId)
            }
            else
            {
                apiRepository.putTournamentInLeague(TournamentInLeague(league.changeScore, league.leagueId, false, creatyTournament.id), tournamentInLeagueId)
            }
            tournamentInLeagueId++
        }

        return tournaments.size
    }
}