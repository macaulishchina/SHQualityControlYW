package com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Weekly_inspection;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 作者： scj
 * 创建时间： 2018/5/31
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Weekly_inspection
 */
@Table(name = "Check2clean")
public class Check2clean {

    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "RowGuid")//任务id
    private String RowGuid;

    @Column(name = "StationID")//点位id
    private String StationID;

    @Column(name = "StationName")//点位名称
    private String StationName;

    @Column(name = "ItemName")//大类
    private String ItemName;

    @Column(name = "MonitorItemType")//小类名称表识code
    private String MonitorItemType;

    @Column(name = "MonitorItemTypeName")//小类名称表识
    private String MonitorItemTypeName;

    @Column(name = "MonitorItemCode")//监测项code
    private String MonitorItemCode;

    @Column(name = "MonitorItemText")//监测项name
    private String MonitorItemText;

    @Column(name = "MonitorItMonitorItemValueemText")//监测项name
    private String MonitorItemValue;//值

    @Column(name = "MonitorReason")//原因
    private String MonitorReason;


    public String getMonitorItemValue() {
        return MonitorItemValue;
    }

    public void setMonitorItemValue(String monitorItemValue) {
        MonitorItemValue = monitorItemValue;
    }

    public String getMonitorReason() {
        return MonitorReason;
    }

    public void setMonitorReason(String monitorReason) {
        MonitorReason = monitorReason;
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

    public String getStationID() {
        return StationID;
    }

    public void setStationID(String stationID) {
        StationID = stationID;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getMonitorItemType() {
        return MonitorItemType;
    }

    public void setMonitorItemType(String monitorItemType) {
        MonitorItemType = monitorItemType;
    }

    public String getMonitorItemTypeName() {
        return MonitorItemTypeName;
    }

    public void setMonitorItemTypeName(String monitorItemTypeName) {
        MonitorItemTypeName = monitorItemTypeName;
    }

    public String getMonitorItemCode() {
        return MonitorItemCode;
    }

    public void setMonitorItemCode(String monitorItemCode) {
        MonitorItemCode = monitorItemCode;
    }

    public String getMonitorItemText() {
        return MonitorItemText;
    }

    public void setMonitorItemText(String monitorItemText) {
        MonitorItemText = monitorItemText;
    }
}
