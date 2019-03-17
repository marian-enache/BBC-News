package com.example.marian.bbcnews.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marian.bbcnews.repositories.NewsRepository
import com.example.marian.bbcnews.rest.models.News
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.Realm.getDefaultInstance

class NewsViewModel(var repository: NewsRepository) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    val news: MutableLiveData<MutableList<News>> by lazy { MutableLiveData<MutableList<News>>() }

    private var realm: Realm = getDefaultInstance()

    val noItemsAtAll = ObservableBoolean()
    val isLoading = ObservableBoolean()

    val noInternet by lazy { MutableLiveData<Boolean>() }
    val remoteError by lazy { MutableLiveData<Boolean>()}

    private var dbQueried = false
    private var serverQueried = false

    init {
        getNews()
    }

    private fun getNews() {
        noInternet.value = !repository.hasNetwork()

        if (repository.hasNetwork()) {
            fetchNews()
        } else {
            getNewsFromDb()
        }
    }

    private fun fetchNews() {
        setLoading(true)
        compositeDisposable.add(
            repository.getNewsFromServer()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    remoteError.value = false
                    news.value = response
                    repository.saveNews(response)
                    setLoading(false)
                    serverQueried = true
                    checkIfItems()
                }, { throwable ->
                    remoteError.value = true
                    setLoading(false)
                    getNewsFromDb()
                }
            )
        )
    }

    private fun getNewsFromDb() {
        setLoading(true)

        news.value = repository.getNewsFromDb()

        dbQueried = true
        setLoading(false)
        checkIfItems()
    }

    fun onRefresh() {
        noInternet.value = !repository.hasNetwork()
        fetchNews()
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        realm.close()
        super.onCleared()
    }

    private fun setLoading(loading: Boolean) {
        isLoading.set(loading)
    }

    private fun checkIfItems() {
        noItemsAtAll.set((dbQueried || serverQueried) && news.value.isNullOrEmpty())
    }

}