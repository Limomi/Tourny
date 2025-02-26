package com.example.tourny.ui.theme.screens.tournament

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tourny.models.League
import com.example.tourny.models.Paring
import com.example.tourny.models.Tour
import com.example.tourny.models.Tournament
import com.example.tourny.models.TournamentInLeague
import com.example.tourny.models.User
import com.example.tourny.models.UserInLeague
import com.example.tourny.models.UserInTournament
import com.example.tourny.models.onlyApp.ParingWithName
import com.example.tourny.models.onlyApp.TourForApp
import com.example.tourny.models.onlyApp.UserWithScore
import com.example.tourny.network.api.ApiRepository
import kotlin.math.pow

class TournamentViewModel: ViewModel() {

    suspend fun loadTournamentInfo(tournamentId: Int): Tournament{
        val apiRepository = ApiRepository()

        val tournaments = apiRepository.getTournaments()

        return (tournaments[tournamentId])
    }

    suspend fun loadAllPlayers(tournamentId: Int): List<UserWithScore>{
        val apiRepository = ApiRepository()

        val userInTournament: List<UserInTournament> = apiRepository.getUserInTournament()
        val users: List<User> = apiRepository.getUsers()
        val userWithScore: MutableList<UserWithScore> = mutableListOf()

        userInTournament.forEach {userInTour->
            if (userInTour.tournamentId == tournamentId){
                userWithScore.add(UserWithScore(
                    users[userInTour.userId.toInt()].id,
                    users[userInTour.userId.toInt()].lastname,
                    users[userInTour.userId.toInt()].firstname,
                    users[userInTour.userId.toInt()].patronymic,
                    userInTour.scoreUser
                    )
                )
            }
        }

        userWithScore.sortByDescending { it.score }

        return userWithScore
    }

    suspend fun loadParings(tournamentId: Int): List<TourForApp>{
        val apiRepository = ApiRepository()

        val allTour: List<Tour> = apiRepository.getTours()
        val allParing: List<Paring> = apiRepository.getParings()
        val allUser: List<User> = apiRepository.getUsers()

        var tourForApp: MutableList<TourForApp> = mutableListOf()
//        var paringWithName: ParingWithName = ParingWithName(0, 0, "","","", "","","", "", "", 0, 0, false)

        allTour.forEach {tour ->
            if(tour.tournamentId == tournamentId){
                tourForApp.add(TourForApp(tour.id, tour.name, tour.closed ,tournamentId, mutableListOf()))
                allParing.forEach { paring ->
                    if(paring.tourId == tour.id){
                        tourForApp[tourForApp.size-1].parings.add(ParingWithName(paring.id, paring.numberInTour, paring.tourId, paring.firstUserId, paring.secondUserId, allUser[paring.firstUserId.toInt()].lastname, allUser[paring.secondUserId.toInt()].lastname, allUser[paring.firstUserId.toInt()].firstname, allUser[paring.secondUserId.toInt()].firstname, allUser[paring.firstUserId.toInt()].patronymic, allUser[paring.secondUserId.toInt()].patronymic, paring.firstUserScore.toString(), paring.secondUserScore.toString(), paring.entered))
                    }
                }
            }
        }

        tourForApp = tourForApp.reversed().toMutableList()

        return tourForApp
    }

