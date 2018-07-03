package com.example.sinoyd.frameapplication.KotlinFrame.Code.jso;

import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTask;
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTaskFactor;
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTaskPicture;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

/**
 * 作者： hyd
 * 创建时间： 2018/7/2
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.jso
 */


public class JsonTaskPicture {

    /**
     *{"picture":{"Cate":"站房环境","LocalCachePath":"file:///storage/emulated/0/DCIM/Camera/IMG_20180416_094155.jpg","PointId":0,"RowGuid":"25222bd7-a5d6-4447-ad41-6fbc6d0bc712","TakeTime":"2018/07/03 03:29:58","TaskCode":"","TaskGuid":"b5ae0af4-e86d-4e56-afa8-9207d3fac20e","UploadTime":"","Username":"本地账号","id":1}}
     */

    private FormTaskPicture picture;

    public FormTaskPicture getPicture() {
        return picture;
    }

    public void setPicture(FormTaskPicture picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        Gson gs = new Gson();
        return gs.toJson(this);
    }


}
