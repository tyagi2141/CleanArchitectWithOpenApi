package com.example.cleanarchitectwithopenapi.di

import com.example.cleanarchitectwithopenapi.di.auth.AuthFragmentBuilderModule
import com.example.cleanarchitectwithopenapi.di.auth.AuthModule
import com.example.cleanarchitectwithopenapi.di.auth.AuthScope
import com.example.cleanarchitectwithopenapi.di.auth.AuthViewModelModule
import com.example.cleanarchitectwithopenapi.ui.auth.AuthActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Rahul on 07/08/20.
 */
@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
        modules = [AuthModule::class, AuthFragmentBuilderModule::class, AuthViewModelModule::class]
    )
    abstract fun contributeAuthActivity(): AuthActivity

}