    suspend fun closeTour(tour: TourForApp, tournament: Tournament, parings: List<ParingWithName>){
        val apiRepository = ApiRepository()
        apiRepository.updateTour(Tour(tour.id,tour.tournamentId, tour.name, true))

        val users:List<UserInTournament> = apiRepository.getUserInTournament()
        val usersInTournament:MutableList<UserInTournament> = mutableListOf()

        users.forEach { user->
            if(user.tournamentId == tournament.id){
                usersInTournament.add(user)
            }
        }

        parings.forEach {paring->
            if (paring.firstUserScore ==paring.secondUserScore){
                usersInTournament.forEach { user->
                    if(user.userId == paring.firstUserId){
                        apiRepository.updateUserInTournament(UserInTournament(user.id, user.scoreUser + 1, user.tournamentId, user.userId))
                    }
                    if (user.userId == paring.secondUserId){
                        apiRepository.updateUserInTournament(UserInTournament(user.id, user.scoreUser + 1, user.tournamentId, user.userId))
                    }
                }
            }
            else{
                if (paring.firstUserScore>paring.secondUserScore){
                    usersInTournament.forEach { user->
                        if(user.userId == paring.firstUserId){
                            apiRepository.updateUserInTournament(UserInTournament(user.id, user.scoreUser + 3, user.tournamentId, user.userId))
                        }
                    }
                }
                else{
                    usersInTournament.forEach { user->
                        if (user.userId == paring.secondUserId){
                            apiRepository.updateUserInTournament(UserInTournament(user.id, user.scoreUser + 3, user.tournamentId, user.userId))
                        }
                    }
                }
            }
        }

//      ELO rating

        val leagues: List<League> = apiRepository.getLeagues()
        val tournamentInLeague: List<TournamentInLeague> = apiRepository.getTournamentInLeague()
        val eloLeaguesId: MutableList<Int> = mutableListOf()

        tournamentInLeague.forEach { connectionTIL ->
            if(
                connectionTIL.tournamentId == tournament.id &&
                "Elo" == leagues[connectionTIL.leagueId].typeScore &&
                connectionTIL.changeScore
                ){
                eloLeaguesId.add(connectionTIL.leagueId)
            }
        }

        if(eloLeaguesId.size>0){
            val usersInLeague: List<UserInLeague> = apiRepository.getUserInLeague()

            var oldFirtUserRating: Int = 0
            var oldSecondUserRating: Int = 0
            var firstUILId = 0
            var secondUILId = 0

            parings.forEach {paring->
                eloLeaguesId.forEach eloLeagueLoop@ { eloLeagueId->
                    firstUILId = 0
                    secondUILId = 0
                    usersInLeague.forEach { userInLeagueFirst ->
                        if(
                            userInLeagueFirst.userId == paring.firstUserId &&
                            userInLeagueFirst.leagueId == eloLeagueId
                            ){
                            usersInLeague.forEach { userInLeagueSecond ->

                                    if (
                                        userInLeagueSecond.userId == paring.secondUserId &&
                                        userInLeagueSecond.leagueId == eloLeagueId
                                    ){
                                        oldFirtUserRating = userInLeagueFirst.score
                                        oldSecondUserRating = userInLeagueSecond.score



                                        if (paring.firstUserScore > paring.secondUserScore){
                                            userInLeagueFirst.score = newEloRating(oldFirtUserRating, oldSecondUserRating, 1.0)
                                            userInLeagueSecond.score = newEloRating(oldSecondUserRating, oldFirtUserRating, 0.0)
                                        }
                                        else{
                                            if (paring.firstUserId< paring.secondUserScore){
                                                userInLeagueFirst.score = newEloRating(oldFirtUserRating, oldSecondUserRating, 0.0)
                                                userInLeagueSecond.score = newEloRating(oldSecondUserRating, oldFirtUserRating, 1.0)
                                            }
                                            else{
                                                userInLeagueFirst.score = newEloRating(oldFirtUserRating, oldSecondUserRating, 0.5)
                                                userInLeagueSecond.score = newEloRating(oldSecondUserRating, oldFirtUserRating, 0.5)
                                            }
                                        }

                                        apiRepository.updateUserInLeague(userInLeagueFirst, firstUILId)
                                        apiRepository.updateUserInLeague(userInLeagueSecond, secondUILId)

                                        return@eloLeagueLoop
                                    }
                                secondUILId++
                            }
                        }
                        firstUILId++
                    }
                }
            }
        }
    }

    private fun newEloRating(playerA:Int, playerB:Int, k: Double):Int{
        val ten:Double = 10.0
        return (playerA + 20 * (k - (1/ ten.pow((playerB - playerA)/400)))).toInt()
    }


    fun isClosedTour(parings: List<ParingWithName>): Boolean{
        parings.forEach {paring->
            if (paring.entered == false){
                return false
            }
        }

        return true
    }

    suspend fun startTournament(tournament: Tournament){
        val apiRepository = ApiRepository()

        apiRepository.updateTournament(Tournament(tournament.adminId, tournament.id, tournament.name, "", tournament.roundCount, tournament.closedTournament, tournament.typeParing))

        makeFirstParings(tournament.id, tournament.typeParing)
    }

