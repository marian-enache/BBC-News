package com.example.marian.bbcnews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.marian.bbcnews.Constants
import com.example.marian.bbcnews.R
import com.example.marian.bbcnews.viewmodels.WebViewViewModel

class WebViewActivity : AppCompatActivity() {

    private lateinit var activityDataBinding: com.example.marian.bbcnews.databinding.ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_web_view)
        activityDataBinding.lifecycleOwner = this
        activityDataBinding.viewModel = WebViewViewModel(intent.extras.getString(Constants.INTENT_EXTRA_LINK)!!)
    }

}