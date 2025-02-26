package com.example.tourny.ui.theme.screens.join

import androidx.lifecycle.ViewModel
import com.example.tourny.models.Tournament
import com.example.tourny.models.UserInTournament
import com.example.tourny.network.api.ApiRepository

class JoinViewModel: ViewModel() {
    suspend fun joinTournament(TournamentCode:String):Int{
        val apiRepository = ApiRepository()
        val tournaments: List<Tournament> = apiRepository.getTournaments()
        var i: Int = tournaments.size-1
        while (i>=0){
            if (TournamentCode == tournaments[i].joinCode){
                return tournaments[i].id
            }
            i--
        }
        return -1
    }
}