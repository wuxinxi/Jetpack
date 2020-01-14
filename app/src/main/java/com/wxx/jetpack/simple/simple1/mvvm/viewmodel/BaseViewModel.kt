package com.wxx.jetpack.simple.simple1.mvvm.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * @author ：wuxinxi on 2020/1/14 .
 * @packages ：com.wxx.jetpack.simple.simple1.mvvm.viewmodel .
 * TODO:一句话描述
 */
open class BaseViewModel : ViewModel(), LifecycleObserver, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main //To change initializer of created properties use File | Settings | File Templates.

    private val mLaunchManager: MutableList<Job> = mutableListOf()

    protected fun request(
        request: suspend CoroutineScope.() -> Unit,
        exception: suspend CoroutineScope.(Throwable) -> Unit?,
        compiler: suspend CoroutineScope.() -> Unit?
    ) {
        callRequest(request, exception, compiler)
    }

    private fun callRequest(
        request: suspend CoroutineScope.() -> Unit,
        exception: suspend CoroutineScope.(Throwable) -> Unit?,
        compiler: suspend CoroutineScope.() -> Unit?
    ) {
        val job = launch {
            baseRequest(request, exception, compiler)
        }
        mLaunchManager.add(job)
        job.invokeOnCompletion { mLaunchManager.remove(job) }
    }

    private suspend fun baseRequest(
        request: suspend CoroutineScope.() -> Unit,
        exception: suspend CoroutineScope.(Throwable) -> Unit?,
        compiler: suspend CoroutineScope.() -> Unit?
    ) {
        coroutineScope {
            try {
                request()
            } catch (e: Exception) {
                e.printStackTrace()
                exception(e)
            } finally {
                compiler()
            }
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        mLaunchManager.clear()
    }

}