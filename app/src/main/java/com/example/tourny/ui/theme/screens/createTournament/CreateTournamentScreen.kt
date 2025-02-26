package com.example.tourny.ui.theme.screens.createTournament

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.List
import androidx.compose.material.icons.sharp.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FabPosition
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tourny.models.League
import com.example.tourny.models.onlyApp.LeagueForCreateTournament
import com.example.tourny.network.RegistretedUser
import com.example.tourny.ui.theme.TournyPurple
import com.example.tourny.ui.theme.components.TournyButton
import com.example.tourny.ui.theme.components.TournyOutlinedTextField
import kotlinx.coroutines.launch

@Composable
fun CreateTournamentScreen(navController: NavController){
    val scope = rememberCoroutineScope()
    val createTournamentViewModel :CreateTournamentViewModel = viewModel()
    var errorMessage: String by remember { mutableStateOf<String>("") }

    var leagues: List<League> by remember { mutableStateOf<List<League>>(emptyList()) }
    var nameTournament: String by remember { mutableStateOf<String>("") }
//    var seeDrop: Boolean by remember { mutableStateOf<Boolean>(false) }
    var typeTournament: String by remember { mutableStateOf<String>("Лучший с лучшим") }
    var tourCount: String by remember { mutableStateOf("") }
    var leagueSearch: String by remember { mutableStateOf<String>("") }
    var addLeague: MutableList<Boolean> by remember { mutableStateOf<MutableList<Boolean>>(mutableListOf())}
    var leaguesForTournament: MutableList<LeagueForCreateTournament> by remember { mutableStateOf<MutableList<LeagueForCreateTournament>>(mutableListOf()) }
    var sortLeagueId:Int by remember { mutableStateOf(-1) }
    var idNewTournament:Int by remember { mutableStateOf(-1) }

    var redraw: Boolean by remember { mutableStateOf(true) }

    fun loadLeagues(){
        scope.launch {
            try {
                leagues = createTournamentViewModel.GetLeagues()
                leagues.forEach {
                    addLeague.add(false)
                }
            }
            catch (e: Exception){
                errorMessage = e.message.toString()
            }
        }
    }
//    if(typeTournament == "Лучший с лучшим"){true}else{false}
    LaunchedEffect(Unit) {
        loadLeagues()
    }

    Scaffold (
        topBar = {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TournyPurple),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    modifier = Modifier.padding(6.dp),
                    text = "Создай турнир",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White
                )
            }
        },

        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                item{
                    TournyOutlinedTextField(
                        labelName = "Название турнира",
                        variableText = nameTournament,
                        changingVariableText = {
                                newText -> nameTournament = newText
                        },
                        keyboardType = KeyboardType.Text
                    )

                    Spacer(modifier = Modifier.padding(6.dp))
                }

                item {


                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Тип первоначальных парингов",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Black,
                            lineHeight = 22.sp,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.padding(6.dp))

                        Row (
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxWidth()
                                .padding(6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Checkbox(
                                checked = (typeTournament == "Лучший с лучшим"),
                                onCheckedChange = {typeTournament = "Лучший с лучшим"}
                            )

                            Text(
                                text = "Лучший с лучшим",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Black
                            )
                        }

                        Row (
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxWidth()
                                .padding(6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Checkbox(
                                checked = (typeTournament == "Лучший со средним"),
                                onCheckedChange = {typeTournament = "Лучший со средним"}
                            )

                            Text(
                                text = "Лучший со средним",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Black
                            )
                        }

                        Row (
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxWidth()
                                .padding(6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Checkbox(
                                checked = (typeTournament == "Случайные"),
                                onCheckedChange = {typeTournament = "Случайные"}
                            )

                            Text(
                                text = "Случайные",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Black
                            )
                        }
                    }
                }
//              Подредактировать выпадающие пункты
//                item {
//                    Button(
//                        onClick = { seeDrop = !seeDrop },
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color.White,
//                            contentColor = Color.Black
//                        )
//                    ) {
//                        Text(
//                            text = typeTournament,
//                            fontSize = 30.sp,
//                            fontWeight = FontWeight.Bold,
//                            style = MaterialTheme.typography.labelMedium,
//                            color = Color.Black
//                        )
//                    }
//
//                    Box (
//                        modifier = Modifier.width(200.dp)
//                    ){
//                        DropdownMenu(
//                            expanded = seeDrop,
//                            onDismissRequest = { seeDrop = false }) {
//
//                            DropdownMenuItem(
//                                text = {
//                                    Text(
//                                        text = "Лучшие с лучшими",
//                                        fontSize = 20.sp,
//                                        fontWeight = FontWeight.Bold,
//                                        style = MaterialTheme.typography.labelMedium
//                                    )
//                                },
//                                onClick = { typeTournament = "Лучшие с лучшими" }
//                            )
//
//                            DropdownMenuItem(
//                                text = {
//                                    Text(
//                                        text = "Случайные",
//                                        fontSize = 20.sp,
//                                        fontWeight = FontWeight.Bold,
//                                        style = MaterialTheme.typography.labelMedium
//                                    )
//                                },
//                                onClick = { typeTournament = "Случайные" }
//                            )
//
//                            DropdownMenuItem(
//                                text = {
//                                    Text(
//                                        text = "Ручные",
//                                        fontSize = 20.sp,
//                                        fontWeight = FontWeight.Bold,
//                                        style = MaterialTheme.typography.labelMedium
//                                    )
//                                },
//                                onClick = { typeTournament = "Ручные" }
//                            )
//                        }
//                    }
//                }

                item {
                    TournyOutlinedTextField(
                        labelName = "Количество раундов",
                        variableText = tourCount,
                        changingVariableText = {newText -> tourCount = newText},
                        keyboardType = KeyboardType.Number
                    )
                }

                item {
                    Spacer(modifier = Modifier.padding(4.dp))
                    
                    TournyOutlinedTextField(
                        labelName = "Выбор лиги",
                        variableText = leagueSearch,
                        changingVariableText = {newText->leagueSearch = newText},
                        keyboardType = KeyboardType.Text
                    )
                }

                if(leagueSearch.isNotEmpty()){
                    items(leagues){ league->
                        if (league.name.contains(leagueSearch, true)){
                            CardAddLeagueTournament(
                                leagueName = league.name,
                                navTrigger = {
                                    navController.navigate("Leagues/${league.leagueId}")
                                },
                                addTrigger = {
                                    if(addLeague[league.leagueId] == false){
                                        addLeague[league.leagueId] = true
                                        leaguesForTournament.add(LeagueForCreateTournament(league.leagueId, league.name, false, false))
                                        redraw = !redraw
                                    }
                                }
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.padding(8.dp))
                }

                items(leaguesForTournament){league ->
                    CardLeagueForTournament(
                        league,
                        navController,
                        {
                            league.changeScore = !league.changeScore
                            redraw = !redraw
                        },
                        sortLeagueId,
                        {
                            sortLeagueId = league.leagueId
                        }
                    )
                }

                item{
                    Spacer(modifier = Modifier.padding(40.dp))
                }

                //for when you add league in tournament, cards with new league add now
                if (redraw){
                    item {
                        Spacer(modifier = Modifier.padding(0.dp))
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
        },

        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            TournyButton(text = "Создать турнир") {
                if(nameTournament.count() >= 5){
                    if(tourCount.isNotEmpty()){
                        try {
                            scope.launch {
                                idNewTournament = createTournamentViewModel.CreateTournament(nameTournament, tourCount.toInt(), typeTournament, leaguesForTournament, sortLeagueId)
                                navController.navigate("Tournaments/${idNewTournament}")
                            }
                            leaguesForTournament.forEach { league->
                                Log.e("Лига", league.leagueName)
                            }
                        }
                        catch (e: Exception){
                            errorMessage = e.message!!
                            Log.e("Ошибка на создании", errorMessage)
                        }
                    }
                    else{
                        errorMessage = "Поле \"количество раундов\" не должно быть пустым"
                    }
                }
                else{
                    errorMessage = "Поле \"название турнира\" должно быть длинной минимум 5 символов"
                }
            }
        }
    )
}

@Composable
fun CardAddLeagueTournament(leagueName: String, navTrigger: ()->Unit, addTrigger: ()->Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(2.dp, TournyPurple, shape = RoundedCornerShape(6.dp))
            .background(Color.White),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Row (
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Button(
                onClick = navTrigger,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = leagueName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Black
                )
            }

            Button(
                onClick = addTrigger,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    imageVector = Icons.Sharp.Add,
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
fun CardLeagueForTournament(leaguesForTournament: LeagueForCreateTournament, navController: NavController, changeScoreTrigger: (Boolean)->Unit, sortLeagueId: Int, changeSortTrigger: (Boolean)->Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(2.dp, TournyPurple, shape = RoundedCornerShape(6.dp))
            .background(Color.White),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(

        ) {
            Row (
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Button(
                    onClick = {navController.navigate("Leagues/${leaguesForTournament.leagueId}")},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = leaguesForTournament.leagueName,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Black
                    )
                }

                Button(
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "Удалить")
                }
            }

            Row (
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Checkbox(
                    checked = leaguesForTournament.changeScore,
                    onCheckedChange = changeScoreTrigger,
                )

                Text(
                    text = "Влияет на рейтинг"
                )
            }

            Row (
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Checkbox(
                    checked = (leaguesForTournament.leagueId == sortLeagueId),
                    onCheckedChange = changeSortTrigger,
                )

                Text(
                    text = "Влияет на паринги"
                )
            }
        }
    }
}