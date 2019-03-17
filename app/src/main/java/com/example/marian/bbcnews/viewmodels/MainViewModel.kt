package com.example.marian.bbcnews.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marian.bbcnews.R
import com.example.marian.bbcnews.ui.AboutFragment
import com.example.marian.bbcnews.ui.NewsFragment
import com.example.marian.bbcnews.ui.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainViewModel : ViewModel() {

    var fragmentTag: MutableLiveData<String> = MutableLiveData()

    init {
        fragmentTag.value = NewsFragment.TAG
    }

    val onNavigationItemSelected = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.navigation_news -> {
                fragmentTag.value = NewsFragment.TAG
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                fragmentTag.value = SettingsFragment.TAG
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_about -> {
                fragmentTag.value = AboutFragment.TAG
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }
}