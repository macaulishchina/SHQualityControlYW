package com.example.sinoyd.frameapplication.KotlinFrame.Code.jso;

import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTask;
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Performance_assessment.BiaoZhunData;
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Performance_assessment.JianChuXianData;
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Performance_assessment.LingDianData;
import com.google.gson.Gson;


import java.util.List;

/**
 * 作者： scj
 * 创建时间： 2018/6/9
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.jso
 */

public class Jsonxingnengkaohe {
    private FormTask Task;
    private List<BiaoZhunData> bzdata;
    private List<JianChuXianData> jcxdata;
    private List<LingDianData> lddata;

    public FormTask getTask() {
        return Task;
    }

    public void setTask(FormTask task) {
        Task = task;
    }

    public List<BiaoZhunData> getBzdata() {
        return bzdata;
    }

    public void setBzdata(List<BiaoZhunData> bzdata) {
        this.bzdata = bzdata;
    }

    public List<JianChuXianData> getJcxdata() {
        return jcxdata;
    }

    public void setJcxdata(List<JianChuXianData> jcxdata) {
        this.jcxdata = jcxdata;
    }

    public List<LingDianData> getLddata() {
        return lddata;
    }

    public void setLddata(List<LingDianData> lddata) {
        this.lddata = lddata;
    }

    @Override
    public String toString() {
        Gson gs = new Gson();
        return gs.toJson(this);
    }
}
