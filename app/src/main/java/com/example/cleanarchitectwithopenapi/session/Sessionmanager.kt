package com.example.cleanarchitectwithopenapi.session

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cleanarchitectwithopenapi.models.AuthToken
import com.example.cleanarchitectwithopenapi.persistence.AuthTokenDao
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rahul on 04/08/20.
 */

@Singleton
class Sessionmanager
@Inject
constructor(
    val authTokkenDao: AuthTokenDao,
    val application: Application
) {
    val TAG = "Sessionmanager"
    val _cacheTokken = MutableLiveData<AuthToken>()

    val cacheTokken: LiveData<AuthToken>
        get() = _cacheTokken

    fun logout() {
        Log.d(TAG, "logout......")
        GlobalScope.launch(IO) {
            var errorMessage: String? = null

            try {
                cacheTokken.value!!.account_pk?.let {
                    authTokkenDao.nullfyitoken(it)
                }
            } catch (e: CancellationException) {
                Log.d(TAG, "logout: {${e.message}}")
                errorMessage = e.message
            } catch (e: Exception) {
                Log.d(TAG, "logout: Exception {${e.message}}")
                errorMessage = errorMessage + "\n" + e.message
            } finally {
                errorMessage?.let {
                    Log.e(TAG, "logout: {$errorMessage}")
                }
                Log.e(TAG, "logout: finally")
                setValue(null)
            }
        }
    }

    fun login(authToken: AuthToken) {
        setValue(authToken)
    }

    fun setValue(newAuthTokken: AuthToken?) {
        GlobalScope.launch(Main) {
            if (_cacheTokken.value != newAuthTokken) {
                _cacheTokken.value = newAuthTokken
            }
        }
    }

    fun isConnectedToInternet(): Boolean {
        val cm=application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        try {
            return cm.activeNetworkInfo.isConnected
        }catch (e:Exception){
            Log.e(TAG, "isConnectedToInternet: {${e.message}}" )
        }
        return false
    }
}