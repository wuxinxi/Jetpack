package com.wxx.jetpack

import android.app.Application
import com.wxx.jetpack.room.manager.DataBase

/**
 * @author ：wuxinxi on 2020/1/14 .
 * @packages ：com.wxx.jetpack .
 * TODO:一句话描述
 */
class App:Application() {
    override fun onCreate() {
        super.onCreate()
        DataBase.init(this)
    }
}