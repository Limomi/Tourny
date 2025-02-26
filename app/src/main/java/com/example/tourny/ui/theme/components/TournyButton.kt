package com.example.tourny.ui.theme.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourny.ui.theme.TournyPurple

@Composable
fun TournyButton(
    text: String,
    onClick:()->Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = TournyPurple),
        modifier = Modifier
            .size(270.dp, 60.dp),
    ) {
        Text(
            text = text,
            fontSize = 20.sp
        )
    }
}