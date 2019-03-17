package com.example.marian.bbcnews.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marian.bbcnews.repositories.SettingsRepository
import com.example.marian.bbcnews.viewmodels.SettingsViewModel

class SettingsFragmentViewModelFactory(var repository: SettingsRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = SettingsViewModel(repository) as T
}
