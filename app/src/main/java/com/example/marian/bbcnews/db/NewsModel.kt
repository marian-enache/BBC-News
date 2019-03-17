package com.example.marian.bbcnews.db

import com.example.marian.bbcnews.rest.models.News
import io.realm.RealmModel
import io.realm.annotations.RealmClass

/**
 * Am incercat sa folosesc Room, dar am avut foarte multe probleme de configuratie intre librariile de room si de databinding.
 */
@RealmClass
open class NewsModel() : RealmModel {

    var title: String? = null
    var description: String? = null
    var link: String? = null
    var date: String? = null

    var width: Int? = null
    var height: Int? = null
    var imageUrl: String? = null

    constructor(news: News) : this() {
        fromNews(news)
    }

    fun fromNews(news: News) : NewsModel{
        title = news.title
        description = news.description
        link = news.link
        date = news.date

        width = news.image?.width
        height = news.image?.height
        imageUrl = news.image?.url

        return this
    }
}