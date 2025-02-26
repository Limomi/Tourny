package com.example.tourny

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tourny.models.Tournament
import com.example.tourny.ui.theme.TournyTheme
import com.example.tourny.ui.theme.screens.acceptJoinTournament.AcceptJoinTournamentScreen
import com.example.tourny.ui.theme.screens.allLeague.AllLeagueScreen
import com.example.tourny.ui.theme.screens.profile.ProfileScreen
import com.example.tourny.ui.theme.screens.join.JoinScreen
import com.example.tourny.ui.theme.screens.entry.EntryScreen
import com.example.tourny.ui.theme.screens.registreted.RegistretedScreen
import com.example.tourny.ui.theme.screens.allTournaments.AllTournamentsScreen
import com.example.tourny.ui.theme.screens.createLeague.CreateLeagueScreen
import com.example.tourny.ui.theme.screens.createTournament.CreateTournamentScreen
import com.example.tourny.ui.theme.screens.league.LeagueScreen
import com.example.tourny.ui.theme.screens.paring.ParingScreen
import com.example.tourny.ui.theme.screens.tournament.TournamentScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TournyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TournyApp()
                }
            }
        }
    }
}

@Composable
private fun TournyApp(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Entry") {
        composable(route = "Tournaments"){
            AllTournamentsScreen(navController = navController)
        }

        composable(route = "Tournaments/{${"id"}}"){navBackTournamentEntry ->
            TournamentScreen(navController = navController, tournamentId = navBackTournamentEntry.arguments?.getString("id"))
        }
        composable(route = "Tournament/Create"){
            CreateTournamentScreen(navController = navController)
        }

        composable(route = "JoinTournament"){
            JoinScreen(navController = navController)
        }

        composable(route = "JoinTournament/{${"id"}}"){navBackJoinEntry ->
            AcceptJoinTournamentScreen(navController = navController, tournamentId = navBackJoinEntry.arguments?.getString("id"))
        }

        composable(route = "profile/{${"id"}}"){ navBackProfileEntry ->
            ProfileScreen(navController = navController, userId =  navBackProfileEntry.arguments?.getString("id"))
        }
        
        composable(route = "Entry"){
            EntryScreen(navController = navController)
        }
        
        composable(route = "Registreted"){
            RegistretedScreen(navController = navController)
        }
        
        composable(route = "Leagues"){
            AllLeagueScreen(navController = navController)
        }

        composable(route = "Leagues/{${"id"}}"){navBackLeagueEntry ->
            LeagueScreen(navController = navController, leagueId = navBackLeagueEntry.arguments?.getString("id"))
        }
        
        composable(route = "League/Create"){
            CreateLeagueScreen(navController = navController)
        }

        composable(route = "Paring/{${"id"}}"){ navBackParingEntry ->
            ParingScreen(navController = navController, paringId = navBackParingEntry.arguments?.getString("id"))
        }
    }
}