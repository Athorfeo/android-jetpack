package com.obcompany.androidjetpack.repository

import com.obcompany.androidjetpack.api.API
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseRepository {
    val api by lazy { API.create() }
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun clear(){
        compositeDisposable.clear()
    }
}