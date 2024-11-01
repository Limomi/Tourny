package com.example.tourny.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import com.example.tourny.models.Tournament
import com.example.tourny.network.api.ApiRepository
import kotlinx.coroutines.launch

@Composable
fun TournamentsScreen(){
    var tournaments by remember { mutableStateOf<List<Tournament>>(emptyList()) }
    var showLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()
    val apiRepository = ApiRepository()

    fun loadTournaments() {
        showLoading = true
        errorMessage = null

        scope.launch {
            try {
                val response = apiRepository.getTournaments()
                tournaments = response
            } catch (e: Exception){
                errorMessage = "Error loading tournaments ${e.message}"
            } finally {
                showLoading = false
            }
        }
    }

    LaunchedEffect(Unit) {
        loadTournaments()
    }

    Column(
    modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Tournaments",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if(showLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        errorMessage?.let { message ->
            Text(
                text = message,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(tournaments) {tournament ->
                TournamentItem(tournament = tournament)
            }
        }

        Button(onClick = { loadTournaments() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        ) {
            Text(text = "Reload")
        }
    }
}

@Composable
fun TournamentItem(tournament: Tournament){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = tournament.id.toString(), style = MaterialTheme.typography.headlineMedium)
            Text(text = tournament.name, style = MaterialTheme.typography.headlineMedium)
            Text(text = tournament.game, style = MaterialTheme.typography.headlineMedium)
        }

    }
}