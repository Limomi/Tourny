package com.example.tourny.ui.theme.screens.profile

import android.content.ClipData.Item
import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tourny.models.Tournament
import com.example.tourny.models.User
import com.example.tourny.models.onlyApp.LeagueWithScore
import com.example.tourny.network.RegistretedUser
import com.example.tourny.ui.theme.TournyGray
import com.example.tourny.ui.theme.TournyPurple
import com.example.tourny.ui.theme.components.TournyLeagueWithScoreCard
import com.example.tourny.ui.theme.components.TournyTournamentCard
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavController, userId: String?){

    val scope = rememberCoroutineScope()
    val profileViewModel: ProfileViewModel = viewModel()
    var userInfo by remember { mutableStateOf<User>(User("","","","","")) }
    var leagueWithScore by remember { mutableStateOf<List<LeagueWithScore>>(emptyList()) }
    var tournaments by remember { mutableStateOf<List<Tournament>>(emptyList()) }
    var seeLeagues by remember { mutableStateOf<Boolean>(false) }
    var seeTournaments by remember { mutableStateOf<Boolean>(false) }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun loadUserInfo(){
        errorMessage = null

        scope.launch{
            try {
                userInfo = profileViewModel.GetUserInfo(userId)
                leagueWithScore = profileViewModel.GetUserLeagues(userId)
                tournaments = profileViewModel.GetTournament(userId)
            }
            catch (e: Exception){
                errorMessage = e.message
            }
        }
    }

    LaunchedEffect(Unit){
        loadUserInfo()
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
                    text = "Профиль",
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
            ) {
                item {
                    Text(
                        modifier = Modifier.padding(6.dp),
                        text = userInfo.id,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium
                    )

                    Spacer(modifier = Modifier.padding(8.dp))
                }

                item{
                    Text(
                        modifier = Modifier.padding(6.dp),
                        text = if(!userInfo.patronymic.isNullOrEmpty()){userInfo.lastname + " " + userInfo.firstname[0] + ". " + userInfo.patronymic!![0] + "."}else{userInfo.firstname + " " + userInfo.lastname},
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                item {
                    errorMessage?.let { message ->
                        Text(
                            text = message,
                            color = Color.Red,
                            modifier = Modifier.padding(8.dp)
                        )
                        Log.e("e", message)
                    }

                    Spacer(modifier = Modifier.padding(12.dp))
                }

                item {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Лиги игрока",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelMedium
                        )

                        Button(
                            onClick = {
                                seeLeagues = !seeLeagues
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = TournyGray
                            )
                        ) {
                            Icon(
                                imageVector =if(seeLeagues){
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



                if(seeLeagues){
                    items(leagueWithScore){league ->
                        TournyLeagueWithScoreCard(league, navController)
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
                            text = "Турниры игрока",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelMedium
                        )

                        Button(
                            onClick = {
                                seeTournaments = !seeTournaments
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = TournyGray
                            )
                        ) {
                            Icon(
                                imageVector =if(seeTournaments){
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

                if(seeTournaments){
                    items(tournaments){tournament ->
                        TournyTournamentCard(tournament = tournament, {navController.navigate("Tournaments/${tournament.id}")})
                    }
                }

                if (userId == RegistretedUser.id){
                    item {
                        Spacer(modifier = Modifier.padding(12.dp))

                        Button(
                            onClick = {
                                RegistretedUser.id = "000"
                                navController.navigate("Entry")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            ),
                            border = BorderStroke(4.dp, Color.Red)
                        ) {
                            Text(
                                text = "Выйти из аккаунта",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        },

        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
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
                    selected = true,
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