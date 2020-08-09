package com.example.cleanarchitectwithopenapi.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.cleanarchitectwithopenapi.R
import com.example.cleanarchitectwithopenapi.util.ApiEmptyResponse
import com.example.cleanarchitectwithopenapi.util.ApiErrorResponse
import com.example.cleanarchitectwithopenapi.util.ApiSuccessResponse


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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.Regisration("orio@gmail.com","orio","12345","12345").observe(viewLifecycleOwner,
            Observer {response->
                when(response){
                    is ApiSuccessResponse->{
                        Log.e("RegistrationResponse","successfull"+response.body.toString())
                    }
                    is ApiErrorResponse->{
                        Log.e("RegistrationResponse","errror"+response.errorMessage)

                    }
                    is ApiEmptyResponse->{
                        Log.e("RegistrationResponse","emplty")

                    }
                }

            })
    }
    companion object {

    }
}