package com.example.cleanarchitectwithopenapi.ui

import android.util.Log
import com.example.cleanarchitectwithopenapi.session.Sessionmanager
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Rahul on 08/08/20.
 */
abstract class BaseActivity : DaggerAppCompatActivity(), DataStateChangeListener {

    val TAG = "BaseActivity"

    @Inject
    lateinit var sessionmanager: Sessionmanager

    override fun onDataStateChange(dataState: DataState<*>?) {
        dataState?.let {
            GlobalScope.launch(Main) {
                displayProgress(it.loading.isLoading)

                it.error?.let { errorEvent ->
                    handelStateError(errorEvent)
                }

                it.data?.let {
                    it.response?.let { responsseEvent ->
                        handellResponseEvent(responsseEvent)

                    }
                }
            }
        }
    }

    private fun handellResponseEvent(responsseEvent: Event<Response>) {

        responsseEvent.getContentIfNotHandled()?.let {
            when (it.responseType) {

                is ResponseType.Toast -> {
                    it.message.let { message ->
                        displayToast(message)
                    }
                }
                is ResponseType.Dialog -> {
                    it.message?.let { message ->
                        displaySuccessDialog(message)
                    }
                }
                is ResponseType.None -> {

                    Log.e(TAG, "handelStateError: ${it.message}")
                }
            }
        }

    }

    private fun handelStateError(errorEvent: Event<StateError>) {

        errorEvent.getContentIfNotHandled()?.let {
            when (it.response.responseType) {

                is ResponseType.Toast -> {
                    it.response.message.let { message ->
                        displayToast(message)
                    }
                }
                is ResponseType.Dialog -> {
                    it.response.message?.let { message ->
                        displayErrorDialog(message)
                    }
                }
                is ResponseType.None -> {

                    Log.e(TAG, "handelStateError: ${it.response.message}")
                }
            }
        }
    }

    abstract fun displayProgress(boolean: Boolean)
}