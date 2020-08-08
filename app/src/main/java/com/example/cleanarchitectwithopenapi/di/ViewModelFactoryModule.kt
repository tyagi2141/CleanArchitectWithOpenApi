package com.example.cleanarchitectwithopenapi.di

import androidx.lifecycle.ViewModelProvider
import com.example.cleanarchitectwithopenapi.di.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

/**
 * Created by Rahul on 08/08/20.
 */
@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}