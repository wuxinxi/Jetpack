package com.wxx.jetpack.base

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions

/**
 * @author TangRen on 2020/1/12 .
 * @packages：com.wxx.jetpack.base .
 * TODO：一句话描述
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onStart() {
        super.onStart()
        checkAppPermission()
    }

    private fun checkAppPermission() {
        val permissions = RxPermissions(this)
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe {}.dispose()
    }
}