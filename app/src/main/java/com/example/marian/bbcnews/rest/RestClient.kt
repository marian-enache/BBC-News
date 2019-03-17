package com.example.marian.bbcnews.rest

import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object RestClient {

    private const val BBC_API = "https://feeds.bbci.co.uk/"

    val apiService: ApiService = getRetrofitInstance(BBC_API).create(ApiService::class.java)

    private fun getRetrofitInstance(url: String): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
//            .certificatePinner(
//                CertificatePinner.Builder()
//                    .add(
//                        "*.bbci.co.uk",
//                        "sha256/b94f6f125c79e3a5ffaa826f584c10d52ada669e6762051b826b55776d05aed7"
//                    )
//                    .add(
//                        "*.bbci.co.uk",
//                        "sha256/1d0a0e2da2b6e46e80eb9398f6d0202b3e1cd64ff0084f8948e8b79506e5d677"
//                    )
//                    .build()
//            )
            .build()

        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(
                SimpleXmlConverterFactory.createNonStrict(
                    Persister(AnnotationStrategy())
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}