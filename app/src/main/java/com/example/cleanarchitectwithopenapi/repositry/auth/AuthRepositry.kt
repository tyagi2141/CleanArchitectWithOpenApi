package com.example.cleanarchitectwithopenapi.repositry.auth

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.cleanarchitectwithopenapi.di.auth.network_responses.LoginResponse
import com.example.cleanarchitectwithopenapi.di.auth.network_responses.RegistrationResponse
import com.example.cleanarchitectwithopenapi.models.AccountProperties
import com.example.cleanarchitectwithopenapi.models.AuthToken
import com.example.cleanarchitectwithopenapi.openapi.api.auth.OpenApiAuthService
import com.example.cleanarchitectwithopenapi.persistence.AccountPropertiesDao
import com.example.cleanarchitectwithopenapi.persistence.AuthTokenDao
import com.example.cleanarchitectwithopenapi.repositry.NetworkBoundResource
import com.example.cleanarchitectwithopenapi.session.Sessionmanager
import com.example.cleanarchitectwithopenapi.ui.DataState
import com.example.cleanarchitectwithopenapi.ui.Response
import com.example.cleanarchitectwithopenapi.ui.ResponseType
import com.example.cleanarchitectwithopenapi.ui.auth.state.AuthViewState
import com.example.cleanarchitectwithopenapi.ui.auth.state.LoginFields
import com.example.cleanarchitectwithopenapi.ui.auth.state.RegistrationField
import com.example.cleanarchitectwithopenapi.util.AbsentLiveData
import com.example.cleanarchitectwithopenapi.util.ApiSuccessResponse
import com.example.cleanarchitectwithopenapi.util.ErrorHandling.Companion.ERROR_SAVE_AUTH_TOKEN
import com.example.cleanarchitectwithopenapi.util.ErrorHandling.Companion.GENERIC_AUTH_ERROR
import com.example.cleanarchitectwithopenapi.util.GenericApiResponse
import com.example.cleanarchitectwithopenapi.util.PreferenceKeys.Companion.PREVIOUS_AUTH_USER
import com.example.cleanarchitectwithopenapi.util.SuccessHandling.Companion.RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE
import kotlinx.coroutines.Job
import javax.inject.Inject

/**
 * Created by Rahul on 04/08/20.
 */
