package com.example.cleanarchitectwithopenapi.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cleanarchitectwithopenapi.di.auth.network_responses.LoginResponse
import com.example.cleanarchitectwithopenapi.di.auth.network_responses.RegistrationResponse
import com.example.cleanarchitectwithopenapi.repositry.auth.AuthRepositry
import com.example.cleanarchitectwithopenapi.util.GenericApiResponse
import javax.inject.Inject

/**
 * Created by Rahul on 04/08/20.
 */
class AuthViewModel
@Inject
constructor(
    val authRepositry: AuthRepositry
) : ViewModel() {

    fun Login(username: String, password: String): LiveData<GenericApiResponse<LoginResponse>> {
        return authRepositry.Login(username, password)
    }

    fun Regisration(
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): LiveData<GenericApiResponse<RegistrationResponse>> {

        return authRepositry.registration(email, username, password, confirmPassword)
    }
}