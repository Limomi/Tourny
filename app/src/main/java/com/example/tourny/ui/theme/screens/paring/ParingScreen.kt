package com.example.tourny.ui.theme.screens.paring

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.List
import androidx.compose.material.icons.sharp.Share
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
import com.example.tourny.models.onlyApp.ParingWithName
import com.example.tourny.network.RegistretedUser
import com.example.tourny.ui.theme.TournyPurple
import com.example.tourny.ui.theme.components.TournyButton
import com.example.tourny.ui.theme.components.TournyOutlinedTextField
import kotlinx.coroutines.launch

@Composable
fun ParingScreen(navController: NavController, paringId: String?){
    val scope = rememberCoroutineScope()
    val paringViewModel: ParingViewModel = viewModel()
    Log.e("Проблемка", "Вылет на паринг скрине")

    var paring:ParingWithName by remember { mutableStateOf(ParingWithName(0,0,0,"a","a","a","a","a","a","a","a","","",false))  }
    var firstUserScore:String by remember { mutableStateOf("0") }
    var secondUserScore:String by remember { mutableStateOf("0") }
    var errorMessage: String? by remember { mutableStateOf(null) }

    fun loadParing(){
        try {
            scope.launch {
                paring = paringViewModel.loadParing(paringId!!)
                firstUserScore = paring.firstUserScore
                secondUserScore= paring.secondUserScore
            }
        }
        catch (e:Exception){
            errorMessage = e.message
        }
    }

    LaunchedEffect(Unit) {
        loadParing()
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
                    text = "Паринг",
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
                      horizontalAlignment = Alignment.CenterHorizontally
                  ) {
                      Text(
                          text = "Код турнира: " + paring.tourId.toString(),
                          textAlign = TextAlign.Center,
                          fontSize = 22.sp,
                          fontWeight = FontWeight.Bold,
                          style = MaterialTheme.typography.labelMedium
                      )

                      Text(
                          text = "Тур: " + paring.tourId,
                          textAlign = TextAlign.Center,
                          fontSize = 22.sp,
                          fontWeight = FontWeight.Bold,
                          style = MaterialTheme.typography.labelMedium
                      )

                      errorMessage?.let { message->
                          Text(
                              text = message,
                              textAlign = TextAlign.Center,
                              fontSize = 22.sp,
                              fontWeight = FontWeight.Bold,
                              style = MaterialTheme.typography.labelMedium,
                              color = Color.Red
                          )
                      }

                      Spacer(modifier = Modifier.padding(8.dp))

                      Text(
                          text = paring.firstUserLastname + " " + paring.firstUserFirstname[0] + ". " + paring.firstUserPatronymic!![0],
                          textAlign = TextAlign.Center,
                          fontSize = 22.sp,
                          fontWeight = FontWeight.Bold,
                          style = MaterialTheme.typography.labelMedium
                      )
                      
                      TournyOutlinedTextField(
                          labelName = "Кол очков",
                          variableText = firstUserScore,
                          changingVariableText = {newText -> firstUserScore = newText},
                          keyboardType = KeyboardType.Number
                      )

                      Spacer(modifier = Modifier.padding(6.dp))

                      Text(
                          text = paring.secondUserLastname + " " + paring.secondUserFirstname[0] + ". " + paring.secondUserPatronymic!![0],
                          textAlign = TextAlign.Center,
                          fontSize = 22.sp,
                          fontWeight = FontWeight.Bold,
                          style = MaterialTheme.typography.labelMedium
                      )

                      TournyOutlinedTextField(
                          labelName = "Кол очков",
                          variableText = secondUserScore,
                          changingVariableText = {newText -> secondUserScore = newText},
                          keyboardType = KeyboardType.Number
                      )

                      Spacer(modifier = Modifier.padding(8.dp))

                      TournyButton(
                          text = "Внести результат") {
                            scope.launch {
                                try {
                                    paringViewModel.uploadScore(paring, firstUserScore, secondUserScore)
                                    navController.popBackStack()
                                }
                                catch (e:Exception){
                                    e.message
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

