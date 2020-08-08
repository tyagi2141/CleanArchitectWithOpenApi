package com.example.cleanarchitectwithopenapi.session

import android.app.Application
import com.example.cleanarchitectwithopenapi.persistence.AuthTokenDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rahul on 04/08/20.
 */

@Singleton
class Sessionmanager
@Inject
    constructor(
        val  authTokkenDao: AuthTokenDao,
        val application: Application
    ){
}