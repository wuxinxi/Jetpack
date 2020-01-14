package com.wxx.jetpack.room.manager

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wxx.jetpack.room.dao.UserDao
import com.wxx.jetpack.room.entity.User

/**
 * @author ：wuxinxi on 2020/1/14 .
 * @packages ：com.wxx.jetpack.lifecycle.room.manager .
 * TODO:一句话描述
 */
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}