package com.example.tourny.ui.theme.screens.registreted

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tourny.ui.theme.TournyGray
import com.example.tourny.ui.theme.TournyPurple
import kotlinx.coroutines.launch

@Composable
fun RegistretedScreen(navController: NavHostController){
    val registretedViewModel: RegistretedViewModel = viewModel()
    val scope = rememberCoroutineScope()

    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var patronymic by remember { mutableStateOf("") }
    var firstVersionPassword by remember { mutableStateOf("") }
    var secondVersionPassword by remember { mutableStateOf("") }

    var registretionIssue by remember { mutableStateOf("") }

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
                text = "Регистрация",
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(modifier = Modifier.padding(6.dp))

            OutlinedTextField(
                value = lastname,
                onValueChange ={
                        newText -> lastname = newText
                },
                label ={
                    Text(
                        text = "Фамилия",
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
                )
//                isError = login.isEmpty(),
            )

            Spacer(modifier = Modifier.padding(8.dp))

            OutlinedTextField(
                value = firstname,
                onValueChange ={
                        newText -> firstname = newText
                },
                label ={
                    Text(
                        text = "Имя",
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
                )
//                isError = login.isEmpty(),
            )

            Spacer(modifier = Modifier.padding(8.dp))

            OutlinedTextField(
                value = patronymic,
                onValueChange ={
                        newText -> patronymic = newText
                },
                label ={
                    Text(
                        text = "Отчество",
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
                )
//                isError = login.isEmpty(),
            )

            Spacer(modifier = Modifier.padding(8.dp))

            OutlinedTextField(
                value = firstVersionPassword,
                onValueChange ={
                        newText -> firstVersionPassword = newText
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
                )
//                isError = login.isEmpty(),
            )

            Spacer(modifier = Modifier.padding(8.dp))

            OutlinedTextField(
                value = secondVersionPassword,
                onValueChange ={
                        newText -> secondVersionPassword = newText
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
                )
//                isError = login.isEmpty(),
            )

            Spacer(modifier = Modifier.padding(2.dp))

            Text(
                text = registretionIssue,
                fontSize = 20.sp,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Red
            )

            Spacer(modifier = Modifier.padding(2.dp))

            Button(
                onClick = {
                    if(firstname != "" && lastname != "" && patronymic != ""){
                        scope.launch {
//                        loggingIssue = entryViewModel.LoggingInAccaunt(login, password)
                            registretionIssue  = registretedViewModel.registretionUser(lastname, firstname, patronymic, firstVersionPassword, secondVersionPassword)

                            if (registretionIssue.isNullOrEmpty()){
                                navController.navigate(route = "Tournaments")
                            }
                        }
                    }
                    else{
                        registretionIssue = "ФИО не заполнено"
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = TournyPurple),
                modifier = Modifier.size(270.dp, 60.dp)

            ) {
                Text(
                    text = "Зарегистрироваться",
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Button(
                onClick = { navController.navigate(route = "Entry") },
                colors = ButtonDefaults.buttonColors(containerColor = TournyGray),
                modifier = Modifier.size(270.dp, 60.dp)

            ) {
                Text(
                    text = "Уже есть аккаунт",
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}