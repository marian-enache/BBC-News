package com.example.marian.bbcnews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.marian.bbcnews.FragmentNavigator
import com.example.marian.bbcnews.R
import com.example.marian.bbcnews.ui.AboutFragment
import com.example.marian.bbcnews.ui.NewsFragment
import com.example.marian.bbcnews.ui.SettingsFragment
import com.example.marian.bbcnews.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var activityDataBinding: com.example.marian.bbcnews.databinding.ActivityContainerBinding

    private val fragmentNavigator by lazy { FragmentNavigator(R.id.flContainer, supportFragmentManager) }
    private val newsFragment by lazy { NewsFragment.newInstance() }
    private val settingsFragment by lazy { SettingsFragment.newInstance() }
    private val aboutFragment by lazy { AboutFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_container)
        activityDataBinding.lifecycleOwner = this
        activityDataBinding.viewModel = MainViewModel()

        activityDataBinding.viewModel?.fragmentTag?.observe(this@MainActivity, Observer { tag ->
            showOneFragment(tag)
        })
    }

    private fun showOneFragment(tag: String) {
        when (tag) {
            NewsFragment.TAG -> {
                fragmentNavigator.loadFragment(newsFragment, NewsFragment.TAG)
                fragmentNavigator.hideFragment(SettingsFragment.TAG)
                fragmentNavigator.hideFragment(AboutFragment.TAG)
            }
            SettingsFragment.TAG -> {
                fragmentNavigator.loadFragment(settingsFragment, SettingsFragment.TAG)
                fragmentNavigator.hideFragment(NewsFragment.TAG)
                fragmentNavigator.hideFragment(AboutFragment.TAG)
            }
            AboutFragment.TAG -> {
                fragmentNavigator.loadFragment(aboutFragment, AboutFragment.TAG)
                fragmentNavigator.hideFragment(NewsFragment.TAG)
                fragmentNavigator.hideFragment(SettingsFragment.TAG)
            }
        }
    }
}
