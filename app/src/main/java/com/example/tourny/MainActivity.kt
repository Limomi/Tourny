package com.example.tourny

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.example.tourny.network.RegistretedUser
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
                val sharedPreference =  getSharedPreferences("preferUserId", Context.MODE_PRIVATE)
                var fisrtScreen: String
                if (sharedPreference.getString("userId", "000").toString() == "000"){
                    fisrtScreen = "Entry"
                }
                else{
                    fisrtScreen = "Tournaments"
                }
//                val editor = sharedPreference.edit()
//                editor.putString("userId", "000")
//                editor.commit()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TournyApp(fisrtScreen)
                }
            }
        }
    }


    override fun onPause() {
        super.onPause()

        val sharedPreference =  getSharedPreferences("preferUserId", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("userId", RegistretedUser.id)
        editor.commit()
    }

    override fun onStart() {
        super.onStart()

        val sharedPreference =  getSharedPreferences("preferUserId", Context.MODE_PRIVATE)
        RegistretedUser.id = sharedPreference.getString("userId", "000").toString()
        Log.e("1221", sharedPreference.getString("userId", "000").toString())
    }
}

@Composable
private fun TournyApp(firstsCreen: String){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = firstsCreen) {
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
