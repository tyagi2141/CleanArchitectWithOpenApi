package com.example.cleanarchitectwithopenapi.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cleanarchitectwithopenapi.models.AccountProperties

/**
 * Created by Rahul on 04/08/20.
 */

@Dao
interface AccountPropertiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndReplace(accountProperties: AccountProperties):Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun inserAndIgnor(accountProperties: AccountProperties):Long

    @Query("SELECT * FROM account_properties Where pk=:pk")
    fun searchBypkId(pk:Int):AccountProperties?

    @Query("SELECT * FROM account_properties Where email=:email")
    fun searchByEmail(email:String):AccountProperties?
}