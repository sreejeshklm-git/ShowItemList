package com.github.showitemlist.api

interface ServerCallback {
    fun onSuccess(`object`: Any?)

    fun onTokenExpiry()

    fun onError(throwable: Throwable?)

    fun onNetworkError()

    fun onComplete()
}