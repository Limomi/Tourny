package com.example.tourny.ui.theme.screens.tournament

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.KeyboardArrowUp
import androidx.compose.material.icons.sharp.List
import androidx.compose.material.icons.sharp.Refresh
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material.icons.sharp.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tourny.models.Tournament
import com.example.tourny.models.onlyApp.ParingWithName
import com.example.tourny.models.onlyApp.TourForApp
import com.example.tourny.models.onlyApp.UserWithScore
import com.example.tourny.network.RegistretedUser
import com.example.tourny.ui.theme.TournyGray
import com.example.tourny.ui.theme.TournyPurple
import com.example.tourny.ui.theme.components.TournyButton
import kotlinx.coroutines.launch

@Composable
fun TournamentScreen(navController: NavController, tournamentId: String?){
    val scope = rememberCoroutineScope()
    val tournamentViewModel: TournamentViewModel = viewModel()
    var errorMessage: String? by remember { mutableStateOf<String?>(null) }

    var tournament: Tournament by remember { mutableStateOf<Tournament>(Tournament("000", tournamentId!!.toInt(), "", "", 0, true, "")) }
    var userWithScore: List<UserWithScore> by remember { mutableStateOf<List<UserWithScore>>(emptyList()) }
    var tourForApp: List<TourForApp> by remember { mutableStateOf<List<TourForApp>>(emptyList()) }
    var paringsClosed: Boolean by remember { mutableStateOf(false) }

//    var paringScore: MutableList<MutableList<MutableList<String>>> by remember { mutableStateOf(mutableListOf()) }
//    var i: Int = 1

    fun loadTournament(){
        scope.launch {
            try {
                tournament = tournamentViewModel.loadTournamentInfo(tournamentId!!.toInt())
                userWithScore = tournamentViewModel.loadAllPlayers(tournamentId.toInt())
                tourForApp = tournamentViewModel.loadParings(tournamentId.toInt())

                paringsClosed = tournamentViewModel.isClosedTour(tourForApp[0].parings)
            }
            catch (e: Exception){
                errorMessage = e.message
                Log.e("Error", errorMessage!!)
            }
        }
    }

    LaunchedEffect(Unit) {
        loadTournament()
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
                    text = "Турнир",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White
                )

                Button(
                    modifier = Modifier.padding(6.dp),
                    onClick = { loadTournament() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TournyPurple,
                        contentColor = Color.White
                    )

                ) {
                    Icon(
                        imageVector =Icons.Sharp.Refresh,
                        contentDescription = ""
                    )
                }
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
                    Spacer(modifier = Modifier.padding(4.dp))

                    Text(
                        text = tournament.id.toString(),
                        textAlign = TextAlign.Center,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium,
                        lineHeight = 35.sp
                    )
                }

                item {
                    Spacer(modifier = Modifier.padding(6.dp))
                    Text(
                        text = tournament.name,
                        textAlign = TextAlign.Center,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium,
                        lineHeight = 35.sp
                    )
                }

                item {
                    if (tournament.adminId == RegistretedUser.id){
                        Spacer(modifier = Modifier.padding(6.dp))
                        Text(
                            text = tournament.joinCode,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelMedium,
                            lineHeight = 35.sp,
                            color = TournyPurple
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.padding(8.dp))
                    if (!errorMessage.isNullOrEmpty()){
                        Text(text = errorMessage!!)
                    }
                }

                items(userWithScore){user->
                    Divider(
                        color = TournyGray,
                        thickness = 2.dp
                    )
                    UserInTournamentTableElement(user, navController, tournament.adminId)
                }

                item{
                    Divider(
                        color = TournyGray,
                        thickness = 2.dp
                    )

                    Spacer(
                        modifier = Modifier.padding(4.dp)
                    )
                }

//                end headers
//                i = 1

                items(tourForApp){ tour->
                    Spacer(modifier = Modifier.padding(6.dp))
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .background(Color.White),) {
                        Column(
                            modifier = Modifier
                                .background(Color.White),
                            horizontalAlignment =Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Раунд: " + tour.name,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Black,
                                modifier = Modifier.background(Color.White)
                            )
                            Spacer(modifier = Modifier.padding(12.dp))
                            tour.parings.forEach { paring ->
                                if(!tour.closed && (tournament.adminId == RegistretedUser.id || paring.firstUserId == RegistretedUser.id || paring.secondUserId == RegistretedUser.id)){
                                    ParingCardChanges(
                                        paring,
                                        navController
                                    )
                                }
                                else{
                                    ParingCardNoChange(paring)
                                }
                            }
                            Spacer(modifier = Modifier.padding(6.dp))
                        }
                    }
                }

                if(tournament.adminId ==RegistretedUser.id){

                    item {
                        Spacer(modifier = Modifier.padding(30.dp))
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
        floatingActionButton ={
            if (tournament.adminId ==RegistretedUser.id){
                if(tourForApp.isNotEmpty()){
                    if (tourForApp[0].closed){
                        if(tournament.roundCount>tourForApp.size){
                            TournyButton("Начать тур") {
                                scope.launch{
                                    tournamentViewModel.makeParings(tournament.id, (tourForApp.size + 1).toString())
                                    loadTournament()
                                }
                            }
                        }
                        else{
                            if(!tournament.closedTournament){
                                TournyButton(text = "Закончить турнир") {
                                    scope.launch {
                                        tournamentViewModel.closeTournament(tournament.id)
                                        loadTournament()
                                    }
                                }
                            }
                        }
                    }
                    else{
                        if (paringsClosed){
                            TournyButton(
                                "Закончить тур",
                                {
                                    scope.launch {
                                        tournamentViewModel.closeTour(tourForApp[0], tournament, tourForApp[0].parings)
                                        loadTournament()
                                    }
                                }
                            )
                        }
                    }
                }
                else{
                    TournyButton(text = "Начать турнир") {
                        scope.launch {
                            tournamentViewModel.startTournament(tournament)
                            loadTournament()
                        }
                    }
                }
            }

        }
    )


}

