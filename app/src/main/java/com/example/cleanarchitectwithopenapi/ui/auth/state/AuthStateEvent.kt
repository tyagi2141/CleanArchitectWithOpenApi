package com.example.cleanarchitectwithopenapi.ui.auth.state

/**
 * Created by Rahul on 09/08/20.
 */
//
sealed class AuthStateEvent {

    data class LoginAttemptEvent(
        val username: String,
        val password: String
    ) : AuthStateEvent()

    data class RegistrationAttempt(
        val email: String,
        val username: String,
        val password: String,
        val confirm_password: String
    ) : AuthStateEvent()

    class CheckPreviousAuthEvent:AuthStateEvent()
}