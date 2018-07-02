package com.example.sinoyd.frameapplication.KotlinFrame.UI

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.sinoyd.Code.Until.HttpListener
import com.sinoyd.Code.Until.ShowLog4okhttp
import com.sinoyd.Code.Until.showdialog
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.toast

/**
 * 作者： scj
 * 创建时间： 2018/5/4
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.UI
 */


open class BaseActivity : AppCompatActivity(), HttpListener {


    var responsestr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        //设置横屏
        if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        super.onCreate(savedInstanceState)
    }


    override fun requestSuccess(response: Response, TAG: String) {
        this.runOnUiThread {
            showdialog( "loadsuccess")
        }

        responsestr = response.body()!!.string()
        ShowLog4okhttp("scj").printinfo(response).printJson(responsestr)
    }

    override fun requestFailed(response: Response) {
        this.runOnUiThread {
            showdialog("loadfail")
        }
        responsestr = response.body()!!.string()
        ShowLog4okhttp("scj").printinfo(response).printJson(responsestr)
        Looper.prepare()
        toast("请求失败")
        Looper.loop()
    }

    override fun onFailure(call: Call) {
        this.runOnUiThread {
            showdialog( "loadfail")
        }
        ShowLog4okhttp("scj").printinfo(call)
        Looper.prepare()
        toast("请求出错")
        Looper.loop()
    }

    override fun onDestroy() {
        super.onDestroy()
        showdialog( "loaddismiss")
    }


}
