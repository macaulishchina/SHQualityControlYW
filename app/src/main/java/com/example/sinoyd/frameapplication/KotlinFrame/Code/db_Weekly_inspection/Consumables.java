package com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Weekly_inspection;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 作者： scj
 * 创建时间： 2018/6/12
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Weekly_inspection
 */
@Table(name = "Consumables")
public class Consumables {
    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "RowGuid")//任务id
    private String RowGuid;
    @Column(name = "MonitoringinstrumentUid")//仪器id
    private String MonitoringinstrumentUid;
    @Column(name = "PointId")//站点id
    private int PointId;
    @Column(name = "MonitoringPointUid")//站点id
    private String MonitoringPointUid;
    @Column(name = "MonitoringPointName")//站点名称
    private String MonitoringPointName;
    @Column(name = "InstrumentUid")//仪器id
    private String InstrumentUid;
    @Column(name = "InstrumentName")//仪器name
    private String InstrumentName;
    @Column(name = "StanLiquidName")//耗材选项
    private String StanLiquidName;
    @Column(name = "Consumablesresult")//耗材选择结果
    private String Consumablesresult;
    @Column(name = "UsageValue")//用量
    private String UsageValue;
    @Column(name = "Reason")//原因
    private String Reason;
    @Column(name = "RecordStr")//记录
    private String RecordStr;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRowGuid() {
        return RowGuid;
    }

    public void setRowGuid(String rowGuid) {
        RowGuid = rowGuid;
    }

    public String getMonitoringinstrumentUid() {
        return MonitoringinstrumentUid;
    }

    public void setMonitoringinstrumentUid(String monitoringinstrumentUid) {
        MonitoringinstrumentUid = monitoringinstrumentUid;
    }

    public int getPointId() {
        return PointId;
    }

    public void setPointId(int pointId) {
        PointId = pointId;
    }

    public String getMonitoringPointUid() {
        return MonitoringPointUid;
    }

    public void setMonitoringPointUid(String monitoringPointUid) {
        MonitoringPointUid = monitoringPointUid;
    }

    public String getMonitoringPointName() {
        return MonitoringPointName;
    }

    public void setMonitoringPointName(String monitoringPointName) {
        MonitoringPointName = monitoringPointName;
    }

    public String getInstrumentUid() {
        return InstrumentUid;
    }

    public void setInstrumentUid(String instrumentUid) {
        InstrumentUid = instrumentUid;
    }

    public String getInstrumentName() {
        return InstrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        InstrumentName = instrumentName;
    }

    public String getStanLiquidName() {
        return StanLiquidName;
    }

    public void setStanLiquidName(String stanLiquidName) {
        StanLiquidName = stanLiquidName;
    }

    public String getConsumablesresult() {
        return Consumablesresult;
    }

    public void setConsumablesresult(String consumablesresult) {
        Consumablesresult = consumablesresult;
    }

    public String getUsageValue() {
        return UsageValue;
    }

    public void setUsageValue(String usageValue) {
        UsageValue = usageValue;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getRecordStr() {
        return RecordStr;
    }

    public void setRecordStr(String recordStr) {
        RecordStr = recordStr;
    }
}
