package com.example.cleanarchitectwithopenapi.repositry.auth

import androidx.lifecycle.LiveData
import com.example.cleanarchitectwithopenapi.di.auth.network_responses.LoginResponse
import com.example.cleanarchitectwithopenapi.di.auth.network_responses.RegistrationResponse
import com.example.cleanarchitectwithopenapi.openapi.api.auth.OpenApiAuthService
import com.example.cleanarchitectwithopenapi.persistence.AccountPropertiesDao
import com.example.cleanarchitectwithopenapi.persistence.AuthTokenDao
import com.example.cleanarchitectwithopenapi.session.Sessionmanager
import com.example.cleanarchitectwithopenapi.util.GenericApiResponse
import javax.inject.Inject

/**
 * Created by Rahul on 04/08/20.
 */
class AuthRepositry

@Inject
constructor(
    val authTokkenDao: AuthTokenDao,
    val accountProppertiesDao: AccountPropertiesDao,
    val openApiAuthService: OpenApiAuthService,
    val sessionmanager: Sessionmanager


) {


    fun Login(username:String,password:String):LiveData<GenericApiResponse<LoginResponse>>{
        return openApiAuthService.login(username,password)
    }

    fun registration(email: String,username:String,password: String,confirmPassword:String):LiveData<GenericApiResponse<RegistrationResponse>>{

        return openApiAuthService.register(email,username,password,confirmPassword)
    }
}