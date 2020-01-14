package com.wxx.jetpack.simple.simple1.mvvm.viewmodel

import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.wxx.jetpack.simple.simple1.mvvm.model.Chapters
import com.wxx.jetpack.simple.simple1.repository.HttpRepository
import com.wxx.jetpack.util.loge
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

/**
 * @author ：wuxinxi on 2020/1/14 .
 * @packages ：com.wxx.jetpack.simple.simple1.mvvm.viewmodel .
 * TODO:一句话描述
 */
class ChaptersViewModel : BaseViewModel() {
    val chapters: MutableLiveData<Chapters> = MutableLiveData()


    fun getChaptersList(
        exception: suspend CoroutineScope.(Throwable) -> Unit?,
        compiler: suspend CoroutineScope.() -> Unit?
    ) {
        request({
            val res = withContext(IO) {
                loge("开始请求 我是主线程：${Looper.getMainLooper() == Looper.getMainLooper()}")
                HttpRepository.getChapters()
            }
            chapters.value = res.await()
            loge("请求结束：${Looper.getMainLooper() == Looper.getMainLooper()}")
        }, {
            exception(it)
        }, {
            compiler()
        })
    }
}