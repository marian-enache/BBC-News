package com.example.marian.bbcnews.ui

import androidx.lifecycle.ViewModel
import com.example.marian.bbcnews.R
import com.example.marian.bbcnews.base.BaseFragment
import com.example.marian.bbcnews.databinding.FragmentNewsBinding
import com.example.marian.bbcnews.viewmodels.AboutViewModel
import com.example.marian.bbcnews.viewmodels.NewsViewModel
import com.example.marian.bbcnews.viewmodels.SettingsViewModel

class AboutFragment : BaseFragment<FragmentNewsBinding, ViewModel>() {

    companion object {
        val TAG = AboutFragment::class.simpleName.toString()

        fun newInstance() = AboutFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_about
    }

    override fun getViewModel(): ViewModel {
        return AboutViewModel()
    }

    override fun getBindingVariable(): Int {
        return com.example.marian.bbcnews.BR.viewModel
    }
}