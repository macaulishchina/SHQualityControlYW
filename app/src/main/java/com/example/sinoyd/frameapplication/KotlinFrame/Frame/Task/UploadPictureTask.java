package com.example.sinoyd.frameapplication.KotlinFrame.Frame.Task;

import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.HttpUtil;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 作者： hyd
 * 创建时间： 2018/7/3
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Frame.Task
 */
public class UploadPictureTask extends BaseTask{
    String url;
    Map<String,String> params;
    File file;
    String mimeType;

    public UploadPictureTask(int taskId, BaseTaskCallBack callBack, String url, Map<String, String> params, File file, String mimeType) {
        super(taskId, callBack);
        this.url = url;
        this.params = params;
        this.file = file;
        this.mimeType = mimeType;
    }

    @Override
    public Object execute() {
        try {
            return HttpUtil.post(url,params,file,mimeType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
