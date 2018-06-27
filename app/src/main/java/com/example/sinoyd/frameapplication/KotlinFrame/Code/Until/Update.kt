package com.example.sinoyd.frameapplication.KotlinFrame.Code.Until

import android.content.Context
import android.os.Looper
import android.util.Log
import com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass.PointInfo
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dataclass.gson
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.Networkrequestmodel
import com.example.sinoyd.jiaxingywapplication.Myapplication
import com.sinoyd.Code.Until.*
import okhttp3.Response
import org.jetbrains.anko.toast
import org.xutils.DbManager
import org.xutils.x

/**
 * 作者： scj
 * 创建时间： 2018/5/10
 * 版权： 江苏远大信息股份有限公司
 * 描述： 用于一些基础数据更新
 *
 * 比如：站点信息，因子信息
 */

class Update(con: Context) : BaseUpdate(con) {


    var pointInfo: PointInfo = PointInfo()
    var myapplication: Myapplication = Myapplication()
    var db: DbManager? = null

    init {
        db = x.getDb(myapplication.getDaoConfig())
    }

    fun startupdate() {
        //下载站点信息
        getPtInfo()
    }


    //下载站点信息
    fun getPtInfo() {
        //下载站点信息
        showdialog(con, "下载", "loadshow", "站点")
        var ntrequest = Networkrequestmodel()
        ntrequest.setMethod(Networkrequestmodel.GETREQUEST)
                .settag("getPointInfo")
                .seturl(Networkrequestaddress.URL_PointInfo)
                .addparam("userGuid", SharedPreferencesFactory.getdata(con, "RowGuid"))
                .start(this)
    }


    override fun requestSuccess(response: Response, TAG: String) {
        super.requestSuccess(response, TAG)
        when (TAG) {
        //获取站点信息
            "getPointInfo" -> {
                try {
                    pointInfo = gson.fromJson(responsestr, PointInfo::class.java)
                    if (response.isSuccessful) {
                        if (pointInfo.result == "True") {
                            SharedPreferencesFactory.savedata(con, "firstinstall", "安装过了")
                            try {
                                db!!.save(pointInfo.pointData)
                                Log.i("scj", "站点信息保存成功")
                            } catch (e: Exception) {
                                Log.i("scj", "站点信息保存失败")
                            }
                        } else {
                            Looper.prepare()
                            con.toast(pointInfo.message)
                            Looper.loop()
                        }
                    } else {
                        Looper.prepare()
                        con.toast(pointInfo.message)
                        Looper.loop()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }
    }



}









