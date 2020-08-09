package com.example.cleanarchitectwithopenapi.ui.auth

import androidx.lifecycle.LiveData
import com.example.cleanarchitectwithopenapi.di.auth.network_responses.LoginResponse
import com.example.cleanarchitectwithopenapi.di.auth.network_responses.RegistrationResponse
import com.example.cleanarchitectwithopenapi.models.AuthToken
import com.example.cleanarchitectwithopenapi.repositry.auth.AuthRepositry
import com.example.cleanarchitectwithopenapi.ui.BaseViewModel
import com.example.cleanarchitectwithopenapi.ui.DataState
import com.example.cleanarchitectwithopenapi.ui.auth.state.AuthStateEvent
import com.example.cleanarchitectwithopenapi.ui.auth.state.AuthStateEvent.*
import com.example.cleanarchitectwithopenapi.ui.auth.state.AuthViewState
import com.example.cleanarchitectwithopenapi.ui.auth.state.LoginFields
import com.example.cleanarchitectwithopenapi.ui.auth.state.RegistrationField
import com.example.cleanarchitectwithopenapi.util.AbsentLiveData
import com.example.cleanarchitectwithopenapi.util.GenericApiResponse
import javax.inject.Inject

/**
 * Created by Rahul on 04/08/20.
 */
class AuthViewModel
@Inject
constructor(
    val authRepositry: AuthRepositry
) : BaseViewModel<AuthStateEvent, AuthViewState>() {



    fun setRegistrationFields(registrationField: RegistrationField) {

        val update = getCurrentViewStateOrNew()
        if (update.registrationField == registrationField) {
            return
        }
        update.registrationField = registrationField
        _viewState.value = update
    }

    fun setLoginFields(loginFields: LoginFields) {

        val update = getCurrentViewStateOrNew()
        if (update.loginField == loginFields) {
            return
        }
        update.loginField = loginFields
        _viewState.value = update
    }

    fun setAuthToken(authToken: AuthToken) {

        val update = getCurrentViewStateOrNew()
        if (update.authTokken == authToken) {
            return
        }
        update.authTokken = authToken
        _viewState.value = update
    }

    override fun newViewState(): AuthViewState {
        return AuthViewState()
    }

    override fun handleStateEvent(stateEvent: AuthStateEvent): LiveData<DataState<AuthViewState>> {
        when (stateEvent) {
            is LoginAttemptEvent -> {
                return AbsentLiveData.create()
            }
            is RegistrationAttempt -> {
                return AbsentLiveData.create()
            }
            is CheckPreviousAuthEvent -> {
                return AbsentLiveData.create()
            }


        }
    }
}