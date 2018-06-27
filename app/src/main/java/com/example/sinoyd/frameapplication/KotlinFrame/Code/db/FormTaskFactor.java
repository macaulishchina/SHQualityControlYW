package com.example.sinoyd.frameapplication.KotlinFrame.Code.db;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 作者： scj
 * 创建时间： 2018/5/9
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.db
 */

@Table(name = "FormTaskFactor")
public class FormTaskFactor implements IFormTaskFactor {
    @Column(name = "id", isId = true)
    private int id;//id索引
    @Column(name = "StanLiquidCode")
    private String StanLiquidCode = "";//标液编号
    @Column(name = "PollutantCode")
    private String PollutantCode = "";//因子编号
    @Column(name = "PollutantValue")
    private String PollutantValue = "";//测试值
    @Column(name = "PollutantValue2")
    private String PollutantValue2 = "";//测试值
    @Column(name = "PollutantValue3")
    private String PollutantValue3 = "";//测试值
    @Column(name = "PollutantValue4")
    private String PollutantValue4 = "";//测试值
    @Column(name = "PollutantValue5")
    private String PollutantValue5 = "";//测试值
    @Column(name = "PollutantValue6")
    private String PollutantValue6 = "";//测试值
    @Column(name = "StanValue")
    private String StanValue = "";//标准值
    @Column(name = "StanvalueUnit")
    private String StanvalueUnit = "";//标准值单位
    @Column(name = "SurplusAmount")
    private String SurplusAmount = "";//剩余量
    @Column(name = "UseAmount")
    private String UseAmount = "";//标液使用量
    @Column(name = "RemainderUnit")
    private String RemainderUnit = "";//用量单位

    @Column(name = "TaskGuid")
    private String TaskGuid = "";//任务id
    @Column(name = "PollutantName")
    private String PollutantName = "";//因子名称
    @Column(name = "unit")
    private String unit = "";//因子单位
    @Column(name = "DilutionMethod")
    private String DilutionMethod = "";//稀释方式
    @Column(name = "StaLiquidGuid")
    private String StaLiquidGuid = "";//标液编号guid
    @Column(name = "Capacity")
    private String Capacity = "";//定容体积
    @Column(name = "CapacityUnit")
    private String CapacityUnit = "";//定容体积单位


    @Column(name = "AddValue")
    private String AddValue = "";//加标量
    @Column(name = "PollutantValueAdd")
    private String PollutantValueAdd = "";//加标测试值

    @Column(name = "InstrumentName")
    private String InstrumentName = "";//仪器

    @Column(name = "InstrumentGuid")
    private String InstrumentGuid = "";//仪器GUID

    @Column(name = "AverageValue")
    private String AverageValue = "";//平均值

    @Column(name = "StandardDeviation")
    private String StandardDeviation = "";//标准偏差

    @Column(name = "AccuracyValue")
    private String AccuracyValue = "";//准确度

    @Column(name = "PrecisionValue")
    private String PrecisionValue = "";//精密度

    @Column(name = "DecimalDigit")
    private String DecimalDigit = "";//小数位数

    public String getDecimalDigit() {
        return DecimalDigit;
    }

    public void setDecimalDigit(String decimalDigit) {
        DecimalDigit = decimalDigit;
    }

    public String getAverageValue() {
        return AverageValue;
    }

    public void setAverageValue(String averageValue) {
        AverageValue = averageValue;
    }

    public String getStandardDeviation() {
        return StandardDeviation;
    }

    public void setStandardDeviation(String standardDeviation) {
        StandardDeviation = standardDeviation;
    }

    public String getAccuracyValue() {
        return AccuracyValue;
    }

    public void setAccuracyValue(String accuracyValue) {
        AccuracyValue = accuracyValue;
    }

    public String getPrecisionValue() {
        return PrecisionValue;
    }

    public void setPrecisionValue(String precisionValue) {
        PrecisionValue = precisionValue;
    }

    public String getPollutantValue2() {
        return PollutantValue2;
    }

