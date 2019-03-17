package com.example.marian.bbcnews

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration



class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().name("myrealm.realm").build()
        Realm.setDefaultConfiguration(config)
    }
}