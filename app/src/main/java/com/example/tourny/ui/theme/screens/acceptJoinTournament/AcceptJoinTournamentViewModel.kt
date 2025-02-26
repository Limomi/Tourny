package com.example.tourny.ui.theme.screens.acceptJoinTournament

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tourny.models.League
import com.example.tourny.models.TournamentInLeague
import com.example.tourny.models.UserInLeague
import com.example.tourny.models.UserInTournament
import com.example.tourny.network.RegistretedUser
import com.example.tourny.network.api.ApiRepository

class AcceptJoinTournamentViewModel: ViewModel() {
    suspend fun getTournamentLeague(tournamentId: String?):List<League>{
        val apiRepository = ApiRepository()
        val leaguesForTournament: MutableList<League> = mutableListOf()
        val leagues: List<League> = apiRepository.getLeagues()
        val tournamentInLeagues: List<TournamentInLeague> = apiRepository.getTournamentInLeague()
        var scopeTournamentInLeague:Int = tournamentInLeagues.size-1

        tournamentInLeagues.forEach {connectionTIL ->
            if (tournamentId!!.toInt() == connectionTIL.tournamentId){
                leaguesForTournament.add(leagues[connectionTIL.leagueId])
            }
        }

//        while (scopeTournamentInLeague>=0){
//            if (tournamentId ==tournamentInLeagues[scopeTournamentInLeague].tournamentId.toString() ){
//                leaguesForTournament.add(leagues[tournamentInLeagues[scopeTournamentInLeague].tournamentId])
//            }
//            scopeTournamentInLeague--
//        }
        return leaguesForTournament
    }

    suspend fun joinTournament(tournamentId: String?):String{
        val apiRepository = ApiRepository()
        val userInTornament: List<UserInTournament> = apiRepository.getUserInTournament()
        var notInTournament: Boolean = true
        var score: Int = userInTornament.size-1
        while (score>=0){
            if (userInTornament[score].tournamentId.toString() == tournamentId &&
                userInTornament[score].userId == RegistretedUser.id){
                notInTournament = false
                break
            }
            score--
        }

        if (notInTournament){

            apiRepository.putUserInTournament(UserInTournament(userInTornament.size, 0, tournamentId!!.toInt(), RegistretedUser.id), userInTornament.size)
            Log.e("Error", "Ошибка на получении")
            val tournamentInLeague: List<TournamentInLeague> = apiRepository.getTournamentInLeague()
            val userInLeague: List<UserInLeague> = apiRepository.getUserInLeague()
            var league: League = League(0,0,"", "")
            var notInLeague: Boolean = true
            score = tournamentInLeague.size - 1
            while (score>=0){
                if (tournamentInLeague[score].tournamentId == tournamentId.toInt()){
                    notInLeague = true
                    loopcheck@ for (userLeag in userInLeague) {
                        if (
                            tournamentInLeague[score].leagueId ==userLeag.leagueId &&
                            userLeag.userId==RegistretedUser.id
                            ){
                            notInLeague = false
                            break@loopcheck
                        }
                    }

                    if (notInLeague){
                        Log.e("Error", "Не получает")
                        league = apiRepository.getSingleLeague(tournamentInLeague[score].leagueId)
                        Log.e("Нет в лиге", "Не отсылает")
                        apiRepository.putUserInLeague(UserInLeague(league.leagueId, league.basicScore, RegistretedUser.id), userInLeague.size)
                    }
                }
             score--
            }
        }
        return ""
    }
}