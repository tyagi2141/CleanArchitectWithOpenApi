package com.example.cleanarchitectwithopenapi.ui.auth.state

import com.example.cleanarchitectwithopenapi.models.AuthToken

/**
 * Created by Rahul on 09/08/20.
 */


data class AuthViewState(
    var registrationField: RegistrationField? = RegistrationField(),
    var loginField: LoginFields? = LoginFields(),
    var authTokken:AuthToken?=null
) {

}

data class RegistrationField(
    var registration_email: String? = null,
    var registration_username: String? = null,
    var registration_password: String? = null,
    var registration_confirmPassword: String? = null
) {
    class RegistrationError {
        companion object {

            fun mustFillAllField(): String {
                return "All Field Are Requireed"
            }

            fun passwordDoNotMatch(): String {
                return "password not match"
            }

            fun Nono(): String {
                return "None"
            }
        }


    }

    fun isValidForregistrartion(): String {
        if (
            registration_email.isNullOrEmpty() ||
            registration_username.isNullOrEmpty() ||
            registration_password.isNullOrEmpty() ||
            registration_confirmPassword.isNullOrEmpty()
        ) {
            return RegistrationError.mustFillAllField()
        }

        if (!registration_password.equals(registration_confirmPassword)) {
            return RegistrationError.passwordDoNotMatch()
        }

        return RegistrationError.Nono()
    }
}


data class LoginFields(
    var login_email: String? = null,
    var login_password: String? = null
) {
    class LoginError {

        companion object {

            fun mustFillAllFields(): String {
                return "You can't login without an email and password."
            }

            fun none(): String {
                return "None"
            }

        }
    }

    fun isValidForLogin(): String {

        if (login_email.isNullOrEmpty()
            || login_password.isNullOrEmpty()
        ) {

            return LoginError.mustFillAllFields()
        }
        return LoginError.none()
    }

    override fun toString(): String {
        return "LoginState(email=$login_email, password=$login_password)"
    }
}