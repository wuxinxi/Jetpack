package com.wxx.jetpack.lifecycle.model

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wxx.jetpack.room.entity.User
import com.wxx.jetpack.room.manager.DataBase
import com.wxx.jetpack.util.loge
import kotlin.concurrent.thread

/**
 * @author ：wuxinxi on 2020/1/14 .
 * @packages ：com.wxx.jetpack.lifecycle.model .
 * TODO:一句话描述
 */
class UserViewModel : ViewModel() {
    private val users: MutableLiveData<List<User>> by lazy {
        MutableLiveData<List<User>>().also {
            loadUsers()
        }
    }

    fun getUsers(): LiveData<List<User>> {
        return users
    }

    private fun loadUsers() {
        thread {
            loge("读取...")
            val data = DataBase.AppDB().userDao().getAll()
            Handler(Looper.getMainLooper()).post {
                users.value = data
            }
        }
    }

}