package com.wxx.jetpack.lifecycle.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author ：wuxinxi on 2020/1/14 .
 * @packages ：com.wxx.jetpack.lifecycle.livedata .
 * TODO:一句话描述
 */
class NameViewModel : ViewModel() {
    val currentName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

}