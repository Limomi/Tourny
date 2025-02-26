package com.example.tourny.network.api

import com.example.tourny.models.League
import com.example.tourny.models.Paring
import com.example.tourny.models.Tour
import com.example.tourny.models.Tournament
import com.example.tourny.models.TournamentInLeague
import com.example.tourny.models.User
import com.example.tourny.models.UserInLeague
import com.example.tourny.models.UserInTournament
import com.example.tourny.network.api.ApiClient.client
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ApiRepository {
//    suspend fun getTournaments(): List<Tournament> = client.get(ApiRoutes.SEARCH_TOURNAMENT).body()?: emptyList<Tournament>()
    suspend fun getTournaments(): List<Tournament> = client.get(ApiRoutes.SEARCH_TOURNAMENT).body()

    suspend fun getSingleTournament(id: String?): Tournament = client.get(ApiRoutes.SINGLE_TOURNAMENT + "${id}.json").body()

    suspend fun putTournament(tournament: Tournament){
        client.put(ApiRoutes.SINGLE_TOURNAMENT + "${tournament.id}.json"){
            contentType(ContentType.Application.Json)
            setBody(tournament)
        }
    }
    suspend fun updateTournament(tournament: Tournament){
        client.patch(ApiRoutes.SINGLE_TOURNAMENT + "${tournament.id}.json"){
            contentType(ContentType.Application.Json)
            setBody(tournament)
        }
    }
    suspend fun getUsers(): List<User> = client.get(ApiRoutes.SEARCH_USER).body()

    suspend fun getSingleUser(id: Int): User = client.get(ApiRoutes.SINGLE_USER + "${id}.json").body()

    suspend fun putUser(
        id:Int,
        login: String,
        firstname: String,
        lastname: String,
        patronymic: String,
        passwordNewUser: String
    ){
        client.put(ApiRoutes.SINGLE_USER + "${id}.json")
        {
            contentType(ContentType.Application.Json)
            setBody(User(login, firstname, lastname, patronymic, passwordNewUser))
        }
    }

    suspend fun getLeagues(): List<League> = client.get(ApiRoutes.SEARCH_LEAGUE).body()
    suspend fun getSingleLeague(id: Int): League = client.get(ApiRoutes.SINGLE_LEAGUE + "${id}.json").body()

    suspend fun putLeague(league: League, id: Int){
        client.put(ApiRoutes.SINGLE_LEAGUE + "${id}.json")
        {
            contentType(ContentType.Application.Json)
            setBody(league)
        }
    }

    suspend fun getUserInTournament(): List<UserInTournament> = client.get(ApiRoutes.SEARCH_USERINTOURNAMENT).body()

    suspend fun updateUserInTournament(userInTournament: UserInTournament){
        client.patch(ApiRoutes.SINGLE_USERINTOURNAMENT + "${userInTournament.id}.json")
        {
            contentType(ContentType.Application.Json)
            setBody(userInTournament)
        }
    }

    suspend fun putUserInTournament(userInTournament: UserInTournament, id: Int){
        client.put(ApiRoutes.SINGLE_USERINTOURNAMENT + "${id}.json")
        {
            contentType(ContentType.Application.Json)
            setBody(userInTournament)
        }
    }

    suspend fun getUserInLeague(): List<UserInLeague> = client.get(ApiRoutes.SEARCH_USERINLEAGUE).body()

    suspend fun updateUserInLeague(userInLeague: UserInLeague, id: Int){
        client.patch(ApiRoutes.SINGLE_USERINLEAGUE + "${id}.json"){
            contentType(ContentType.Application.Json)
            setBody(userInLeague)
        }
    }

    suspend fun putUserInLeague(userInLeague: UserInLeague, id: Int){
        client.put(ApiRoutes.SINGLE_USERINLEAGUE + "${id}.json"){
            contentType(ContentType.Application.Json)
            setBody(userInLeague)
        }
    }

    suspend fun getTournamentInLeague(): List<TournamentInLeague> = client.get(ApiRoutes.SEARCH_TOURNAMENTINLEAGUE).body()

    suspend fun putTournamentInLeague(tournamentInLeague: TournamentInLeague, id: Int){
        client.put(ApiRoutes.SINGLE_TOURNAMENTINLEAGUE + id + ".json"){
            contentType(ContentType.Application.Json)
            setBody(tournamentInLeague)
        }

    }
    suspend fun getTours(): List<Tour> = client.get(ApiRoutes.SEARCH_TOUR).body()

    suspend fun putTour(tour: Tour){
        client.put(ApiRoutes.SINGLE_TOUR + tour.id + ".json"){
            contentType(ContentType.Application.Json)
            setBody(tour)
        }
    }

    suspend fun updateTour(tour: Tour){
        client.patch(ApiRoutes.SINGLE_TOUR + tour.id + ".json") {
            contentType(ContentType.Application.Json)
            setBody(tour)
        }
    }

    suspend fun getParings(): List<Paring> = client.get(ApiRoutes.SEARCH_PARING).body()

    suspend fun getSingleParing(paringId: Int): Paring = client.get(ApiRoutes.SINGLE_PARING + paringId + ".json").body()

    suspend fun putParing(paring: Paring){
        client.put(ApiRoutes.SINGLE_PARING + paring.id + ".json"){
            contentType(ContentType.Application.Json)
            setBody(paring)
        }
    }

    suspend fun updateParing(paring: Paring){
        client.patch(ApiRoutes.SINGLE_PARING + paring.id + ".json"){
            contentType(ContentType.Application.Json)
            setBody(paring)
        }
    }

//  Ручной json
//val file = User(login, lastname, firstname, patronymic, passwordNewUser)
//            contentType(ContentType.Application.Json)
//            var jsonchik = Json.encodeToJsonElement(mapOf("${id}" to file))
//            headers{
//                append(HttpHeaders.PublicKeyPins, "${id}")
//            }
//            setBody(jsonchik)


//
//        client.post(ApiRoutes.SEARCH_USER) {
//            contentType(ContentType.Application.Json)
//            setBody(User(login, firstname, lastname, patronymic, passwordNewUser))
//        }

//            Log.e("error", jsonchik.toString().substring(1, jsonchik.toString().count()-1))
//            setBody(","+jsonchik.toString().substring(1, jsonchik.toString().count()-1))

//        val getTournaments: List<Tournament> = client.get(ApiRoutes.SEARCH_TOURNAMENT).body()
//    suspend fun getTournaments(): HttpResponse = client.request(ApiRoutes.SEARCH_TOURNAMENT) {
//        method = HttpMethod.Get
//    }
}