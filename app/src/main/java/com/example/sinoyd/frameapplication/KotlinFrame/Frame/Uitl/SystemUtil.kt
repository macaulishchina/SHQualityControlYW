package com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import com.example.sinoyd.frameapplication.KotlinFrame.Uitl.px2dip

import com.example.sinoyd.frameapplication.R
import com.sinoyd.Code.Until.SharedPreferencesFactory
import org.jetbrains.anko.act


/**
 * 记录及设置终端与用户的绑定情况 Copyright (c) 2015 江苏远大信息股份有限公司
 *
 * @类型名称：SystemUtil
 * @创建人：刘敏
 * @创建日期：2015-1-27
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
object SystemUtil {
    /**
     * 得到分辨率高度
     */
    var heightPs = -1
    /**
     * 得到分辨率宽度
     */
    var widthPs = -1
    /**
     * 得到屏幕密度
     */
    var densityDpi = -1
    /**
     * 得到X轴密度
     */
    var Xdpi = -1f
    /**
     * 得到Y轴密度
     */
    var Ydpi = -1f
    var versionCode: Int = 0
    var versionName: String = ""
    /**
     * 数据库的版本号
     *
     *
     * 使用SystemVersionCode的前3位作为数据库版本号的标志 每次数据库需要升级时 使前3位的值加1，后3位的值也要加1
     * 如：100000--》101001
     *
     *
     * 后面3位在每发布一个新版本的时候都要加1（数据库不需要升级时则前3位不加1） 如：100000--》100001
     */
    var dbVersionCode: Int = 0

    /***
     * 获取手机model
     */
    val phoneMode: String
        get() = deviceName

    val deviceName: String
        get() {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            val andvoidVersion = Build.VERSION.RELEASE
            return if (model.startsWith(manufacturer)) {
                capitalize(model) + " @ " + andvoidVersion
            } else {
                capitalize(manufacturer) + " " + model + " @ " + andvoidVersion
            }
        }

    /***
     * 得到手机的屏幕基本信息
     *
     * @param context
     */
    fun getScreen(context: Activity) {
        val metrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(metrics)
        widthPs = metrics.widthPixels
        heightPs = metrics.heightPixels
        densityDpi = metrics.densityDpi
        Xdpi = metrics.xdpi
        Ydpi = metrics.ydpi


        SharedPreferencesFactory.savedata(context, "width", widthPs.toString())
        SharedPreferencesFactory.savedata(context, "height", heightPs.toString())

        Log.d("scj", "屏幕宽度（像素）：" + widthPs)
        Log.d("scj", "屏幕高度（像素）：" + heightPs)
        Log.d("scj", "屏幕密度" + densityDpi)
        Log.d("scj", "屏幕【宽】密度" + Xdpi)
        Log.d("scj", "屏幕【高】密度" + Ydpi)
        Log.d("scj", "屏幕宽度（dp）：" + px2dip(context, widthPs.toFloat()))
        Log.d("scj", "屏幕高度（dp）：" + px2dip(context, heightPs.toFloat()))

        getVersion(context)
    }

    /***
     * 获取客户端版本
     *
     * @param context
     * @return
     */
    fun getVersion(context: Context) {
        try {
            val manager = context.packageManager
            val info = manager.getPackageInfo(context.packageName, 0)
            versionName = info.versionName
            versionCode = info.versionCode
            Log.d("scj", "当前版本号：" + versionName)
            Log.d("scj", "当前版本Code：" + versionCode)
        } catch (e: NameNotFoundException) {
            Log.e("scj", "获取版本失败")
            e.printStackTrace()
        }

    }

    private fun capitalize(s: String?): String {
        if (s == null || s.length == 0) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first) + s.substring(1)
        }
    }


    /**
     * 是否已经创建了快捷方式
     *
     * @param context
     * @return
     */
    fun hasShortcut(context: Context): Boolean {
        var isInstallShortcur = false
        val AUTHORITY = "com.android.launcher.settings"
        val CONTENT_URI = Uri.parse("content://$AUTHORITY/favorites?notify=true")
        val c = context.contentResolver.query(CONTENT_URI, arrayOf("title", "iconResource"), "title=?", arrayOf(context.getString(R.string.app_name).trim { it <= ' ' }), null)
        if (null != c && c.count > 0) {
            isInstallShortcur = true
        }
        return isInstallShortcur
    }

    /**
     * 检测某个apk是否已经安装
     *
     * @param context
     * @param packageName
     * @return
     */
    @Throws(NameNotFoundException::class)
    fun checkApkExist(context: Context, packageName: String?): Boolean {
        return if (packageName == null || "" == packageName) false else true
    }

    /**
     * 开启别的apk
     *
     * @param context
     * @param packageName
     */
    fun startOtherApk(context: Context, packageName: String) {
        if (TextUtils.isEmpty(packageName)) {
            return
        }
        try {
            val intent = context.packageManager.getLaunchIntentForPackage(packageName)
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}
