package com.example.sinoyd.frameapplication.KotlinFrame.Frame.View

import android.content.Context
import com.xiasuhuei321.loadingdialog.view.LoadingDialog

/**
 * 作者： hyd
 * 创建时间： 2018/7/5
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Frame.View
 */
class WaitingDialog(val context:Context) {
    var dialog:LoadingDialog = LoadingDialog(context)
    init {
        dialog.setLoadingText("加载中")
    }

}