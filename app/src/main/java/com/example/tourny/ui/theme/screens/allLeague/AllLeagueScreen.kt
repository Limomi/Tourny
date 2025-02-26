package com.example.tourny.ui.theme.screens.allLeague

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tourny.models.League
import com.example.tourny.network.RegistretedUser
import com.example.tourny.network.api.ApiRepository
import com.example.tourny.ui.theme.TournyPurple
import com.example.tourny.ui.theme.components.TournyButton
import com.example.tourny.ui.theme.components.TournyLeagueCard
import com.example.tourny.ui.theme.components.TournyOutlinedTextField
import kotlinx.coroutines.launch

@Composable
fun AllLeagueScreen(navController: NavController){
    val scope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val apiRepository = ApiRepository()
    var leagues: List<League> by remember { mutableStateOf<List<League>>(emptyList()) }
    var seeSearch: Boolean by remember { mutableStateOf<Boolean>(false) }
    var searchString: String by remember { mutableStateOf<String>("") }

    fun loadLeagues(){
        errorMessage = null
        scope.launch {
            try {
                leagues = apiRepository.getLeagues()
            }
            catch (e:Exception){
                errorMessage = e.message
                Log.e("Ошибка загрузки", errorMessage!!)
            }
        }
    }

    LaunchedEffect(Unit){
        loadLeagues()
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.background(TournyPurple)
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        modifier = Modifier.padding(6.dp),
                        text = "Лиги",
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
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp)
                ) {
                    item{
                        Spacer(modifier = Modifier.padding(10.dp))

                        Text(
                            text = "Не нашли свою лигу? Может вы её ещё не создали?",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.Center,
                            lineHeight = 26.sp
                        )

                        Spacer(modifier = Modifier.padding(4.dp))

                        TournyButton(text = "Создать", ) {
                            navController.navigate("League/Create")
                        }

                        Spacer(modifier = Modifier.padding(6.dp))

                        errorMessage?.let {message ->
                            Text(
                                text = message,
                                color = Color.Red,
                                modifier = Modifier.padding(8.dp)
                            )
                            Log.e("e", message)
                        }
                    }

                    items(leagues){league->
                        if (league.name.contains(searchString, true)){
                            TournyLeagueCard(
                                league = league
                            ) {
                                navController.navigate("Leagues/${league.leagueId}")
                            }
                        }
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
