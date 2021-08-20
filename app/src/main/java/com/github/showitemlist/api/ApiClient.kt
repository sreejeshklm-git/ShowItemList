package  com.github.showitemlist.api

import android.content.Context
import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {



  //private const val baseUrl = "http://77.240.89.133/Axionic/";
    private const val baseUrl = " https://api.github.com/orgs/square/"
    private var retrofit: Retrofit? = null
    private var retrofitWithoutHeader: Retrofit? = null
    private var apiInterface: ApiInterface? = null
    private var apiInterfaceWithoutHeader: ApiInterface? = null

    fun getApiInterface(context: Context): ApiInterface? {
        if (apiInterface == null) {
            apiInterface = getClient(context)!!.create(ApiInterface::class.java)
        }
        return apiInterface
    }
    fun getApiInterfaceWithoutHeader(context: Context): ApiInterface? {
        if (apiInterfaceWithoutHeader == null) {
            apiInterfaceWithoutHeader = getClientWithoutHeader(context)!!.create(ApiInterface::class.java)
        }
        return apiInterfaceWithoutHeader
    }

    private fun getClient(context: Context): Retrofit? {
        if (retrofit == null) {
            val interceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(interceptor)

           /* val langPref = LangPref(context)
            httpClient.addInterceptor(Interceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                requestBuilder
                    .addHeader("language", langPref.getLang()!!)

                val authSharedPref = AuthPrefReference.getAuthSharedPref(context)
                if (!TextUtils.isEmpty(authSharedPref!!.getToken())) {
                    requestBuilder.addHeader(
                        "Authorization",
                        "Bearer " + authSharedPref.getToken()!!
                    )
                }

                chain.proceed(requestBuilder.build())
            })*/
            val okHttpClient = httpClient.build()
//            val okHttpClient = UnsafeOkHttpClient.unsafeOkHttpClient
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //necessary for rx java retrofit api calls, else error pops up
                .addConverterFactory(ScalarsConverterFactory.create()) //To get result as a string
                .addConverterFactory(GsonConverterFactory.create()) //To get result as such in a model class (POJO)
                .client(okHttpClient)
                .build()
        }
        return retrofit
    }
    private fun getClientWithoutHeader(context: Context): Retrofit? {
        if (retrofitWithoutHeader == null) {
            val interceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(interceptor)

           /* val langPref = LangPref(context)
            val authSharedPref = AuthPrefReference.getAuthSharedPref(context)
            httpClient.addInterceptor(Interceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                requestBuilder
                    .addHeader("language", langPref.getLang()!!)
                    .addHeader("RefreshToken", authSharedPref!!.getRefreshToken()!!)

                chain.proceed(requestBuilder.build())
            })*/
            val okHttpClient = httpClient.build()
//            val okHttpClient = UnsafeOkHttpClient.unsafeOkHttpClient
            retrofitWithoutHeader = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //necessary for rx java retrofit api calls, else error pops up
                .addConverterFactory(ScalarsConverterFactory.create()) //To get result as a string
                .addConverterFactory(GsonConverterFactory.create()) //To get result as such in a model class (POJO)
                .client(okHttpClient)
                .build()
        }
        return retrofitWithoutHeader
    }

}