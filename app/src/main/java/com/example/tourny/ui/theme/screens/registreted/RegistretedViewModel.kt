package com.example.tourny.ui.theme.screens.registreted

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tourny.models.User
import com.example.tourny.network.RegistretedUser
import com.example.tourny.network.api.ApiRepository

class RegistretedViewModel: ViewModel() {

    suspend fun registretionUser(
        lastname:String,
        firstname: String,
        patronymic: String,
        firstPassword: String,
        secondPassword: String): String{
        val apiRepository = ApiRepository()
        var users: List<User> = emptyList()
        try {
            if(firstPassword == secondPassword){
                users = apiRepository.getUsers()
                var id = ((users[users.size-1].id.toInt()) + 1)
                var id_user = id.toString()

                while (id_user.count()<6){
                    Log.e("error", id_user)
                    id_user = "0" + id_user
                }

                apiRepository.putUser(id, id_user, lastname, firstname, patronymic, firstPassword)
                RegistretedUser.id = id_user
                RegistretedUser.lastname = lastname
                RegistretedUser.firstname = firstname
                RegistretedUser.patronymic = patronymic
                RegistretedUser.password = firstPassword
                Log.e("error", id_user)
                return ""
            }
            else{
                return ("Пароль не совпадает")
            }
        }
        catch (e: Exception){
            return ("Не получилось создать на аккаунт ошибка: ${e.message}")
        }
    }
}