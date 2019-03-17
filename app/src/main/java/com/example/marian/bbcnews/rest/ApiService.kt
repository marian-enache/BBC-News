package com.example.marian.bbcnews.rest

import com.example.marian.bbcnews.rest.models.ApiResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("news/rss.xml")
    fun getNews() : Single<ApiResponse>
}