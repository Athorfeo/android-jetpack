package com.obcompany.androidjetpack.repository

import com.obcompany.androidjetpack.data.api.ApiService

class Repository {
    private val api by lazy { ApiService.create() }

    fun getApiService(): ApiService = api

    companion object {
        private val LOCK = Any()
        private var instance: Repository? = null
        fun instance(): Repository {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = Repository()
                }
                return instance!!
            }
        }
    }

}