@Composable
private fun UserInTournamentTableElement(user: UserWithScore, navController: NavController, adminId:String){
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
        ),
        onClick = { navController.navigate("profile/${user.id}") },
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column {
                Text(
                    text = user.id,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Black
                )
                Text(
                    text = user.lastname + " " + user.firstname[0] + ". " + user.patronymic!![0] + ".",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.labelMedium,
                    color = if(RegistretedUser.id== user.id)
                    {
                        TournyPurple
                    }
                    else
                    {
                        if(adminId == user.id)
                        {
                            TournyGray
                        }else{
                            Color.Black
                        }
                    }
                )
            }

            Text(
                text = user.score.toString(),
                fontSize = 26.sp,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )
        }
    }
}

@Composable
fun ParingCardChanges(
    paringForView: ParingWithName,
    navController: NavController
){
    Row (
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column(
            modifier = Modifier.size(130.dp, 150.dp)
        ) {
            Text(
                text = paringForView.firstUserId,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )

            Text(
                text = paringForView.firstUserLastname + " " + paringForView.firstUserFirstname[0] + ". " + paringForView.firstUserPatronymic!![0] + ".",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.padding(6.dp))

            Text(
                text = paringForView.firstUserScore,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )

        }

        Column(
            horizontalAlignment =Alignment.CenterHorizontally
        ) {
            Text(
                text = paringForView.numberInTour.toString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.padding(8.dp))
            
            Button(
                onClick = {
                    navController.navigate("Paring/${paringForView.id.toString()}")
                },
                colors = ButtonDefaults.buttonColors(containerColor = TournyPurple),
                modifier = Modifier
                    .size(120.dp, 60.dp),
            ) {
                Text(
                    text = "Внести",
                    fontSize = 20.sp
                )
            }
        }

        Column(
            horizontalAlignment =Alignment.End,
            modifier = Modifier.size(130.dp, 150.dp)
        ) {
            Text(
                text = paringForView.secondUserId,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )

            Text(
                text = paringForView.secondUserLastname + " " + paringForView.secondUserFirstname[0] + ". " + paringForView.secondUserPatronymic!![0] + ".",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.padding(6.dp))

            Text(
                text = paringForView.secondUserScore,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun ParingCardNoChange(paring: ParingWithName){
    Row (
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column(
            modifier = Modifier.size(130.dp, 150.dp)
        ) {
            Text(
                text = paring.firstUserId,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )

            Text(
                text = paring.firstUserLastname + " " + paring.firstUserFirstname[0] + ". " + paring.firstUserPatronymic!![0] + ".",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )

            Text(
                text = paring.firstUserScore,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )
        }

        Column(
            horizontalAlignment =Alignment.CenterHorizontally
        ) {
            Text(
                text = paring.numberInTour.toString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )
        }

        Column(
            modifier = Modifier.size(130.dp, 150.dp),
            horizontalAlignment =Alignment.End
        ) {
            Text(
                text = paring.secondUserId,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )

            Text(
                text = paring.secondUserLastname + " " + paring.secondUserFirstname[0] + ". " + paring.secondUserPatronymic!![0] + ".",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )

            Text(
                text = paring.secondUserScore,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )
        }
    }
}