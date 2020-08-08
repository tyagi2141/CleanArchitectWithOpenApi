package com.example.cleanarchitectwithopenapi.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Rahul on 04/08/20.
 */
@Entity(tableName = "auth_token",foreignKeys = [
ForeignKey(entity = AccountProperties::class,
parentColumns = ["pk"],
childColumns = ["account_pk"],
onDelete = CASCADE)
])
data class AuthToken(

    @PrimaryKey
    @ColumnInfo(name = "account_pk")
    var account_pk:Int?=-1,

@SerializedName("token")
@ColumnInfo(name = "token")
var token:String?=null
)