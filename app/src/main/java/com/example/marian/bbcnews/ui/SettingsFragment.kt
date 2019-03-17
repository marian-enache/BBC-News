package com.example.marian.bbcnews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marian.bbcnews.R
import com.example.marian.bbcnews.base.BaseFragment
import com.example.marian.bbcnews.databinding.FragmentNewsBinding
import com.example.marian.bbcnews.viewmodels.InjectorUtils
import com.example.marian.bbcnews.viewmodels.NewsViewModel
import com.example.marian.bbcnews.viewmodels.SettingsViewModel

class SettingsFragment : BaseFragment<FragmentNewsBinding, ViewModel>() {

    companion object {
        val TAG = SettingsFragment::class.simpleName.toString()

        fun newInstance() = SettingsFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_settings
    }

    override fun getViewModel(): ViewModel {
        return ViewModelProvider(this, InjectorUtils.provideSettingsFragmentViewModelFactory(context!!))
            .get(SettingsViewModel::class.java)
    }

    override fun getBindingVariable(): Int {
        return com.example.marian.bbcnews.BR.viewModel
    }
}