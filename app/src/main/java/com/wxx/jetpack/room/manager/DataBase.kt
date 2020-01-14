package com.wxx.jetpack.room.manager

import android.content.Context
import androidx.room.Room

/**
 * @author ：wuxinxi on 2020/1/14 .
 * @packages ：com.wxx.jetpack.lifecycle.room.manager .
 * TODO:一句话描述
 */
object DataBase {
    private lateinit var db: AppDatabase
    fun init(context: Context) {
        db = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
    }

    fun AppDB(): AppDatabase {
        return db
    }
}