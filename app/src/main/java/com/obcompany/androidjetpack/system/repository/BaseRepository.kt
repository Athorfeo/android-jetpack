package com.obcompany.androidjetpack.system.repository

import com.obcompany.androidjetpack.system.network.API
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