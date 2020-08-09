package com.example.cleanarchitectwithopenapi.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.cleanarchitectwithopenapi.R
import com.example.cleanarchitectwithopenapi.ui.auth.state.RegistrationField
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : BaseAuthFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "registrationFragment: {${viewModel.hashCode()}}")
        subscribeOnserver()

    }


    fun subscribeOnserver() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer {

            it.registrationField?.let { registration ->
                registration.registration_email?.let { input_email.setText(it) }
                registration.registration_username?.let { input_username.setText(it) }
                registration.registration_password?.let { input_password.setText(it) }
                registration.registration_confirmPassword?.let { input_password_confirm.setText(it) }

            }

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setRegistrationFields(
            RegistrationField(
                input_email.text.toString(),
                input_username.text.toString(),
                input_password.text.toString(),
                input_password_confirm.text.toString()
            )
        )
    }
}