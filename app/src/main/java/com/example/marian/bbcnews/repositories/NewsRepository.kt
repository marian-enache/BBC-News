package com.example.marian.bbcnews.repositories

import android.content.Context
import com.example.marian.bbcnews.Utils
import com.example.marian.bbcnews.db.NewsModel
import com.example.marian.bbcnews.rest.RestClient
import com.example.marian.bbcnews.rest.models.News
import io.reactivex.Single
import io.realm.Realm

class NewsRepository(var context: Context) {

    private var realm = Realm.getDefaultInstance()

    fun hasNetwork() : Boolean{
        return Utils.isNetworkAvailable(context)
    }

    fun getNewsFromServer() : Single<MutableList<News>> {
        return RestClient.apiService.getNews()
            .map { response -> response.channel?.news }
    }

    fun getNewsFromDb() : ArrayList<News>{
        var news = ArrayList<News>()

        try {
            var newsFromDb = realm.where(NewsModel::class.java)
                .findAll()

            for (newsInDb in newsFromDb) {
                news.add(News(newsInDb))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return news
    }

    fun saveNews(news: MutableList<News>?) {
        if (news.isNullOrEmpty()) {
            return
        }

        var newsToInsert = ArrayList<NewsModel>()
        for (n in news) {
            newsToInsert.add(NewsModel(n))
        }

        realm.executeTransactionAsync { realm ->
            realm.deleteAll()
            realm.insert(newsToInsert)
        }
    }

}