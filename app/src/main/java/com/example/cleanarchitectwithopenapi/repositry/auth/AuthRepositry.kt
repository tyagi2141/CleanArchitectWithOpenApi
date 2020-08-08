package com.example.cleanarchitectwithopenapi.repositry.auth

import com.example.cleanarchitectwithopenapi.openapi.api.auth.OpenApiAuthService
import com.example.cleanarchitectwithopenapi.persistence.AccountPropertiesDao
import com.example.cleanarchitectwithopenapi.persistence.AuthTokenDao
import com.example.cleanarchitectwithopenapi.session.Sessionmanager
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
}