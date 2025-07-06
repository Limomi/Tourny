package com.example.tourny.ui.theme.screens.entry

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tourny.R
import com.example.tourny.ui.theme.TournyGray
import com.example.tourny.ui.theme.TournyPurple
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun EntryScreen(navController: NavHostController){
    var login by remember { mutableStateOf("") }
    var password:String by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

//    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    val scope = rememberCoroutineScope()
    val entryViewModel: EntryViewModel = viewModel()
//    val apiRepository = ApiRepository()
    
    var loggingIssue by remember { mutableStateOf("") }

//  Спросить штуку

//    fun EnterLog()
//    {
//        scope.launch {
//            if (entryViewModel.EnterLogUser()){
//                navController.navigate(route = "Tournaments")
//            }
//        }
//    }

//    EnterLog()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
    contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment =Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Вход",
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(modifier = Modifier.padding(16.dp))

            OutlinedTextField(
                value = login,
                onValueChange ={
                    newText -> login = newText
                },
                label ={
                    Text(
                        text = "Код",
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//                isError = login.isEmpty(),
            )

            Spacer(modifier = Modifier.padding(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange ={
                        newText -> password = newText
                },
                label ={
                    Text(
                        text = "Пароль",
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
                    focusedBorderColor = TournyPurple,
                    focusedLabelColor =  TournyPurple
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if(passwordVisible)
                {
                    VisualTransformation.None
                }else
                {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = if(passwordVisible) {
                            Icons.Filled.Visibility
                        }
                        else
                        {
//                            Icons.Filled.VisibilityOff
                        Icons.Filled.VisibilityOff
                        },
                            contentDescription = "")

                    }
                }

//                isError = login.isEmpty(),
            )

            Spacer(modifier = Modifier.padding(6.dp))

            Text(
                text = loggingIssue,
                fontSize = 20.sp,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Red
            )

            Spacer(modifier = Modifier.padding(6.dp))

            Button(
                onClick = {
                    scope.launch {
                        loggingIssue = entryViewModel.LoggingInAccount(login, password)
                        if (loggingIssue.isNullOrEmpty()){
                            navController.navigate(route = "Tournaments")
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = TournyPurple),
                modifier = Modifier.size(270.dp, 60.dp)

            ) {
                Text(
                    text = "Войти",
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Button(
                onClick = { navController.navigate(route = "Registreted") },
                colors = ButtonDefaults.buttonColors(containerColor = TournyGray),
                modifier = Modifier.size(270.dp, 60.dp)

            ) {
                Text(
                    text = "Зарегистрироваться",
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))

        }
    }
}

