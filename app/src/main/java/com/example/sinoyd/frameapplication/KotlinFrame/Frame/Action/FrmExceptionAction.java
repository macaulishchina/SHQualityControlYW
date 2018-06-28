package com.example.sinoyd.frameapplication.KotlinFrame.Frame.Action;

import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.IOHelp;
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.LogUtil;

/**
 * 作者： 王一凡
 * 创建时间： 2017/9/5
 * 版权： 江苏远大信息股份有限公司
 * 描述：
 */
public class FrmExceptionAction {
    public static void saveCrashLog(Throwable throwable){

        String expStr = IOHelp.throwException2String(throwable);

        LogUtil.writeLogThread("ERROR-->",expStr);
    }
}
