package com.example.marian.bbcnews.repositories

import android.content.Context
import com.example.marian.bbcnews.Constants

class SettingsRepository(var context: Context) {

    fun getOpenType() =
        context.getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            .getInt(Constants.OPEN_TYPE, Constants.OPEN_TYPE_APP)

    fun saveOpenType(type: Int) {
        var prefs = context.getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putInt(Constants.OPEN_TYPE, type).apply()
    }
}