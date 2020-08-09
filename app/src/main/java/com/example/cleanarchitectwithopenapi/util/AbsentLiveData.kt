package com.example.cleanarchitectwithopenapi.util

import androidx.lifecycle.LiveData

/**
 * Created by Rahul on 09/08/20.
 */
class AbsentLiveData<T:Any?> private constructor():LiveData<T>() {

    init {
        postValue(null)
    }

    companion object{
        fun <T> create():LiveData<T>{
            return AbsentLiveData()
        }
    }
}