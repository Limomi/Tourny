package com.example.tourny.network.api

object ApiRoutes {
    private const val BASE_URL: String = "https://tourny-830e9-default-rtdb.firebaseio.com"
    var SEARCH_TOURNAMENT = "$BASE_URL/tournament.json"
    var SINGLE_TOURNAMENT = "$BASE_URL/tournament/"
    var SEARCH_USER = "$BASE_URL/user.json"
    var SINGLE_USER ="$BASE_URL/user/"
    var SEARCH_LEAGUE = "$BASE_URL/league.json"
    var SINGLE_LEAGUE = "$BASE_URL/league/"
    var SEARCH_USERINTOURNAMENT = "$BASE_URL/userInTournament.json"
    var SINGLE_USERINTOURNAMENT = "$BASE_URL/userInTournament/"
    var SEARCH_USERINLEAGUE = "$BASE_URL/userInLeague.json"
    var SINGLE_USERINLEAGUE = "$BASE_URL/userInLeague/"
    var SEARCH_TOURNAMENTINLEAGUE = "$BASE_URL/tournamentInLeague.json"
    var SINGLE_TOURNAMENTINLEAGUE = "$BASE_URL/tournamentInLeague/"
    var SEARCH_TOUR = "$BASE_URL/tour.json"
    var SINGLE_TOUR = "$BASE_URL/tour/"
    var SEARCH_PARING = "$BASE_URL/paring.json"
    var SINGLE_PARING = "$BASE_URL/paring/"
}
