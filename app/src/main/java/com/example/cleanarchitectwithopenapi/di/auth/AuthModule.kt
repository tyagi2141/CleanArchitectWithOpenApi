package com.example.cleanarchitectwithopenapi.di.auth

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
    fun provideFakeApiService(): OpenApiAuthService {
        return Retrofit.Builder()
            .baseUrl("https://open-api.xyz")
            .build()
            .create(OpenApiAuthService::class.java)
    }

    @AuthScope
    @Provides
    fun provideAuthRepository(
        sessionManager: Sessionmanager,
        authTokenDao: AuthTokenDao,
        accountPropertiesDao: AccountPropertiesDao,
        openApiAuthService: OpenApiAuthService
    ): AuthRepositry {
        return AuthRepositry(
            authTokenDao,
            accountPropertiesDao,
            openApiAuthService,
            sessionManager
        )
    }

}