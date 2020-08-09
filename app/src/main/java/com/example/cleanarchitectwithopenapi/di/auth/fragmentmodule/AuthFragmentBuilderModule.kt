package com.example.cleanarchitectwithopenapi.di.auth.fragmentmodule

import com.example.cleanarchitectwithopenapi.ui.auth.ForgotPasswordFragment
import com.example.cleanarchitectwithopenapi.ui.auth.LauncherFragment
import com.example.cleanarchitectwithopenapi.ui.auth.LoginFragment
import com.example.cleanarchitectwithopenapi.ui.auth.RegisterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Rahul on 07/08/20.
 */

@Module
abstract class AuthFragmentBuilderModule {

    @ContributesAndroidInjector()
    abstract fun ContributesLaunchFragment(): LauncherFragment

    @ContributesAndroidInjector()
    abstract fun ContributesLoginFragment(): LoginFragment

    @ContributesAndroidInjector()
    abstract fun ContributeRegisterationFragment(): RegisterFragment

    @ContributesAndroidInjector
    abstract fun ContributerForgontPassswword(): ForgotPasswordFragment


}