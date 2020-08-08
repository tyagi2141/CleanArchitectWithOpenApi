package com.example.cleanarchitectwithopenapi.ui.auth

import androidx.lifecycle.ViewModel
import com.example.cleanarchitectwithopenapi.repositry.auth.AuthRepositry
import javax.inject.Inject

/**
 * Created by Rahul on 04/08/20.
 */
class AuthViewModel
@Inject
constructor(
    authRepositry: AuthRepositry
) : ViewModel() {
}