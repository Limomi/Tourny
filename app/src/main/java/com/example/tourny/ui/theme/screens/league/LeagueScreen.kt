package com.example.tourny.ui.theme.screens.league

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.KeyboardArrowUp
import androidx.compose.material.icons.sharp.List
import androidx.compose.material.icons.sharp.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tourny.models.League
import com.example.tourny.models.Tournament
import com.example.tourny.models.onlyApp.UserWithScore
import com.example.tourny.network.RegistretedUser
import com.example.tourny.ui.theme.TournyGray
import com.example.tourny.ui.theme.TournyPurple
import com.example.tourny.ui.theme.components.TournyTournamentCard
import com.example.tourny.ui.theme.components.TournyUserWithScoreCard
import kotlinx.coroutines.launch

@Composable
fun LeagueScreen(navController: NavController, leagueId: String?){
    val scope = rememberCoroutineScope()
    var errorMessage: String? by remember { mutableStateOf<String?>(null) }
    val leagueViewModel: LeagueViewModel = viewModel()
    var league: League by remember { mutableStateOf<League>(League(0,-1,"","")) }
    var usersWithScore by remember { mutableStateOf<List<UserWithScore>>(emptyList()) }
    var tournaments: List<Tournament> by remember { mutableStateOf<List<Tournament>>(emptyList()) }
    var seeTornaments:Boolean by remember { mutableStateOf<Boolean>(false) }
    var seeUsers:Boolean by remember { mutableStateOf<Boolean>(false) }

    fun loadLeagueInfo(){
        scope.launch {
            try {
                league = leagueViewModel.getLeagueInfo(leagueId!!)
                usersWithScore = leagueViewModel.getUserLeague(leagueId)
                tournaments = leagueViewModel.getTournamentLeague(leagueId)
            }
            catch (e: Exception){
                errorMessage = e.message
                Log.e("errorGetInfo", errorMessage!!)
            }
        }
    }

    LaunchedEffect(Unit){
        loadLeagueInfo()
    }

    Scaffold(
        topBar = {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TournyPurple),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    modifier = Modifier.padding(6.dp),
                    text = "Лига",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White
                )
            }
        },

        content = {
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                item {
                    Spacer(modifier = Modifier.padding(8.dp))

                    Text(
                        text = league.leagueId.toString(),
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium,
                        lineHeight = 35.sp
                    )
                }

                item {
                    Spacer(modifier = Modifier.padding(12.dp))

                    Text(
                        text = league.name,
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium,
                        lineHeight = 35.sp
                    )
                }

                item {
                    Spacer(modifier = Modifier.padding(6.dp))

                    Text(
                        text = "Рейтинговая система: "+league.typeScore,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium,
                        lineHeight = 35.sp
                    )
                }

                item {
                    Spacer(modifier = Modifier.padding(6.dp))

                    Text(
                        text = "Начальный рейтинг: " + league.basicScore,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium,
                        lineHeight = 35.sp
                    )
                }

                item {

                    Spacer(modifier = Modifier.padding(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Турниры Лиги",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Button(
                            onClick = {
                                seeTornaments = !seeTornaments
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = TournyGray
                            )
                        ) {
                            Icon(
                                imageVector =if(seeTornaments){
                                    Icons.Sharp.KeyboardArrowUp}else{
                                    Icons.Sharp.Add} ,
                                contentDescription = ""
                            )
                        }
                    }

                    Spacer(modifier = Modifier
                        .padding(2.dp)
                    )
                    Row (
                        modifier = Modifier
                            .height(3.dp)
                            .fillMaxWidth()
                            .background(TournyGray)
                    ){

                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                }

                if(seeTornaments){
                    items(tournaments){tournament ->
                        TournyTournamentCard(tournament = tournament, {navController.navigate("Tournaments/${tournament.id}")})
                    }
                }

                item {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Игроки участвующие в лиге",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Button(
                            onClick = {
                                seeUsers = !seeUsers
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = TournyGray
                            )
                        ) {
                            Icon(
                                imageVector =if(seeUsers){
                                    Icons.Sharp.KeyboardArrowUp}else{
                                    Icons.Sharp.Add} ,
                                contentDescription = ""
                            )
                        }
                    }

                    Spacer(modifier = Modifier
                        .padding(2.dp)
                    )
                    Row (
                        modifier = Modifier
                            .height(3.dp)
                            .fillMaxWidth()
                            .background(TournyGray)
                    ){

                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                }
                
                if(seeUsers){
                    items(usersWithScore){user ->
                        TournyUserWithScoreCard(userWithScore = user, navController)
                    }
                }
            }
        },

        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { navController.navigate("Leagues") },
                    icon = {
                        Icon(
                            imageVector = Icons.Sharp.Share,
                            contentDescription = "Join tournament"
                        )
                    },
                    label = {
                        Text(
                            text = "Лиги"
                        )
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("JoinTournament") },
                    icon = {
                        Icon(
                            imageVector = Icons.Sharp.Add,
                            contentDescription = "Join tournament"
                        )
                    },
                    label = {
                        Text(
                            text = "Участвовать"
                        )
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("Tournaments") },
                    icon = {
                        Icon(
                            imageVector = Icons.Sharp.List,
                            contentDescription = "Tournaments"
                        )
                    },
                    label = {
                        Text(
                            text = "Турниры"
                        )
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("profile/${RegistretedUser.id}") },
                    icon = {
                        Icon(
                            imageVector = Icons.Sharp.Home,
                            contentDescription = "Join tournament"
                        )
                    },
                    label = {
                        Text(
                            text = "Профиль"
                        )
                    }
                )
            }
        }
    )
}