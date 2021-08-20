package com.github.showitemlist.api

import android.content.Context
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class ApiRequest {

    fun requestApi(
        context: Context,
        requests: List<Observable<*>?>?,
        serverCallback: ServerCallback
    ) {

        Observable.merge(requests)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retryWhen(RetryWithDelay(context, 1, 2000))
            .subscribe(object : Observer<Any?> {

                override fun onSubscribe(d: Disposable) {
                    //UtilLog.d("rxjava", "Disposable " + d);
                }

                override fun onNext(t: Any) {
                    serverCallback.onSuccess(t)
                }

                override fun onError(e: Throwable) {
                    Log.d("rxjava", "Throwable $e")
                    if (e is HttpException && e.code() == 401) { //unauthorised exception - token expiry
                        serverCallback.onTokenExpiry()
                    }

                    else if (e is UnknownHostException) {

                        serverCallback.onNetworkError()
                    } else {
                        serverCallback.onError(e)
                    }
                }

                override fun onComplete() {
                    Log.d("rxjava", "onComplete ")
                    serverCallback.onComplete()
                }
            })
    }

    //handle unauthorised exception - token expiry - delay is added as we do not want to overload server (negligible but good practice)
    private class RetryWithDelay(
        private val context: Context,
        private val maxRetries: Int,
        private val retryDelayMillis: Int
    ) :
        Function<Observable<out Throwable?>, Observable<*>> {
        private var retryCount = 0
        override fun apply(attempts: Observable<out Throwable?>): Observable<*> {
            return attempts
                .flatMap(Function { throwable ->
                    if (retryCount++ < maxRetries) {
                        Log.d(
                            "rxjava",
                            "maxRetries: $maxRetries retryCount: $retryCount throwable: $throwable"
                        )
                        if (throwable is HttpException && throwable.code() == 401) { //unauthorised exception - token expiry
                            //Todo
                            //TokenRefresh tokenRefresh = new TokenRefresh(context);
                            //tokenRefresh.refreshToken();
                        } else {
                            return@Function Observable.error<Any>(throwable)
                        }
                        // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                        return@Function Observable.timer(
                            retryDelayMillis.toLong(),
                            TimeUnit.MILLISECONDS
                        )
                    }
                    // Max retries hit. Just pass the error along.
                    Observable.error(throwable)
                })
        }
    }
}