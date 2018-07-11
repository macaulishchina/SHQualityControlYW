package com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl

import android.app.Activity
import android.support.v4.app.ActivityCompat

/**
 * created by hu
 * at 2018/7/8
 * in project salmon
 * description 权限管理工具
 */
class PermissionKits(val activity: Activity){

    /**
     * 请求[permissions]中包含的所有权限，[requestCode]为请求的标识编号，[listener]为发送请求时触发的接口，可为 null
     */
    fun request(permissions:Array<String>,requestCode:Int,listener:OnRequestStartListener?){
        listener?.onRequestStart(permissions,requestCode)
        //Todo check permissions
        ActivityCompat.requestPermissions(activity,permissions,requestCode)
    }

    /**
     * 请求权限开始调用接口
     */
    interface OnRequestStartListener{
        fun onRequestStart(permissions: Array<String>, requestCode: Int)
    }
}