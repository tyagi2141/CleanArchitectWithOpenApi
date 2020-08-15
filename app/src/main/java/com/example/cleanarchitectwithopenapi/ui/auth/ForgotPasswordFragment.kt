package com.example.cleanarchitectwithopenapi.ui.auth

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.example.cleanarchitectwithopenapi.R
import com.example.cleanarchitectwithopenapi.ui.DataState
import com.example.cleanarchitectwithopenapi.ui.DataStateChangeListener
import com.example.cleanarchitectwithopenapi.ui.Response
import com.example.cleanarchitectwithopenapi.ui.ResponseType
import com.example.cleanarchitectwithopenapi.util.Constants
import com.example.cleanarchitectwithopenapi.util.Constants.Companion.PASSWORD_RESET_URL
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/*

class ForgotPasswordFragment : BaseAuthFragment() {

    lateinit var webView: WebView

    lateinit var stateChangeListener: DataStateChangeListener

    val webInteractionCallback = object: WebAppInterface.OnWebInteractionCallback {

        override fun onError(errorMessage: String) {
            Log.e(TAG, "onError: $errorMessage")

            val dataState = DataState.error<Any>(
                response = Response(errorMessage, ResponseType.Dialog())
            )
            stateChangeListener.onDataStateChange(
                dataState = dataState
            )
        }

        override fun onSuccess(email: String) {
            Log.d(TAG, "onSuccess: a reset link will be sent to $email.")
            onPasswordResetLinkSent()
        }

        override fun onLoading(isLoading: Boolean) {
            Log.d(TAG, "onLoading... ")
            CoroutineScope(Main).launch {
                stateChangeListener.onDataStateChange(
                    DataState.isLoading(isLoading = isLoading, cacheData = null)
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = view.findViewById(R.id.webview)

        loadPasswordResetWebView()

        return_to_launcher_fragment.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun loadPasswordResetWebView(){
        stateChangeListener.onDataStateChange(
            DataState.isLoading(isLoading = true, cacheData = null)
        )
        webView.webViewClient = object: WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                stateChangeListener.onDataStateChange(
                    DataState.isLoading(isLoading = false, cacheData = null)
                )
            }
        }
        webView.loadUrl(Constants.PASSWORD_RESET_URL)
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(WebAppInterface(webInteractionCallback), "AndroidTextListener")
    }



    class WebAppInterface
    constructor(
        private val callback: OnWebInteractionCallback
    ) {

        private val TAG: String = "AppDebug"

        @JavascriptInterface
        fun onSuccess(email: String) {
            callback.onSuccess(email)
        }

        @JavascriptInterface
        fun onError(errorMessage: String) {
            callback.onError(errorMessage)
        }

        @JavascriptInterface
        fun onLoading(isLoading: Boolean) {
            callback.onLoading(isLoading)
        }

        interface OnWebInteractionCallback{

            fun onSuccess(email: String)

            fun onError(errorMessage: String)

            fun onLoading(isLoading: Boolean)
        }
    }

    fun onPasswordResetLinkSent(){
        CoroutineScope(Main).launch{
            parent_view.removeView(webView)
            webView.destroy()

            val animation = TranslateAnimation(
                password_reset_done_container.width.toFloat(),
                0f,
                0f,
                0f
            )
            animation.duration = 500
            password_reset_done_container.startAnimation(animation)
            password_reset_done_container.visibility = View.VISIBLE
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            stateChangeListener = context as DataStateChangeListener
        }catch(e: ClassCastException){
            Log.e(TAG, "$context must implement DataStateChangeListener" )
        }
    }
}
*/



class ForgotPasswordFragment : BaseAuthFragment() {

    lateinit var webView: WebView
    lateinit var dataStateChangeListener: DataStateChangeListener

    var webInteractionCallback = object :
        WebAppInterface.OnWebInteractionCallback {

     /*   override fun onSuccess(email: String) {
            Log.e(TAG, "onSucess: ${email}")

            onPasswordChangeRequestSend()        }*/

        override fun onSuccess(email: String) {
            Log.e(TAG, "onSucess: ${email}")

            onPasswordChangeRequestSend()           }

        override fun onError(errorMessage: String) {
            Log.e(TAG, "onError: ${errorMessage}")

            val dataState = DataState.error<Any>(
                response = Response(message = errorMessage, responseType = ResponseType.Dialog())
            )
            dataStateChangeListener.onDataStateChange(dataState = dataState)



        }

        override fun onLoading(isLoading: Boolean) {
            Log.e(TAG, "onLoading: " )


            CoroutineScope(Main).launch {
                dataStateChangeListener.onDataStateChange(
                    DataState.isLoading(isLoading = isLoading, cacheData = null)
                )
            }
        }


    }

    private fun onPasswordChangeRequestSend() {
            CoroutineScope(Main).launch {
                parent_view.removeView(webView)
                webView.destroy()

                val animation = TranslateAnimation(
                    password_reset_done_container.width.toFloat(),
                    0f,
                    0f,
                    0f
                )
                animation.duration = 500
                password_reset_done_container.startAnimation(animation)
                password_reset_done_container.visibility = View.VISIBLE
            }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "forgotPasswoer: {${viewModel.hashCode()}}")
//
//        webView = view.findViewById(R.id.webview)
//
//        loadPasswordResetWebView()
//
//        return_to_launcher_fragment.setOnClickListener {
//            findNavController().popBackStack()
//        }

        webView = view.findViewById(R.id.webview)

        loadPasswordResetWebView()

        return_to_launcher_fragment.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun loadPasswordResetWebView(){
        dataStateChangeListener.onDataStateChange(
            DataState.isLoading(isLoading = true, cacheData = null)
        )
        webView.webViewClient = object: WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                dataStateChangeListener.onDataStateChange(
                    DataState.isLoading(isLoading = false, cacheData = null)
                )
            }
        }
        webView.loadUrl(Constants.PASSWORD_RESET_URL)
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(WebAppInterface( webInteractionCallback), "AndroidTextListener")

    }

/*    class WebAppInterface
    constructor(private val callback: OnWebInteractionCallback) {

        private val TAG: String = "AppDebug"

        @JavascriptInterface
        fun onSuccess(email: String) { callback.onSuccess(email) }

        @JavascriptInterface
        fun onError(errorMessage: String) { callback.onError(errorMessage) }

        @JavascriptInterface
        fun onLoading(isLoading: Boolean) { callback.onLoading(isLoading) }

        interface OnWebInteractionCallback{

            fun onSuccess(email: String)

            fun onError(errorMessage: String)

            fun onLoading(isLoading: Boolean)
        }
    }*/
    class WebAppInterface
  constructor(private  val callback: OnWebInteractionCallback
    ) {
        private val TAG = "ForgotPasswordFragment"

    @JavascriptInterface
    fun onSuccess(email: String) { callback.onSuccess(email) }


    @JavascriptInterface
    fun onError(errorMessage: String) { callback.onError(errorMessage) }

    @JavascriptInterface
        fun onLoading(isLoading: Boolean) { callback.onLoading(isLoading) }

//    @JavascriptInterface
//    fun onLoading(isLoading: Boolean) { callback.onLoading(isLoading) }

    interface OnWebInteractionCallback {

            fun onSuccess(email: String)
            fun onError(errorMessage: String)
            fun onLoading(isLoading: Boolean)
        }
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            dataStateChangeListener = context as DataStateChangeListener
        }catch(e: ClassCastException){
            Log.e(TAG, "$context must implement DataStateChangeListener" )
        }
    }
}
