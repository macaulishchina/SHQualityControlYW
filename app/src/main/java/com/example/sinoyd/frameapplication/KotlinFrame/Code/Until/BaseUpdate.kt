package com.example.sinoyd.frameapplication.KotlinFrame.Code.Until

import android.content.Context
import com.sinoyd.Code.Until.HttpListener
import com.sinoyd.Code.Until.ShowLog4okhttp
import com.sinoyd.Code.Until.showdialog
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.runOnUiThread

/**
 * 作者： scj
 * 创建时间： 2018/5/11
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.Until
 */

open class BaseUpdate(var con: Context) : HttpListener {
    var responsestr = ""
    override fun requestSuccess(response: Response, TAG: String) {
        con.runOnUiThread {
            showdialog(con, "下载", "loadsuccess", "")
        }
        responsestr = response.body()!!.string()
        ShowLog4okhttp("scj").printinfo(response).printJson(responsestr)
    }

    override fun requestFailed(response: Response) {
        con.runOnUiThread {
            showdialog(con, "下载", "loadfail", "")
        }
        ShowLog4okhttp("scj").printinfo(response).printJson(response.body()!!.string())
    }

    override fun onFailure(call: Call) {
        con.runOnUiThread {
            showdialog(con, "下载", "loadfail", "")
        }
        ShowLog4okhttp("scj").printinfo(call)
    }

}