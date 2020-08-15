package com.example.cleanarchitectwithopenapi.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.example.cleanarchitectwithopenapi.R
import com.example.cleanarchitectwithopenapi.ui.BaseActivity
import com.example.cleanarchitectwithopenapi.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Rahul on 09/08/20.
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tool_bar.setOnClickListener {
            sessionmanager.logout()
        }
        subscribeObserver()
    }

    fun subscribeObserver() {



        sessionmanager.cacheTokken.observe(this, Observer { authToken ->

            Log.d(TAG, "MainActiviy: subscribeObserver AuthToken{$authToken}")
            if (authToken == null || authToken.account_pk == -1 || authToken.token == null) {
                navAuthActivity()
            }

        })
    }

    private fun navAuthActivity() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    override fun displayProgress(boolean: Boolean) {
        if (boolean){
            progress_bar.visibility= View.VISIBLE
        }else{
            progress_bar.visibility= View.INVISIBLE

        }
    }

}