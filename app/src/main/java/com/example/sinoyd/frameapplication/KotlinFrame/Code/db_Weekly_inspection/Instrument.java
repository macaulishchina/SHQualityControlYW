package com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Weekly_inspection;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 作者： scj
 * 创建时间： 2018/5/31
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Weekly_inspection
 */
@Table(name = "Instrument")
public class Instrument {
    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "RowGuid")//任务id
    private String RowGuid;

    @Column(name = "MonitoringinstrumentUid")// uid
    private String MonitoringinstrumentUid;

    @Column(name = "PointId")//站点id
    private int PointId;

    @Column(name = "MonitoringPointUid")//点位名称uid
    private String MonitoringPointUid;

    @Column(name = "MonitoringPointName")//点位名称
    private String MonitoringPointName;

    @Column(name = "InstrumentUid")//仪器id
    private String InstrumentUid;

    @Column(name = "InstrumentName")//仪器名称
    private String InstrumentName;

    @Column(name = "OldProductNumber")//老编号
    private String OldProductNumber;

    @Column(name = "Reason")//原因
    private String Reason;

    @Column(name = "NewProductNumber")//新编号
    private String NewProductNumber;

    @Column(name = "RecordStr")//记录
    private String RecordStr;

    public String getRecordStr() {
        return RecordStr;
    }

    public void setRecordStr(String recordStr) {
        RecordStr = recordStr;
    }

    public String getOldProductNumber() {
        return OldProductNumber;
    }

    public void setOldProductNumber(String oldProductNumber) {
        OldProductNumber = oldProductNumber;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getNewProductNumber() {
        return NewProductNumber;
    }

    public void setNewProductNumber(String newProductNumber) {
        NewProductNumber = newProductNumber;
    }

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
}
