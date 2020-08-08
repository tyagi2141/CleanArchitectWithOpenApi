package com.example.cleanarchitectwithopenapi.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cleanarchitectwithopenapi.models.AuthToken

/**
 * Created by Rahul on 04/08/20.
 */

@Dao
interface AuthTokenDao {

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    fun insert(authToken: AuthToken):Long


    @Query("UPDATE auth_token SET token = null WHERE account_pk=:pk")
    fun nullfyitoken(pk:Int):Int
}