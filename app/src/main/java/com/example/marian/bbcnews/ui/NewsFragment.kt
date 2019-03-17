package com.example.marian.bbcnews.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marian.bbcnews.R
import com.example.marian.bbcnews.base.BaseFragment
import com.example.marian.bbcnews.databinding.FragmentNewsBinding
import com.example.marian.bbcnews.viewmodels.InjectorUtils
import com.example.marian.bbcnews.viewmodels.NewsViewModel


class NewsFragment : BaseFragment<FragmentNewsBinding, ViewModel>() {

    companion object {
        val TAG = NewsFragment::class.simpleName.toString()

        fun newInstance() = NewsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val adapter = NewsAdapter()
        binding.rvNews.adapter = adapter

        (mViewModel as NewsViewModel).news.observe(this, Observer { news ->
            if (news != null) adapter.submitList(news)
        })

        (mViewModel as NewsViewModel).noInternet.observe(this, Observer { noInternet ->
            activity?.title = String.format("%s %s", getString(R.string.app_name), if (noInternet) getString(R.string.no_internet) else "")
        })

        (mViewModel as NewsViewModel).remoteError.observe(this, Observer { remoteError ->
            if (remoteError) {
                Toast.makeText(this@NewsFragment.context, getString(R.string.remote_error), Toast.LENGTH_LONG).show()
            }
        })

        return binding.root
    }

    override fun getLayoutId(): Int {
        return com.example.marian.bbcnews.R.layout.fragment_news
    }

    override fun getViewModel(): ViewModel {
        return ViewModelProvider(this, InjectorUtils.provideNewsFragmentViewModelFactory(context!!))
            .get(NewsViewModel::class.java)
    }

    override fun getBindingVariable(): Int {
        return com.example.marian.bbcnews.BR.viewModel
    }
}