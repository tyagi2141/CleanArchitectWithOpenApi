package com.example.cleanarchitectwithopenapi.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.cleanarchitectwithopenapi.R
import com.example.cleanarchitectwithopenapi.di.viewmodel.ViewModelProviderFactory
import com.example.cleanarchitectwithopenapi.ui.BaseActivity
import com.example.cleanarchitectwithopenapi.ui.ResponseType
import com.example.cleanarchitectwithopenapi.ui.auth.state.AuthStateEvent
import com.example.cleanarchitectwithopenapi.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

/**
 * Created by Rahul on 03/08/20.
 */
class AuthActivity : BaseActivity(), NavController.OnDestinationChangedListener {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: AuthViewModel
    override fun displayProgress(boolean: Boolean) {
        if (boolean){
            progress_bar.visibility= View.VISIBLE
        }else{
            progress_bar.visibility= View.INVISIBLE

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        viewModel = ViewModelProvider(this, providerFactory).get(AuthViewModel::class.java)
        findNavController(R.id.auth_nav_host_fragment).addOnDestinationChangedListener(this)

        subscribeObserver()
        checkPreviousAuthUser()
    }

    fun subscribeObserver() {


        viewModel.dataState.observe(this, Observer { dataState ->
            onDataStateChange(dataState)
            dataState.data?.let { data ->
                data.data?.let { event ->
                    event.getContentIfNotHandled()?.let {
                        it.authTokken?.let {
                            Log.d(TAG, "subscribeObserver: dataState in AuthActivty {$it}")
                            viewModel.setAuthToken(it)

                        }
                    }

                }


            }

        })

        viewModel.viewState.observe(this, Observer {
            it.authTokken?.let { authtoken ->
                sessionmanager.login(authtoken)
            }

        })
        sessionmanager.cacheTokken.observe(this, Observer { authToken ->

            Log.d(TAG, "AuthActivity: subscribeObserver AuthToken{$authToken}")
            // authToken?.let {
            if (authToken != null && authToken.account_pk != -1 && authToken.token != null) {
                navAuthActivity()
                //   }
            }

        })
    }

    private fun navAuthActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun checkPreviousAuthUser(){
        viewModel.setStateEvent(AuthStateEvent.CheckPreviousAuthEvent())
    }
    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        viewModel.cancelActiveJob()
    }


}