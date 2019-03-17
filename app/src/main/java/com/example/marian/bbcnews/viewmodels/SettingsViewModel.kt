package com.example.marian.bbcnews.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marian.bbcnews.Constants
import com.example.marian.bbcnews.repositories.SettingsRepository


class SettingsViewModel(var repository: SettingsRepository) : ViewModel() {

    val openType = MutableLiveData<Int>()

    init {
        openType.value = repository.getOpenType()
    }

    fun setOpenType(type: Int) {
        when (type) {
            Constants.OPEN_TYPE_APP,
            Constants.OPEN_TYPE_BROWSER -> {
                repository.saveOpenType(type)
            }
        }
    }

}