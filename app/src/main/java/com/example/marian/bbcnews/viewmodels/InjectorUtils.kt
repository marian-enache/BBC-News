package com.example.marian.bbcnews.viewmodels

import android.content.Context
import com.example.marian.bbcnews.repositories.NewsRepository
import com.example.marian.bbcnews.repositories.SettingsRepository
import com.example.marian.bbcnews.viewmodels.factories.NewsFragmentViewModelFactory
import com.example.marian.bbcnews.viewmodels.factories.SettingsFragmentViewModelFactory

object InjectorUtils {

    fun getNewsRepository(context: Context) = NewsRepository(context)

    fun getSettingsRepository(context: Context) = SettingsRepository(context)

    fun provideNewsFragmentViewModelFactory(context: Context): NewsFragmentViewModelFactory {
        return NewsFragmentViewModelFactory(getNewsRepository(context))
    }

    fun provideSettingsFragmentViewModelFactory(context: Context): SettingsFragmentViewModelFactory {
        return SettingsFragmentViewModelFactory(
            getSettingsRepository(
                context
            )
        )
    }
}