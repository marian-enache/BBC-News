package com.example.marian.bbcnews.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marian.bbcnews.repositories.NewsRepository
import com.example.marian.bbcnews.viewmodels.NewsViewModel

class NewsFragmentViewModelFactory(var repository: NewsRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = NewsViewModel(repository) as T
}
