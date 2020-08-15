package com.example.cleanarchitectwithopenapi.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.cleanarchitectwithopenapi.R
import com.example.cleanarchitectwithopenapi.ui.auth.state.AuthStateEvent
import com.example.cleanarchitectwithopenapi.ui.auth.state.LoginFields
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseAuthFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "LoginFragment: {${viewModel.hashCode()}}")
        login_button.setOnClickListener {
            login()
        }
        subscribeObservers()
    }

    fun login() {
        viewModel.setStateEvent(
            AuthStateEvent.LoginAttemptEvent(
                input_email.text.toString(),
                input_password.text.toString()
            )
        )
    }

    fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            it.loginField?.let {
                it.login_email?.let { input_email.setText(it) }
                it.login_password?.let { input_password.setText(it) }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setLoginFields(
            LoginFields(
                input_email.text.toString(),
                input_password.text.toString()
            )
        )
    }
}