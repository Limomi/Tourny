package com.example.tourny.ui.theme.screens.allTournaments

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.KeyboardArrowUp
import androidx.compose.material.icons.sharp.List
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material.icons.sharp.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
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
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tourny.models.Tournament
import com.example.tourny.network.RegistretedUser
import com.example.tourny.network.api.ApiRepository
import com.example.tourny.ui.theme.TournyPurple
import com.example.tourny.ui.theme.components.TournyButton
import com.example.tourny.ui.theme.components.TournyOutlinedTextField
import com.example.tourny.ui.theme.components.TournyTournamentCard
import kotlinx.coroutines.launch

@Composable
fun AllTournamentsScreen(navController: NavHostController){
    var tournaments by remember { mutableStateOf<List<Tournament>>(emptyList()) }
//    var showLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var seeSearch by remember { mutableStateOf<Boolean>(false) }
    var searchString: String by remember { mutableStateOf<String>("") }

    val scope = rememberCoroutineScope()
    val apiRepository = ApiRepository()

//    var refresh by remember { mutableStateOf<Boolean>(false) }
//    val ptrState = rememberPullRefreshState(refresh, {loadTournaments()})

    fun loadTournaments() {
//        showLoading = true
        errorMessage = null

        scope.launch {
            try {
                val response = apiRepository.getTournaments()
                tournaments = response
//                tournaments = apiRepository.getTournaments()
            } catch (e: Exception){
                errorMessage = "Error loading tournaments ${e.message}"
            }
//            finally {
//                showLoading = false
//            }
        }
    }

    LaunchedEffect(Unit) {
        loadTournaments()
    }

//    Column(
//    modifier = Modifier.padding(16.dp)
//    ) {
//        Text(
//            text = "Tournaments",
//            style = MaterialTheme.typography.headlineLarge,
//            modifier = Modifier.padding(bottom = 8.dp)
//        )
//
////        if(showLoading){
////            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
////        }
//
//        errorMessage?.let { message ->
//            Text(
//                text = message,
//                color = Color.Red,
//                modifier = Modifier.padding(8.dp)
//            )
//            Log.e("e", message)
//        }
//
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            items(tournaments) {tournament ->
//                TournamentItem(tournament = tournament)
//            }
//        }
//
//        Button(onClick = { loadTournaments() },
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//                .padding(top = 16.dp)
//        ) {
//            Text(text = "Reload")
//        }
//    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.background(TournyPurple)
            ){
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        modifier = Modifier.padding(6.dp),
                        text = "Турниры",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White
                    )

                    Button(
                        modifier = Modifier.padding(6.dp),
                        onClick = { seeSearch = !seeSearch },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TournyPurple,
                            contentColor = Color.White
                        )

                    ) {
                        Icon(
                            imageVector =if(seeSearch){Icons.Sharp.KeyboardArrowUp}else{Icons.Sharp.Search},
                            contentDescription = ""
                        )
                    }
                }

                if(seeSearch){
                    Spacer(modifier = Modifier.padding(2.dp))
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        TournyOutlinedTextField(
                            labelName = "",
                            variableText = searchString,
                            changingVariableText = {
                                    newText -> searchString = newText
                            },
                            keyboardType = KeyboardType.Text
                        )
                    }
                    Spacer(modifier = Modifier.padding(2.dp))
                }
            }
        },

        content = {
//            Box(
//                modifier = Modifier.pullRefresh()
//            ) {
//
//            }
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                item{
                    Spacer(modifier = Modifier.padding(10.dp))

                    Text(
                        text = "Не нашли свой турнир? Может вы его ещё не создали",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center,
                        lineHeight = 26.sp
                    )
                }

                item {
                    Spacer(modifier = Modifier.padding(4.dp))

                    TournyButton(
                        text = "Создать",
                        {navController.navigate("Tournament/Create")}
                    )
                }

                item {
                    Spacer(modifier = Modifier.padding(6.dp))

                    errorMessage?.let { message ->
                        Text(
                            text = message,
                            color = Color.Red,
                            modifier = Modifier.padding(8.dp)
                        )
                        Log.e("e", message)
                    }
                }


                items(tournaments) {tournament ->
                    if (tournament.name.contains(searchString, true)){
                        TournyTournamentCard(tournament = tournament, {navController.navigate("Tournaments/${tournament.id}")})
                    }
                }

                item {
                    Button(onClick = { loadTournaments() },
                        modifier = Modifier
                            .padding(top = 16.dp)
                    ) {
                        Text(text = "Перезагрузить")
                    }
                }
            }
        },

        bottomBar = {
            NavigationBar(

            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("Leagues") },
                    icon = {
                        Icon(
                            imageVector = Icons.Sharp.Share,
                            contentDescription = "Join tournament",
                        )
                    },
                    label = {
                        Text(
                            text = "Лиги"
                        )
                    },
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
                    selected = true,
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
