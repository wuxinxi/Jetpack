package com.wxx.jetpack

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.view.View
import androidx.lifecycle.Observer
import androidx.work.*
import com.wxx.jetpack.base.BaseActivity
import com.wxx.jetpack.util.logd
import com.wxx.jetpack.util.loge
import com.wxx.jetpack.workmanager.DownloadWorker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        normal_work.setOnClickListener(this)
        schedule_work.setOnClickListener(this)
        constraint_work.setOnClickListener(this)

        val intent = Intent(this, MainActivity1::class.java).apply {
            putExtra("name", "吴唐人")
            putExtra("default", "安徽亳州")
        }
        startActivity(intent)

//        val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:18682188964"))
//        val activities: List<ResolveInfo> =
//            packageManager.queryIntentActivities(callIntent, PackageManager.MATCH_DEFAULT_ONLY)
//        val isIntentSafe = activities.isNotEmpty()


    }

    override fun onClick(v: View) {
        val apkUrl = "http://192.168.0.105/media/apk/ApiDemos.apk"
        val apkUrl1 = "http://192.168.1.70/media/apk/ftp.apk"
        val apkPath = Environment.getExternalStorageDirectory().absolutePath + "/" + "apk"
        val data = Data.Builder()
            .putString("apkUrl", apkUrl)
            .putString("apkPath", apkPath)
            .build()
        when (v.id) {
            R.id.normal_work -> {
                loge("normal_work")
                //执行单个任务
                val downRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
                    .setInputData(data)
                    .build()
                //取消所有任务
                //WorkManager.getInstance(this).cancelAllWork()
                //取消单个任务
                //WorkManager.getInstance(this).cancelWorkById(downRequest.id)
                WorkManager.getInstance(this).enqueue(downRequest)
                WorkManager.getInstance(this).getWorkInfoByIdLiveData(downRequest.id)
                    .observe(this, Observer { workInfo: WorkInfo? ->
                        if (workInfo != null) {
                            logd(
                                "主线程：${Looper.getMainLooper() == Looper.myLooper()},进度：${workInfo.progress.getInt(
                                    "progress",
                                    0
                                )}"
                            )
                        }
                    })
            }
            R.id.constraint_work -> {
                loge("constraint_work")
                //指明工作何时可以运行
                val build = Constraints.Builder()
                    .setRequiresCharging(true)//充电的时候时
                    .setRequiredNetworkType(NetworkType.CONNECTED)//网络可用时
                    .setRequiresBatteryNotLow(true)//电量充足时
                    .setRequiresStorageNotLow(true)//内存充足时
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    build.setTriggerContentMaxDelay(15, TimeUnit.MINUTES)//最大延迟（API>24时）
                }
                val constraints = build.build()
                val downRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
                    .setInputData(data)
                    .setConstraints(constraints)
                    .setInitialDelay(10, TimeUnit.SECONDS)//任务初始化延迟
                    .setBackoffCriteria(
                        BackoffPolicy.EXPONENTIAL,//指数
                        OneTimeWorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS,//时间
                        TimeUnit.MILLISECONDS//单位
                    )//重试以及退避政策
                    .addTag("CANCEL_TAG")//标记，可用于取消单个任务
                    .build()
                WorkManager.getInstance(this).enqueue(downRequest)
            }
            R.id.schedule_work -> {
                loge("schedule_work")
                /**
                 * 执行周期任务
                 * repeatInterval:任务周期，至少15分钟
                 * flexTimeInterval：最晚可延迟时间，至少5分钟
                 */
                val request = PeriodicWorkRequestBuilder<DownloadWorker>(
                    15, TimeUnit.MINUTES, 5, TimeUnit.MINUTES
                )
                    .setInputData(data)
                    .build()
                WorkManager.getInstance(this).enqueue(request)
            }
        }
    }


}
