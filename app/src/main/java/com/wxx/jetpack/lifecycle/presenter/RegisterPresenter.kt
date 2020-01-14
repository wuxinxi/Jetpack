package com.wxx.jetpack.lifecycle.presenter

import android.os.SystemClock
import androidx.lifecycle.LifecycleOwner
import com.wxx.jetpack.util.logd
import kotlin.concurrent.thread

/**
 * @author ：wuxinxi on 2020/1/14 .
 * @packages ：com.wxx.jetpack.lifecycle.presenter .
 * TODO:一句话描述
 */
class RegisterPresenter : BasePresenter() {
    private var isRunning = true
    lateinit var thread: Thread
    fun register(userName: String, userPwd: String) {
        thread = thread {
            while (isRunning) {
                logd("userName=$userName,userPwd=$userPwd")
                SystemClock.sleep(2000)
            }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        isRunning = false
    }
}