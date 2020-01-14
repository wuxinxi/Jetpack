package com.wxx.jetpack.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wxx.jetpack.room.entity.User

/**
 * @author ：wuxinxi on 2020/1/14 .
 * @packages ：com.wxx.jetpack.lifecycle.room.dao .
 * TODO:一句话描述
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE userName=:name  AND userPwd=:pwd")
    fun findByName(name: String, pwd: String): User?

    @Insert
    fun insertUser(vararg user: User)
}