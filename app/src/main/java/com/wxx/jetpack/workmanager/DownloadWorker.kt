package com.wxx.jetpack.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.wxx.jetpack.util.logd
import com.wxx.jetpack.util.loge
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * @author TangRen on 2020/1/12 .
 * @packages：com.wxx.jetpack.workmanager .
 * TODO：一句话描述
 */
class DownloadWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val apkUrl = inputData.getString("apkUrl")!!
        var apkPath = inputData.getString("apkPath")!!
        logd("开始执行任务")
        loge(apkPath)
        val apkPathFile = File(apkPath)
        if (!apkPathFile.exists()) {
            apkPathFile.mkdirs()
        }
        apkPath += "/apiDemo.apk"
        val res = apkUrl.downloadApk(apkPath)
        return if (res.isNullOrEmpty()) Result.retry() else Result.success()
    }

    private suspend fun String.downloadApk(apkPath: String): String? {
        var connection: HttpURLConnection? = null
        try {
            val url = URL(this)
            connection = url.openConnection() as HttpURLConnection
            connection.connect()
            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val stream = connection.inputStream
                stream.use { inputData ->
                    BufferedOutputStream(FileOutputStream(apkPath)).use { output ->
                        val count = connection.contentLength
                        val step = count / 100.0
                        var bytesCopied: Long = 0
                        val buffer = ByteArray(1024 * 2)
                        var bytes = inputData.read(buffer)
                        while (bytes >= 0) {
                            output.write(buffer, 0, bytes)
                            bytesCopied += bytes
                            bytes = inputData.read(buffer)
                            val progress = bytesCopied / step
                            //logd("进度：${progress.toInt()}")
                            setProgress(workDataOf("progress" to progress.toInt()))
                        }
                    }
                }
            }
            loge("响应码：${connection.responseCode}")
            return apkPath
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()
        }
        return null
    }


}