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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.Login("tyagi2141@gmail.com","9987344388").observe(viewLifecycleOwner, Observer {response->

            when(response){
                is ApiSuccessResponse->{
                    Log.e("LoginDataResponse","sucesssfull  ==  "+response.body.toString())

                }
                is ApiErrorResponse->{
                    Log.e("LoginDataResponse"," error === "+response.errorMessage)

                }
                is ApiEmptyResponse->{
                    Log.e("LoginDataResponse","emplty ==== "+response)

                }
            }
        })


    }
    companion object {

    }
}