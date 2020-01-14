package com.wxx.jetpack.simple.simple1.mvvm.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wxx.jetpack.simple.simple1.mvvm.viewmodel.BaseViewModel

/**
 * @author ：wuxinxi on 2020/1/14 .
 * @packages ：com.wxx.jetpack.simple.simple1.mvvm.view .
 * TODO:一句话描述
 */
class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    protected var mViewModel: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}