class AuthRepositry
@Inject
constructor(
    val authTokkenDao: AuthTokenDao,
    val accountProppertiesDao: AccountPropertiesDao,
    val openApiAuthService: OpenApiAuthService,
    val sessionmanager: Sessionmanager,
    val sharedPreferences: SharedPreferences,
    val sharedPreferencesEditor: SharedPreferences.Editor


) {
    val TAG = "AuthRepositry"
    var repositryJon: Job? = null

    //Todo LOGIN API REPOSITRY
    fun attempLogin(username: String, password: String): LiveData<DataState<AuthViewState>> {

        val loginFieldError = LoginFields(username, password).isValidForLogin()
        if (!loginFieldError.equals(LoginFields.LoginError.none())) {
            return returnErrorResponse(loginFieldError, ResponseType.Dialog())
        }


        return object : NetworkBoundResource<LoginResponse, AuthViewState>(
            sessionmanager.isConnectedToInternet(),
            true
        ) {
            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<LoginResponse>) {
                Log.e(TAG, "handelApiSuccessResponse: {$response }")
                if (response.body.response.equals(GENERIC_AUTH_ERROR)) {
                    return onErrorReturn(response.body.errorMessage, true, false)
                }
                accountProppertiesDao.insertAndReplace(
                    AccountProperties(
                        response.body.pk,
                        response.body.email, ""
                    )
                )

                val result = authTokkenDao.insert(
                    AuthToken(
                        response.body.pk,
                        response.body.token
                    )
                )

                if (result < 0) {
                    return onCompleteJob(
                        DataState.error(
                            Response(
                                ERROR_SAVE_AUTH_TOKEN, ResponseType.Dialog()
                            )
                        )
                    )
                }

                saveAuthenticateEmailToPref(username)
                onCompleteJob(
                    DataState.data(
                        data = AuthViewState(
                            authTokken = AuthToken(response.body.pk, response.body.token)
                        )
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<LoginResponse>> {
                Log.e(TAG, "createCall: {$username $password}")
                return openApiAuthService.login(username, password)
            }

            override fun setJob(job: Job) {
                repositryJon?.cancel()
                repositryJon = job
            }

            override suspend fun createCacheRequestAndReturn() {

            }

        }.asLiveData()
    }

    //TODO ON ERROR IN API
    private fun returnErrorResponse(
        errorMessage: String,
        responseType: ResponseType.Dialog
    ): LiveData<DataState<AuthViewState>> {
        return object : LiveData<DataState<AuthViewState>>() {
            override fun onActive() {
                super.onActive()
                value = DataState.error(
                    response = Response(
                        message = errorMessage,
                        responseType = responseType
                    )
                )
            }
        }
    }

    //TODO CANCEL JOB
    fun cancelActiveJob() {
        Log.e(TAG, "cancelActiveJob: canceling on going job.....")
        repositryJon?.cancel()
    }

    //TODO REGISTRATION
    fun registration(
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): LiveData<DataState<AuthViewState>> {
        val registrationField =
            RegistrationField(email, username, password, confirmPassword).isValidForregistrartion()
        if (!registrationField.equals(RegistrationField.RegistrationError.Nono())) {
            return returnErrorResponse(registrationField, ResponseType.Dialog())
        }
        return object : NetworkBoundResource<RegistrationResponse, AuthViewState>(
            sessionmanager.isConnectedToInternet(), true
        ) {
            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<RegistrationResponse>) {
                Log.d(TAG, "handelApiSuccessResponse: {$response}")
                if (response.body.response.equals(GENERIC_AUTH_ERROR)) {
                    onErrorReturn(response.body.errorMessage, true, false)
                }
                accountProppertiesDao.insertAndReplace(
                    AccountProperties(
                        response.body.pk,
                        response.body.email, ""
                    )
                )

                val result = authTokkenDao.insert(
                    AuthToken(
                        response.body.pk,
                        response.body.token
                    )
                )

                if (result < 0) {
                    return onCompleteJob(
                        DataState.error(
                            Response(
                                ERROR_SAVE_AUTH_TOKEN, ResponseType.Dialog()
                            )
                        )
                    )
                }

                saveAuthenticateEmailToPref(username)
                onCompleteJob(
                    DataState.data(
                        AuthViewState(
                            authTokken = AuthToken(
                                response.body.pk, response.body.token
                            )
                        )
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<RegistrationResponse>> {
                return openApiAuthService.register(email, username, password, confirmPassword)
            }

            override fun setJob(job: Job) {
                repositryJon?.cancel()
                repositryJon = job
            }

            override suspend fun createCacheRequestAndReturn() {

            }

        }.asLiveData()

    }

    //TODO SAVE EMAIL TO PREFRENCE
    private fun saveAuthenticateEmailToPref(username: String) {
        sharedPreferencesEditor.putString(PREVIOUS_AUTH_USER, username)
        sharedPreferencesEditor.apply()
    }

    //TODO CHECK PREVIOUS AUTH USER
    fun checkPreviousAuthUser(): LiveData<DataState<AuthViewState>> {

        val previousAuthEmail = sharedPreferences.getString(PREVIOUS_AUTH_USER, null)
        if (previousAuthEmail.isNullOrBlank()) {
            return noTokkenFound()
        }
        return object : NetworkBoundResource<Void, AuthViewState>(
            sessionmanager.isConnectedToInternet(), false
        ) {
            override suspend fun createCacheRequestAndReturn() {
                accountProppertiesDao.searchByEmail(previousAuthEmail).let { accountProperty ->
                    accountProperty?.let {

                        if (accountProperty.pk > -1) {
                            authTokkenDao.searchByPk(it.pk)?.let { authToken ->

                                onCompleteJob(
                                    DataState.data(
                                        data = AuthViewState(
                                            authTokken = authToken
                                        )
                                    )
                                )

                                return
                            }
                        }
                        Log.e(TAG, "createCacheRequestAndReturn: ")
                        onCompleteJob(
                            DataState.data(
                                data = null,
                                response = Response(
                                    RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE, ResponseType.None()
                                )
                            )
                        )
                    }

                }
            }

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<Void>) {
                TODO("Not yet implemented")
            }

            override fun createCall(): LiveData<GenericApiResponse<Void>> {
                return AbsentLiveData.create()
            }

            override fun setJob(job: Job) {
                repositryJon?.cancel()
                repositryJon = job
            }

        }.asLiveData()
    }

    //TODO IF NO TOKKEN
    private fun noTokkenFound(): LiveData<DataState<AuthViewState>> {
        return object : LiveData<DataState<AuthViewState>>() {
            override fun onActive() {
                super.onActive()
                value = DataState.data(
                    data = null,
                    response = Response(
                        message = RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE,
                        responseType = ResponseType.None()
                    )
                )
            }
        }
    }


//    fun Login(username: String, password: String): LiveData<DataState<AuthViewState>> {
//        return openApiAuthService.login(username, password).switchMap { respnse ->
//            object : LiveData<DataState<AuthViewState>>() {
//                override fun onActive() {
//                    super.onActive()
//
//                    when (respnse) {
//                        is ApiSuccessResponse -> {
//                            value = DataState.data(
//                                data = AuthViewState(
//                                    authTokken = AuthToken(
//                                        respnse.body.pk,
//                                        respnse.body.token
//                                    )
//                                ), response = null
//                            )
//                        }
//                        is ApiErrorResponse -> {
//                            value = DataState.error(
//                                response = Response(
//                                    message = respnse.errorMessage,
//                                    responseType = ResponseType.Dialog()
//                                )
//                            )
//                        }
//                        is ApiEmptyResponse -> {
//                            value = DataState.error(
//                                response = Response(
//                                    message = ERROR_UNKNOWN,
//                                    responseType = ResponseType.Dialog()
//                                )
//                            )
//                        }
//                    }
//                }
//
//            }
//        }
//
//    }

//    fun registration(
//        email: String,
//        username: String,
//        password: String,
//        confirmPassword: String
//    ): LiveData<DataState<AuthViewState>> {
//
//        return openApiAuthService.register(email, username, password, confirmPassword)
//            .switchMap { response ->
//                object : LiveData<DataState<AuthViewState>>() {
//                    override fun onActive() {
//                        super.onActive()
//                        when (response) {
//                            is ApiSuccessResponse -> {
//                                value = DataState.data(
//                                    data = AuthViewState(
//                                        authTokken = AuthToken(
//                                            response.body.pk,
//                                            response.body.token
//                                        )
//                                    )
//                                )
//                            }
//                            is ApiErrorResponse -> {
//                                value = DataState.error(
//
//                                    response = Response(
//                                        message = response.errorMessage,
//                                        responseType = ResponseType.Dialog()
//                                    )
//                                )
//                            }
//                            is ApiEmptyResponse -> {
//                                value = DataState.error(
//                                    response = Response(
//                                        message = ERROR_UNKNOWN,
//                                        responseType = ResponseType.Dialog()
//                                    )
//                                )
//                            }
//                        }
//                    }
//                }
//
//
//            }
//    }


//    fun Login(username:String,password:String):LiveData<GenericApiResponse<LoginResponse>>{
//        return openApiAuthService.login(username,password)
//    }
//
//    fun registration(email: String,username:String,password: String,confirmPassword:String):LiveData<GenericApiResponse<RegistrationResponse>>{
//
//        return openApiAuthService.register(email,username,password,confirmPassword)
//    }

}