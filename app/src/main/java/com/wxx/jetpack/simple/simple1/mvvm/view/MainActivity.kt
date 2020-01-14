package com.wxx.jetpack.simple.simple1.mvvm.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.wxx.jetpack.R
import com.wxx.jetpack.simple.simple1.mvvm.model.Chapters
import com.wxx.jetpack.simple.simple1.mvvm.viewmodel.ChaptersViewModel
import kotlinx.android.synthetic.main.activity_life.*

/**
 * @author ：wuxinxi on 2020/1/14 .
 * @packages ：com.wxx.jetpack.simple.simple1.mvvm.view .
 * TODO:一句话描述
 */
class MainActivity : AppCompatActivity() {
    lateinit var model: ChaptersViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life)
        model = ViewModelProviders.of(this).get(ChaptersViewModel::class.java)
        lifecycle.addObserver(model)
        val chaptersObserver = Observer<Chapters> {
            data.append(it.toString())
        }
        model.chapters.observe(this, chaptersObserver)

        read.setOnClickListener {
            model.getChaptersList()
        }

    }

    override fun onDestroy() {
        model.onDestroy()
        super.onDestroy()
    }
}