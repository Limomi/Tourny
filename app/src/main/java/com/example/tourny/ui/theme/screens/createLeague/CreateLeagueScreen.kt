package com.example.tourny.ui.theme.screens.createLeague

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.List
import androidx.compose.material.icons.sharp.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.tourny.network.RegistretedUser
import com.example.tourny.ui.theme.TournyPurple
import com.example.tourny.ui.theme.components.TournyButton
import com.example.tourny.ui.theme.components.TournyOutlinedTextField
import kotlinx.coroutines.launch

@Composable
fun CreateLeagueScreen(navController: NavController){
    val scope = rememberCoroutineScope()
    val createLeagueViewModel: CreateLeagueViewModel = viewModel()
    var errorMessage: String? by remember { mutableStateOf<String?>(null) }
    var nameLeague: String by remember { mutableStateOf<String>("") }
    var basicScore: String by remember { mutableStateOf<String>("") }
    var typeLeague: String by remember { mutableStateOf<String>("ELO") }
    var idLeague: String by remember { mutableStateOf<String>("") }

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
                    text = "Создай лигу",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White
                )
            }
        },

        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TournyOutlinedTextField(
                    labelName = "Название лиги",
                    variableText = nameLeague,
                    changingVariableText = {newText -> nameLeague = newText},
                    keyboardType = KeyboardType.Text
                )

                Spacer(modifier = Modifier.padding(6.dp))

                TournyOutlinedTextField(
                    labelName = "Базовый рейтинг",
                    variableText = basicScore,
                    changingVariableText = {newText -> basicScore = newText},
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.padding(6.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = "Тип лиги",
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
                            checked = (typeLeague == "ELO"),
                            onCheckedChange = {typeLeague = "ELO"}
                        )

                        Text(
                            text = "ELO",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.padding(6.dp))

                    Row (
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                            .padding(6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Checkbox(
                            checked = (typeLeague == "MagicPoint"),
                            onCheckedChange = {typeLeague = "MagicPoint"}
                        )

                        Text(
                            text = "MagicPoint",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Black
                        )
                    }
                }

//                Button(
//                    onClick = { seeTypeLeague = !seeTypeLeague },
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.White,
//                        contentColor = Color.Black
//                    )
//                ) {
//                    Text(
//                        text = typeLeague,
//                        fontSize = 30.sp,
//                        fontWeight = FontWeight.Bold,
//                        style = MaterialTheme.typography.labelMedium,
//                        color = Color.Black
//                    )
//                }
//
//                Box(
//                    modifier = Modifier.width(200.dp)
//                ) {
//                    DropdownMenu(
//                        expanded = seeTypeLeague,
//                        onDismissRequest = { seeTypeLeague = false }
//                    ) {
//                        DropdownMenuItem(
//                            text = {
//                                Text(
//                                    text = "ELO",
//                                    fontSize = 20.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    style = MaterialTheme.typography.labelMedium
//                                )
//                            },
//                            onClick = { typeLeague = "Elo" }
//                        )
//
//                        DropdownMenuItem(
//                            text = {
//                                Text(
//                                    text = "MagicPoint",
//                                    fontSize = 20.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    style = MaterialTheme.typography.labelMedium
//                                )
//                            },
//                            onClick = { typeLeague = "MagicPoint" }
//                        )
//                    }
//
//                }
                
                Spacer(modifier = Modifier.padding(12.dp))

                TournyButton(text = "Создать лигу") {
                    if (!nameLeague.isNullOrEmpty() && !basicScore.isNullOrEmpty() && typeLeague != "Выбрать тип лиги"){
                        try {
                            scope.launch {
                                idLeague = createLeagueViewModel.createLeague(nameLeague, basicScore, typeLeague)
                                navController.navigate("Leagues/${idLeague}")
                            }
                        }
                        catch (e: Exception){
                            errorMessage = e.message
                        }
                    }
                    else{
                        errorMessage = "Либо отсуствует имя лиги, либо начальный рейтинг, либо не был выбран тип лиги"
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