package com.example.cleanarchitectwithopenapi.di

import com.example.cleanarchitectwithopenapi.di.auth.fragmentmodule.AuthFragmentBuilderModule
import com.example.cleanarchitectwithopenapi.di.auth.AuthModule
import com.example.cleanarchitectwithopenapi.di.auth.AuthScope
import com.example.cleanarchitectwithopenapi.di.auth.viewmodelmodule.AuthViewModelModule
import com.example.cleanarchitectwithopenapi.ui.auth.AuthActivity
import com.example.cleanarchitectwithopenapi.ui.main.MainActivity
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

    @ContributesAndroidInjector
    abstract fun contributMainActivity():MainActivity
}