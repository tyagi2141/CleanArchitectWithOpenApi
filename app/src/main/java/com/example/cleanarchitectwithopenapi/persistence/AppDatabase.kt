package com.example.cleanarchitectwithopenapi.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cleanarchitectwithopenapi.models.AccountProperties
import com.example.cleanarchitectwithopenapi.models.AuthToken

/**
 * Created by Rahul on 04/08/20.
 */

@Database(entities = [AuthToken::class,AccountProperties::class],version = 1)
abstract class AppDatabase:RoomDatabase() {

    abstract fun getAuthTokenDao():AuthTokenDao
    abstract fun getAccountPropertiesDao():AccountPropertiesDao

    companion object{
        const val DATABASE_NAME="app_db"
    }
}