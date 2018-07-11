package com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Weekly_inspection;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 作者： scj
 * 创建时间： 2018/5/31
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Weekly_inspection
 */
@Table(name = "FixInstrument")
public class FixInstrument extends Instrument{

    @Column(name = "FaultHappenTime")
    private String FaultHappenTime;

    @Column(name = "FaultDetail")
    private String FaultDetail;

    @Column(name = "FaultFixTime")
    private String FaultFixTime;

    @Column(name = "FixDetail")
    private String FixDetail;

    public String getFaultHappenTime() {
        return FaultHappenTime;
    }

    public void setFaultHappenTime(String faultHappenTime) {
        FaultHappenTime = faultHappenTime;
    }

    public String getFaultDetail() {
        return FaultDetail;
    }

    public void setFaultDetail(String faultDetail) {
        FaultDetail = faultDetail;
    }

    public String getFaultFixTime() {
        return FaultFixTime;
    }

    public void setFaultFixTime(String faultFixTime) {
        FaultFixTime = faultFixTime;
    }

    public String getFixDetail() {
        return FixDetail;
    }

    public void setFixDetail(String fixDetail) {
        FixDetail = fixDetail;
    }
}