    public void setPollutantValue2(String pollutantValue2) {
        PollutantValue2 = pollutantValue2;
    }

    public String getPollutantValue3() {
        return PollutantValue3;
    }

    public void setPollutantValue3(String pollutantValue3) {
        PollutantValue3 = pollutantValue3;
    }

    public String getPollutantValue4() {
        return PollutantValue4;
    }

    public void setPollutantValue4(String pollutantValue4) {
        PollutantValue4 = pollutantValue4;
    }

    public String getPollutantValue5() {
        return PollutantValue5;
    }

    public void setPollutantValue5(String pollutantValue5) {
        PollutantValue5 = pollutantValue5;
    }

    public String getPollutantValue6() {
        return PollutantValue6;
    }

    public void setPollutantValue6(String pollutantValue6) {
        PollutantValue6 = pollutantValue6;
    }

    public String getInstrumentName() {
        return InstrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        InstrumentName = instrumentName;
    }

    public String getInstrumentGuid() {
        return InstrumentGuid;
    }

    public void setInstrumentGuid(String instrumentGuid) {
        InstrumentGuid = instrumentGuid;
    }

    @NotNull
    @Override
    public String getAddValue() {
        return AddValue;
    }

    @Override
    public void setAddValue(String addValue) {
        AddValue = addValue;
    }

    @NotNull
    @Override
    public String getPollutantValueAdd() {
        return PollutantValueAdd;
    }

    @Override
    public void setPollutantValueAdd(String pollutantValueAdd) {
        PollutantValueAdd = pollutantValueAdd;
    }

    public String getStanValue() {
        return StanValue;
    }

    public void setStanValue(String stanValue) {
        StanValue = stanValue;
    }

    public String getSurplusAmount() {
        return SurplusAmount;
    }

    public void setSurplusAmount(String surplusAmount) {
        SurplusAmount = surplusAmount;
    }

    public String getUseAmount() {
        return UseAmount;
    }

    public void setUseAmount(String useAmount) {
        UseAmount = useAmount;
    }

    public String getPollutantName() {
        return PollutantName;
    }

    public void setPollutantName(String pollutantName) {
        PollutantName = pollutantName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPollutantValue() {
        return PollutantValue;
    }

    public void setPollutantValue(String pollutantValue) {
        PollutantValue = pollutantValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskGuid() {
        return TaskGuid;
    }

    public void setTaskGuid(String taskGuid) {
        TaskGuid = taskGuid;
    }

    public String getPollutantCode() {
        return PollutantCode;
    }

    public void setPollutantCode(String pollutantCode) {
        PollutantCode = pollutantCode;
    }


    public String getStanLiquidCode() {
        return StanLiquidCode;
    }

    public void setStanLiquidCode(String stanLiquidCode) {
        StanLiquidCode = stanLiquidCode;
    }

    public String getDilutionMethod() {
        return DilutionMethod;
    }

    public void setDilutionMethod(String dilutionMethod) {
        DilutionMethod = dilutionMethod;
    }

    public String getRemainderUnit() {
        return RemainderUnit;
    }

    public void setRemainderUnit(String remainderUnit) {
        RemainderUnit = remainderUnit;
    }

    public String getStaLiquidGuid() {
        return StaLiquidGuid;
    }

    public void setStaLiquidGuid(String staLiquidGuid) {
        StaLiquidGuid = staLiquidGuid;
    }

    @NotNull
    @Override
    public String getStanvalueUnit() {
        return StanvalueUnit;
    }

    @Override
    public void setStanvalueUnit(String stanvalueUnit) {
        StanvalueUnit = stanvalueUnit;
    }

    @NotNull
    @Override
    public String getCapacity() {
        return Capacity;
    }

    @Override
    public void setCapacity(String capacity) {
        Capacity = capacity;
    }

    @NotNull
    @Override
    public String getCapacityUnit() {
        return CapacityUnit;
    }

    @Override
    public void setCapacityUnit(String capacityUnit) {
        CapacityUnit = capacityUnit;
    }
}
