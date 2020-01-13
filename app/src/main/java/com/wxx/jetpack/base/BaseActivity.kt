package com.wxx.jetpack.base

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.tbruyelle.rxpermissions2.RxPermissions

/**
 * @author TangRen on 2020/1/12 .
 * @packages：com.wxx.jetpack.base .
 * TODO：一句话描述
 */
abstract class BaseActivity : AppCompatActivity(), LifecycleOwner {
    lateinit var registry: LifecycleRegistry
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registry = LifecycleRegistry(this)
        registry.currentState = Lifecycle.State.CREATED
    }

    override fun onStart() {
        super.onStart()
        registry.currentState = Lifecycle.State.STARTED
        checkAppPermission()
    }

    override fun onDestroy() {
        super.onDestroy()
        registry.currentState = Lifecycle.State.DESTROYED
    }

    private fun checkAppPermission() {
        val permissions = RxPermissions(this)
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe {}.dispose()
    }

//    override fun getLifecycle(): Lifecycle {
//        if (registry == null) return super.getLifecycle() else return registry
//
//    }
}