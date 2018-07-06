package com.example.sinoyd.frameapplication.KotlinFrame.Uitl

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log

import java.io.File
import java.io.IOException
import java.sql.Timestamp
import java.text.DecimalFormat
import android.provider.MediaStore
import android.provider.DocumentsContract
import android.content.ContentUris
import android.os.Build
import android.content.ContentResolver
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.DateUtil
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.util.*


/**
 * 文件工具类 Copyright (c) 2015
 *
 * @类型名称：FileUtil
 * @创建人：
 * @创建日期：
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
object FileUtil {
    /**
     * 获取目录名称
     *
     * @param url
     * @return FileName
     */
    fun getFileName(url: String): String {
        val lastIndexStart = url.lastIndexOf("/")
        return if (lastIndexStart != -1) {
            url.substring(lastIndexStart + 1, url.length)
        } else {
            Timestamp(System.currentTimeMillis()).toString()
        }
    }

    /**
     * 判断SD卡是否存在
     *
     * @return boolean
     */
    fun checkSDCard(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    /**
     * 保存目录目录到目录
     *
     * @param context
     * @return 目录保存的目录
     */
    fun setMkdir(context: Context): String {
        var filePath: String? = null
        if (checkSDCard()) {
            filePath = Environment.getExternalStorageDirectory().toString() + File.separator + "sinoyd" + File.separator + "downloads"
        } else {
            filePath = context.cacheDir.absolutePath + File.separator + "sinoyd" + File.separator + "downloads"
        }
        val file = File(filePath)
        if (!file.exists()) {
            file.mkdirs()
            Log.e("file", "目录不存在   创建目录    ")
        } else {
            Log.e("file", "目录存在")
        }
        return filePath
    }

    /**
     * 保存目录目录到目录
     *
     * @param context
     * @return 目录保存的目录
     */
    fun setHiddenMkdir(context: Context): String {
        var filePath: String? = null
        if (checkSDCard()) {
            filePath = Environment.getExternalStorageDirectory().toString() + File.separator + "sinoyd" + File.separator + ".face"
        } else {
            filePath = context.cacheDir.absolutePath + File.separator + "sinoyd" + File.separator + ".face"
        }
        val file = File(filePath)
        if (!file.exists()) {
            file.mkdirs()
            Log.e("file", "目录不存在   创建目录    ")
        } else {
            Log.e("file", "目录存在")
        }
        return filePath
    }

    /**
     * 获取路径
     *
     * @return
     * @throws IOException
     */
    fun getPath(context: Context, url: String): String {
        var path: String = ""
        try {
            path = FileUtil.setMkdir(context) + File.separator + url.substring(url.lastIndexOf("/") + 1)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return path
    }

    /**
     * 获取文件的大小
     *
     * @param fileSize 文件的大小
     * @return
     */
    fun FormetFileSize(fileSize: Long): String {// 转换文件大小
        val df = DecimalFormat("#.00")
        var fileSizeString = ""
        if (fileSize < 1024) {
            fileSizeString = df.format(fileSize.toDouble()) + "B"
        } else if (fileSize < 1048576) {
            fileSizeString = df.format(fileSize.toDouble() / 1024) + "K"
        } else if (fileSize < 1073741824) {
            fileSizeString = df.format(fileSize.toDouble() / 1048576) + "M"
        } else {
            fileSizeString = df.format(fileSize.toDouble() / 1073741824) + "G"
        }
        return fileSizeString
    }

    /**
     * 根据URI获得文件路径
     */
    @SuppressLint("Recycle")
    fun getImagePath(context: Context, uri: Uri, selection: String?): String? {
        var path: String? = null
        //通过Uri和selection来获取真实的图片路径
        val cursor = context.contentResolver.query(uri, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }


    /**
     * 通过Base64将Bitmap转换成Base64字符串
     * @param bit
     * @return
     */
    fun BitmapToStrByBase64(bit: Bitmap,quality :Int = 80): String {
        val bos = ByteArrayOutputStream()
        bit.compress(Bitmap.CompressFormat.JPEG, quality, bos)//第二个入参表示图片压缩率，如果是100就表示不压缩
        val bytes = bos.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    /**
     * 对图片进行压缩
     * @param srcPath
     * @return
     */
    /**
     * 对图片进行压缩
     * @param srcPath
     * @return
     */
    fun decodeFile(srcPath: String,width:Int): Bitmap {
        val options = BitmapFactory.Options()
        // 获取这个图片的宽和高
        options.inJustDecodeBounds = true
        val bitmap = BitmapFactory.decodeFile(srcPath, options) //此时返回bm为空

        var be = options.outWidth / width
        if (be < 1) {
            be = 1
        }
        options.inJustDecodeBounds = false
        options.inSampleSize = be

        return BitmapFactory.decodeFile(srcPath, options)

    }
}
