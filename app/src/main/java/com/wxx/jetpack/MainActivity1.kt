package com.wxx.jetpack

import android.os.Bundle
import android.widget.Toast
import com.wxx.jetpack.base.BaseActivity

/**
 * @author TangRen on 2020/1/13 .
 * @packages：com.wxx.jetpack .
 * TODO：一句话描述
 */
class MainActivity1 : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val name = intent.getStringExtra("name")
        val default = intent.getStringExtra("default")

        Toast.makeText(applicationContext, "name=$name  default=$default", Toast.LENGTH_SHORT)
            .show()
    }
}