    private suspend fun makeFirstParings(
        tournamentId: Int,
        typeParings: String
    ){
        val apiRepository = ApiRepository()

        val users:List<UserInTournament> = apiRepository.getUserInTournament()
        val usersInTournament:MutableList<UserInTournament> = mutableListOf()
        val tournamentInLeagues: List<TournamentInLeague> = apiRepository.getTournamentInLeague()
        var sortLeagueId: Int = -1
        val tours:List<Tour> = apiRepository.getTours()
        val usersInLeague: List<UserInLeague> = apiRepository.getUserInLeague()
        val usersInTheLeague: MutableList<UserInLeague> = mutableListOf()
        val parings: List<Paring> = apiRepository.getParings()
        val paringsInTour: MutableList<Paring> = mutableListOf()

        apiRepository.putTour(Tour(tours.size, tournamentId, "1", false))

        users.forEach { user->
            if(user.tournamentId == tournamentId){
                usersInTournament.add(user)
            }
        }

        if(usersInTournament.size % 2 == 1){
            apiRepository.putUserInTournament(UserInTournament(usersInTournament.size, 0, tournamentId, "000000"), usersInTournament.size)
            usersInTournament.add(UserInTournament(usersInTournament.size, 0, tournamentId, "000000"))
        }

        tournamentInLeagues.forEach { connectionTIL->
            if(connectionTIL.tournamentId == tournamentId && connectionTIL.sort){
                sortLeagueId = connectionTIL.leagueId
                return@forEach
            }
        }

        usersInLeague.forEach {connectionUIL ->
            if(connectionUIL.leagueId ==sortLeagueId){
                usersInTheLeague.add(connectionUIL)
            }
        }

        usersInTournament.forEach { connectionUIT->
            usersInTheLeague.forEach secondLoop@ { connectionUIL->
                if (connectionUIT.userId == connectionUIL.userId){
                    connectionUIT.scoreUser = connectionUIL.score
                    return@secondLoop
                }
            }
        }

        usersInTournament.sortByDescending { it.scoreUser }


        var numberInTour: Int = 1
        var paringId: Int = parings.size
        when(typeParings){
            "Лучший с лучшим" ->{
                while(usersInTournament.size>0){
                    paringsInTour.add(Paring(paringId, numberInTour, tours.size, usersInTournament[0].userId,  usersInTournament[1].userId, 0, 0, false))
                    numberInTour++
                    paringId++
                    usersInTournament.removeAt(0)
                    usersInTournament.removeAt(0)
                }
            }
            "Лучший со средним"->{
                var secondUserId: Int = (usersInTournament.size / 2) + 1
                while (usersInTournament.size>0){
                    paringsInTour.add(Paring(paringId, numberInTour, tours.size, usersInTournament[0].userId, usersInTournament[secondUserId].userId, 0, 0, false))
                    numberInTour++
                    paringId++
                    usersInTournament.removeAt(secondUserId)
                    usersInTournament.removeAt(0)
                    secondUserId--
                }
            }
            "Случайные"->{
                usersInTournament.shuffle()
                while(usersInTournament.size>0){
                    paringsInTour.add(Paring(paringId, numberInTour, tours.size, usersInTournament[0].userId,  usersInTournament[1].userId, 0, 0, false))
                    numberInTour++
                    paringId++
                    usersInTournament.removeAt(0)
                    usersInTournament.removeAt(0)
                }
            }
        }

        paringsInTour.forEach { paring->
            apiRepository.putParing(paring)
        }
    }


