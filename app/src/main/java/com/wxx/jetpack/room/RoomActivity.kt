package com.wxx.jetpack.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wxx.jetpack.R
import com.wxx.jetpack.room.entity.User
import com.wxx.jetpack.room.manager.DataBase
import com.wxx.jetpack.util.logd
import kotlinx.android.synthetic.main.activity_room.*
import kotlin.concurrent.thread

/**
 * @author ：wuxinxi on 2020/1/14 .
 * @packages ：com.wxx.jetpack.lifecycle.room .
 * TODO:一句话描述
 */
class RoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        insert.setOnClickListener {
            thread {
                val user = User(
                    userName = System.currentTimeMillis().toString(),
                    userPwd = System.currentTimeMillis().toString()
                )
                DataBase.AppDB().userDao().insertUser(user)
            }
        }

        read.setOnClickListener {
            thread {
                val all = DataBase.AppDB().userDao().getAll()
                all.forEach {
                    logd(it.toString())
                }
            }

        }
    }
}