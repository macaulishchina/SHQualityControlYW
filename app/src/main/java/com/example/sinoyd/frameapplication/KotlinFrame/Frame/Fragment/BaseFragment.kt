package com.example.sinoyd.frameapplication.KotlinFrame.Frame.Fragment

import android.support.v4.app.Fragment
import com.sinoyd.Code.Until.HttpListener
import com.sinoyd.Code.Until.ShowLog4okhttp
import okhttp3.Call
import okhttp3.Response

/**查看采样记录**/
open class BaseFragment : Fragment(), HttpListener {

    var responsestr = ""

    override fun requestSuccess(response: Response, TAG: String) {
        responsestr = response.body().string()
        ShowLog4okhttp("scj").printinfo(response).printJson(responsestr)
    }

    override fun onFailure(call: Call) {
        ShowLog4okhttp("scj").printinfo(call)
    }

    override fun requestFailed(response: Response) {
        responsestr = response.body().string()
        ShowLog4okhttp("scj").printinfo(response).printJson(responsestr)
    }

}