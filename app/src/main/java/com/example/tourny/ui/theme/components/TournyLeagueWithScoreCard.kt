package com.example.tourny.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourny.models.onlyApp.LeagueWithScore
import com.example.tourny.ui.theme.TournyPurple

@Composable
fun TournyLeagueWithScoreCard(leagueWithScore: LeagueWithScore){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(2.dp, TournyPurple, shape = RoundedCornerShape(6.dp))
            .background(Color.White),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column {
                Text(
                    text = leagueWithScore.leagueId.toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.labelMedium
                )

                Text(
                    text = leagueWithScore.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Text(
                text = leagueWithScore.score.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.End
            )
        }
    }
}