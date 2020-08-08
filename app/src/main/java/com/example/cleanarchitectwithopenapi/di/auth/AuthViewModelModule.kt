package com.example.cleanarchitectwithopenapi.di.auth

import androidx.lifecycle.ViewModel
import com.codingwithmitch.openapi.di.auth.keys.ViewModelKey
import com.example.cleanarchitectwithopenapi.ui.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Rahul on 07/08/20.
 */
@Module
abstract class AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: AuthViewModel): ViewModel

}