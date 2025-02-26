package com.example.tourny.ui.theme.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp

@Composable
fun TournyOutlinedTextField(
    labelName: String,
    variableText: String,
    changingVariableText: (String)->Unit,
    keyboardType: KeyboardType
){
    OutlinedTextField(
        value = variableText,
        onValueChange = changingVariableText,
        label ={
            Text(
                text = labelName,
                fontSize = 20.sp,
                style = MaterialTheme.typography.labelMedium
            )
        },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            unfocusedTextColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            unfocusedLabelColor =  Color.Black,
            focusedContainerColor = Color.White,
            focusedTextColor = Color(0xff222222),
            focusedBorderColor = Color(red = 72, green = 27, blue = 194),
            focusedLabelColor =  Color(red = 72, green = 27, blue = 194)
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
//                isError = login.isEmpty(),
    )
}