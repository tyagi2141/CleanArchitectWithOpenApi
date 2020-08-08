package com.example.cleanarchitectwithopenapi.di

import androidx.lifecycle.ViewModelProvider
import com.example.cleanarchitectwithopenapi.di.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory


}








