package com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Weekly_inspection;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 作者： scj
 * 创建时间： 2018/5/31
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Weekly_inspection
 */
@Table(name = "CheckInstrument")
public class CheckInstrument extends Instrument {


    @Column(name = "LastCheckTime")
    private String LastCheckTime;

    @Column(name = "ThisCheckTime")
    private String ThisCheckTime;

    @Column(name = "CheckDetail")
    private String CheckDetail;

    @Column(name = "IsPass")
    private String IsPass;

    public String getLastCheckTime() {
        return LastCheckTime;
    }

    public void setLastCheckTime(String lastCheckTime) {
        LastCheckTime = lastCheckTime;
    }

    public String getThisCheckTime() {
        return ThisCheckTime;
    }

    public void setThisCheckTime(String thisCheckTime) {
        ThisCheckTime = thisCheckTime;
    }

    public String getCheckDetail() {
        return CheckDetail;
    }

    public void setCheckDetail(String checkDetail) {
        CheckDetail = checkDetail;
    }

    public String getIsPass() {
        return IsPass;
    }

    public void setIsPass(String isPass) {
        IsPass = isPass;
    }
}
