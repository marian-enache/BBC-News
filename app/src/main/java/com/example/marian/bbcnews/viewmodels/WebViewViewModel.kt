package com.example.marian.bbcnews.viewmodels

import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class WebViewViewModel(var link: String) : ViewModel() {

    val isLoading = ObservableBoolean()
    val webViewClient by lazy { MutableLiveData<Client>() }

    init {
        setLoading(true)
        webViewClient.value = Client()
    }

    fun setLoading(loading: Boolean) {
        isLoading.set(loading)
    }

    inner class Client : WebViewClient() {
        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            super.onReceivedError(view, request, error)
            setLoading(false)
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            setLoading(false)
        }
    }
}