    suspend fun makeParings(tournamentId: Int, tourName:String){
        val apiRepository = ApiRepository()

        val users:List<UserInTournament> = apiRepository.getUserInTournament()
        val usersInTournament:MutableList<UserInTournament> = mutableListOf()
        val tours: List<Tour> = apiRepository.getTours()
        val toursInTournament: MutableList<Tour> = mutableListOf()
        val parings: List<Paring> = apiRepository.getParings()
        val paringsOlds: MutableList<Paring> = mutableListOf()
        val paringsInTour: MutableList<Paring> = mutableListOf()

        users.forEach { user->
            if(user.tournamentId == tournamentId){
                usersInTournament.add(user)
            }
        }

        tours.forEach { tour ->
            if(tour.tournamentId == tournamentId){
                toursInTournament.add(tour)
            }
        }

        usersInTournament.sortByDescending {it.scoreUser}

        parings.forEach {paring->
            toursInTournament.forEach {tour->
                if (paring.tourId == tour.id){
                    paringsOlds.add(paring)
                }
            }
        }

        var tour: Tour = Tour(tours.size, tournamentId, tourName, false)

        apiRepository.putTour(tour)

        var double: Boolean = true
        var idUserSecond: Int = 0
        var numberInTour: Int = 1
        var paringId: Int = parings.size

        while(usersInTournament.size>0){
            idUserSecond = 0
            while (double){
                idUserSecond++
                double = false
                paringsOlds.forEach{ paring ->  
                    if(
                        (usersInTournament[0].userId==paring.firstUserId && usersInTournament[idUserSecond].userId == paring.secondUserId)
                        || (usersInTournament[0].userId==paring.secondUserId && usersInTournament[idUserSecond].userId == paring.firstUserId)){
                        double = true
                    }
                }

                if (!double){
                    paringsInTour.add(Paring(paringId, numberInTour, tour.id, usersInTournament[0].userId, usersInTournament[idUserSecond].userId, 0, 0, false))
                    numberInTour++
                    paringId++
                    usersInTournament.removeAt(idUserSecond)
                    usersInTournament.removeAt(0)
                }
                Log.e("Мисье цикл", idUserSecond.toString())
            }
            double = true
            Log.e("Цикл", usersInTournament.size.toString())
        }

        paringsInTour.forEach { paring ->
            apiRepository.putParing(paring)
        }

    }

    suspend fun closeTournament(tournamentId: Int){
        val apiRepository = ApiRepository()

        var tournament: Tournament = apiRepository.getSingleTournament(tournamentId.toString())

        tournament.closedTournament = true

        apiRepository.updateTournament(tournament)

        val tournamentInLeague: List<TournamentInLeague> = apiRepository.getTournamentInLeague()
        var theTournamentInLeague: MutableList<Int> = mutableListOf()

        val leagues: List<League> = apiRepository.getLeagues()

        tournamentInLeague.forEach {connection->
            if (tournament.id == connection.tournamentId){
                leagues.forEach { league->
                    if (connection.leagueId == league.leagueId){
                        if(league.typeScore == "MagicPoint"){
                            theTournamentInLeague.add(league.leagueId)
                        }
                    }
                }
            }
        }

        if(theTournamentInLeague.isNotEmpty()){
            val userInTournament = apiRepository.getUserInTournament()
            val theUserInTournament:MutableList<UserInTournament> = mutableListOf()

            userInTournament.forEach {user ->
                if(user.tournamentId == tournament.id){
                    theUserInTournament.add(user)
                }
            }

            theUserInTournament.sortByDescending { it.scoreUser }

            val userInLeague: List<UserInLeague> = apiRepository.getUserInLeague()
            var theUserInLeague: MutableList<UserInLeague> = mutableListOf()

            userInLeague.forEach { connectionUiL ->
                theUserInTournament.forEach {connectionUiT->
                    if(connectionUiL.userId == connectionUiT.userId){
                        theTournamentInLeague.forEach { connectionTiL->
                            if(connectionUiL.leagueId == connectionTiL){
                                theUserInLeague.add(connectionUiL)
                            }
                        }
                    }
                }
            }

            var countUser:Int = theUserInTournament.size

            theUserInTournament.forEach { connectionUiT ->
                theUserInLeague.forEach { connectionUiL ->
                    if (connectionUiT.userId == connectionUiL.userId){
                        connectionUiL.score += countUser
                        countUser--
                    }
                }
            }

            var idCountUserInLeague: Int = 0

            theUserInLeague.forEach { user ->
                idCountUserInLeague = 0
                userInLeague.forEach { allUserInLeague->
                    if(user.userId == allUserInLeague.userId && user.leagueId == allUserInLeague.leagueId){
                        apiRepository.updateUserInLeague(user, idCountUserInLeague)
                    }
                    idCountUserInLeague++
                }
            }
        }
    }
}

// возращать список туров