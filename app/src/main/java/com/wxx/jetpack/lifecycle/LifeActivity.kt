package com.wxx.jetpack.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.wxx.jetpack.R
import com.wxx.jetpack.lifecycle.model.NameViewModel
import com.wxx.jetpack.lifecycle.model.UserViewModel
import com.wxx.jetpack.lifecycle.presenter.IPresenter
import com.wxx.jetpack.lifecycle.presenter.RegisterPresenter
import com.wxx.jetpack.room.entity.User
import com.wxx.jetpack.util.loge
import kotlinx.android.synthetic.main.activity_life.*

/**
 * @author ：wuxinxi on 2020/1/14 .
 * @packages ：com.wxx.jetpack.lifecycle .
 * TODO:一句话描述
 */

class LifeActivity : AppCompatActivity() {
    lateinit var mPresenter: IPresenter
    lateinit var model: NameViewModel
    lateinit var userModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life)

        mPresenter = RegisterPresenter()
        lifecycle.addObserver(mPresenter)

        initNameModel()
        initUserModel()

    }

    private fun initUserModel() {
        userModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        val usersObserver = Observer<List<User>> { list ->
            data.text = ""
            list.forEach {
                data.append(it.toString() + "\n")
            }
        }
        read.setOnClickListener {
            userModel.getUsers().observe(this, usersObserver)
        }


    }

    private fun initNameModel() {
        model = ViewModelProviders.of(this).get(NameViewModel::class.java)

        btn1.setOnClickListener {
            model.currentName.value = "name=${System.currentTimeMillis()}"
            //(mPresenter as RegisterPresenter).register("吴新喜", "m12345")
        }

        val nameObserver = Observer<String> { name ->
            btn1.text = name
        }

        model.currentName.observe(this, nameObserver)

    }

    override fun onDestroy() {
        super.onDestroy()
        loge("activity onDestroy")
    }

}