package com.example.tourny.ui.theme.screens.entry

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tourny.models.User
import com.example.tourny.network.api.ApiRepository
import com.example.tourny.network.RegistretedUser

class EntryViewModel: ViewModel() {
    suspend fun LoggingInAccount(Login: String, Password: String):String{
        val apiRepository = ApiRepository()
        var users: List<User> = emptyList()
        try {
            users = apiRepository.getUsers()
            users.forEach{
                if(it.id ==Login && it.password == Password){
                    RegistretedUser.id = it.id
                    RegistretedUser.lastname = it.lastname
                    RegistretedUser.firstname = it.firstname
                    RegistretedUser.patronymic = it.patronymic
                    RegistretedUser.password = it.password
                    return("")
                }
            }
            return ("Нету аккаунта с таким кодом и паролем")
        }
        catch (e: Exception){
            Log.e("E", e.message.toString())
            return ("Не получилось войти на аккаунт ошибка: ${e.message}")
        }
    }

//    suspend fun EnterLogUser(): Boolean{
//        if (RegistretedUser.id != null && LoggingInAccount(RegistretedUser.id, RegistretedUser.password) == ""){
//            return (true)
//        }
//        else{
//            return (false)
//        }
//    }

}