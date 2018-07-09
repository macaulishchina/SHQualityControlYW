package com.example.sinoyd.frameapplication.KotlinFrame.Code.model;

import com.google.gson.annotations.SerializedName;

import org.xutils.db.annotation.Column;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.AbstractMutableList;

/**
 * 作者： hyd
 * 创建时间： 2018/7/6
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.model
 */
public class Site {

    private String pointId ="";
    private String monitoringPointUid="";
    private String name="";
    private String address="";
    private String des="";
    private String region="";
    private String siteType="";
    private String area="";
    private String valley="";
    private String heliu="";
    private String controlType="";
    private String runStatus="";
    private String EQI="";
    private String calEQIType="";
    private String lastDataTime="";
    private String latitude="";
    private String longitude="";

    private List<Factor> factors = new ArrayList<>();

    public void addFactor(Factor factor){
        for(Factor f : factors){
            if(f.getName().equals(factor.getName())){
                f.setUnit(factor.getName());
                f.setValue(factor.getValue());
                return;
            }
        }
        factors.add(factor);
    }

    public Factor getFactor(String name){
        Factor factor = null;
        for(Factor f : factors){
            if(f.getName().equals(name)){
                return f;
            }
        }
        return factor;
    }

    public String getValueWithUnit(String name){
        for(Factor f : factors){
            if(f.getName().equals(name)){
                return f.getValue()+(f.getUnit().equals("")?"":" "+f.getUnit());
            }
        }
        return "";
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getMonitoringPointUid() {
        return monitoringPointUid;
    }

    public void setMonitoringPointUid(String monitoringPointUid) {
        this.monitoringPointUid = monitoringPointUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getValley() {
        return valley;
    }

    public void setValley(String valley) {
        this.valley = valley;
    }

    public String getHeliu() {
        return heliu;
    }

    public void setHeliu(String heliu) {
        this.heliu = heliu;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(String runStatus) {
        this.runStatus = runStatus;
    }

    public String getEQI() {
        return EQI;
    }

    public void setEQI(String EQI) {
        this.EQI = EQI;
    }

    public String getCalEQIType() {
        return calEQIType;
    }

    public void setCalEQIType(String calEQIType) {
        this.calEQIType = calEQIType;
    }

    public String getLastDataTime() {
        return lastDataTime;
    }

    public void setLastDataTime(String lastDataTime) {
        this.lastDataTime = lastDataTime;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<Factor> getFactors() {
        return factors;
    }

    public void setFactors(List<Factor> factors) {
        this.factors = factors;
    }
}
