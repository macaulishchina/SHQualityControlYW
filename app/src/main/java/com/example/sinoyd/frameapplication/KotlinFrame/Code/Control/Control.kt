package com.example.sinoyd.frameapplication.KotlinFrame.Code.Control

import android.util.Log
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTask
import org.xutils.DbManager

/**
 * 作者： scj
 * 创建时间： 2018/5/11
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.Control
 */


//根据rowguid取formtask
fun getdataforsqlite(db: DbManager?, rowGuid: String): FormTask {
    try {
        var formtask = db!!.selector(FormTask::class.java).where("RowGuid", "=", rowGuid).findFirst()
        Log.i("scj", "获取【formtask】和【formtaskfactor】成功")
        return formtask
    } catch (e: Exception) {
        e.printStackTrace()
        Log.i("scj", "获取【formtask】和【formtaskfactor】失败")
    }
    return FormTask()
}