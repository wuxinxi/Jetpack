package com.wxx.jetpack.simple.simple1.repository

import com.wxx.jetpack.simple.simple1.mvvm.model.Chapters
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

/**
 * @author ：wuxinxi on 2020/1/14 .
 * @packages ：com.wxx.jetpack.simple.simple1.repository .
 * TODO:一句话描述
 */

interface Api {
    @GET("/wxarticle/chapters/json")
    fun getChapters(): Deferred<Chapters>
}