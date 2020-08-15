package com.example.cleanarchitectwithopenapi.ui

interface DataStateChangeListener{

    fun onDataStateChange(dataState: DataState<*>?)
}