package com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl

import android.util.Log
import com.example.sinoyd.frameapplication.KotlinFrame.Uitl.FileUtil
import com.sinoyd.Code.Until.HttpListener
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * 作者： scj
 * 创建时间： 2018/5/12
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.Mode
 */


class Networkrequestmodel {

    companion object {
        val GETREQUEST = "GET"
        val POSTREQUEST = "POST"
    }

    var parameter = HashMap<String, String>()

    var parameterrul = ""
    var tag = ""
    var url = ""
    var file : File? = null
    var Method = ""
    var okHttpClient = OkHttpClient()

    init {
        parameter = HashMap()
        parameterrul = ""
        val logInterceptor = HttpLoggingInterceptor(LogUtil())
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY;
        okHttpClient = OkHttpClient.Builder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor)
                .build()
        Log.i("scj", "请求开始ing>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
    }

    fun setMethod(method: String): Networkrequestmodel {
        this.Method = method
        return this
    }

    fun settag(tag: String): Networkrequestmodel {
        this.tag = tag
        return this
    }

    fun seturl(url: String): Networkrequestmodel {
        this.url = url
        return this
    }

    fun setFile(file:File) :Networkrequestmodel{
        this.file = file
        return this
    }

    fun addparam(key: String, value: String): Networkrequestmodel {
        parameter.put(key, value)
        return this
    }


    private fun addPostParameter(requestBody: MultipartBody.Builder) {
        for (item in parameter) {
            requestBody.addFormDataPart(item.key,item.value)
        }
    }
    private fun setparameter() {
        for (item in parameter) {
            parameterrul += "&${item.key}=" + item.value
        }
    }


    fun start(listener: HttpListener) {
        var request: Request? = null
        when (Method) {
        //GET请求方式
            GETREQUEST -> {
                setparameter()
                request = Request.Builder()
                        .url(
                                if (parameter.size > 0) {
                                    url + "?" + parameterrul.substring(1, parameterrul.length)
                                } else {
                                    url
                                }
                        )
                        .build()
            }
        //Post请求方式
            POSTREQUEST -> {
                val requestBody = MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                if(this.file != null){      //如果上传包含图片
                    val fileBody = RequestBody.create(MediaType.parse("image/jpeg"),file)
                    requestBody.addFormDataPart("file", file!!.name,fileBody)
                }
                for (item in parameter) {
                    requestBody.addFormDataPart(item.key,item.value)
                }
                request = Request.Builder()
                .url(url)
                .post(requestBody.build())
                .build()
            }

        }
        //开始发送请求
        try {
            var response = okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                    Log.i("scj", "捕获到的异常    " + e!!.message)
                    listener.onFailure(call!!)
                }

                override fun onResponse(call: Call?, response: Response) {
                    if (response.isSuccessful) {
                        listener.requestSuccess(response, tag)
                    } else {
                        listener.requestFailed(response)
                    }
                }

            })
        } catch (e: Exception) {
            Log.i("scj", "网络请求模块中异常了" + e.message)
            e.printStackTrace()
        }


    }

}