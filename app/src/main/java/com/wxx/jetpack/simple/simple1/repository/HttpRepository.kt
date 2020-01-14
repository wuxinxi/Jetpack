package com.wxx.jetpack.simple.simple1.repository

import com.wxx.jetpack.simple.simple1.ext.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author ：wuxinxi on 2020/1/14 .
 * @packages ：com.wxx.jetpack.simple.simple1.repository .
 * TODO:一句话描述
 */
object HttpRepository {
    private fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .callTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    private fun getApiService(): Api {
        return Retrofit.Builder()
            .baseUrl("https://wanandroid.com")
            .client(okHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(Api::class.java)
    }

    fun getChapters() = getApiService().getChapters()

}