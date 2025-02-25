package com.example.tourny.ui.theme.screens.profile

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.List
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tourny.models.User
import com.example.tourny.network.RegistretedUser
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController){

    val scope = rememberCoroutineScope()
    val profileViewModel: ProfileViewModel = viewModel()
    var userInfo by remember { mutableStateOf<User>(User("","","","","")) }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun loadUserInfo(){
        errorMessage = null

        scope.launch{
            try {
                userInfo = profileViewModel.GetUserInfo(RegistretedUser.id)
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
        content = {
            Column (
                modifier = Modifier.padding(it)
            ) {
                errorMessage?.let { message ->
                    Text(
                        text = message,
                        color = Color.Red,
                        modifier = Modifier.padding(8.dp)
                    )
                    Log.e("e", message)
                }

                userInfo.let {it->

                    Text(
                        text = it.id
                    )

                    if(userInfo.patronymic?.isNullOrEmpty() == true){
                        Row {
                            Text(
                                text = it.firstname
                            )

                            Text(
                                text = it.lastname
                            )
                        }
                    }
                    else{
                        Row {
                            Text(text = it.lastname)
                            Text(text = it.firstname[0] + ".")
                            Text(text = it.patronymic!!.substring(0,0) + ".")
                        }
                    }
                }
            }
        },

        bottomBar = {
            NavigationBar {
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
                            text = "Присойдениться"
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
                    onClick = { navController.navigate("Home") },
                    icon = {
                        Icon(
                            imageVector = Icons.Sharp.Add,
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