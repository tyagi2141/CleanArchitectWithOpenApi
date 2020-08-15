package com.example.cleanarchitectwithopenapi.di.auth

import android.content.SharedPreferences
import com.example.cleanarchitectwithopenapi.openapi.api.auth.OpenApiAuthService
import com.example.cleanarchitectwithopenapi.persistence.AccountPropertiesDao
import com.example.cleanarchitectwithopenapi.persistence.AuthTokenDao
import com.example.cleanarchitectwithopenapi.repositry.auth.AuthRepositry
import com.example.cleanarchitectwithopenapi.session.Sessionmanager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by Rahul on 07/08/20.
 */
@Module
class AuthModule{

    // TEMPORARY
    @AuthScope
    @Provides
    fun provideFakeApiService(retrofitbuilder:Retrofit.Builder): OpenApiAuthService {
        return retrofitbuilder
            .build()
            .create(OpenApiAuthService::class.java)
    }

    @AuthScope
    @Provides
    fun provideAuthRepository(
        sessionManager: Sessionmanager,
        authTokenDao: AuthTokenDao,
        accountPropertiesDao: AccountPropertiesDao,
        openApiAuthService: OpenApiAuthService,
        sharedPreferences: SharedPreferences,
        editor: SharedPreferences.Editor
    ): AuthRepositry {
        return AuthRepositry(
            authTokenDao,
            accountPropertiesDao,
            openApiAuthService,
            sessionManager,
            sharedPreferences,
            editor
        )
    }

}