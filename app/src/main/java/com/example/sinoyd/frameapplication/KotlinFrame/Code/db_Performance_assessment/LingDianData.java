package com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Performance_assessment;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 作者： scj
 * 创建时间： 2018/6/7
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Performance_assessment
 */
@Table(name = "LingDianData")
public class LingDianData {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "RowGuid")//任务id
    private String RowGuid;
    @Column(name = "PollutantCode")//因子code
    private String PollutantCode;
    @Column(name = "PollutantName")//因子名称
    private String PollutantName;
    @Column(name = "Unit")//单位
    private String Unit;
    @Column(name = "DecimalDigit")//小数位数
    private Integer DecimalDigit;
    @Column(name = "InstrumentGuid")//仪器GUID
    private String InstrumentGuid;
    @Column(name = "InstrumentName")//仪器名字
    private String InstrumentName;
    
    @Column(name = "ResponseTime")//响应时间
    private String ResponseTime;
    @Column(name = "Qualification")//响应时间是否合格
    private String Qualification;
    @Column(name = "StandardValue")//标准液浓度
    private String StandardValue;
    @Column(name = "RangeValues")//量程值
    private String RangeValues;
    @Column(name = "Frequency")//零点监测次数
    private String Frequency;
    @Column(name = "ActualValue")//零点测试值
    private String ActualValue;
    @Column(name = "DateTime")//时间
    private String DateTime;
    @Column(name = "InitialValue")//零点初始值（前3次均值）
    private String InitialValue;
    @Column(name = "MaxValue")//零点最大值
    private String MaxValue;
    @Column(name = "MinValue")//零点最小值
    private String MinValue;
    @Column(name = "ZeroDrift")  //零点漂移%
    private String ZeroDrift;




    @Column(name = "DateTimeXY")//响应时间
    private String DateTimeXY;
    @Column(name = "StandardValueL")//量程校正液浓度
    private String StandardValueL;
    @Column(name = "RangeValuesL")//量程值
    private String RangeValuesL;
    @Column(name = "FrequencyL")//量程监测次数
    private String FrequencyL;
    @Column(name = "ActualValueL")//量程测试值
    private String ActualValueL;
    @Column(name = "DateTimeL")
    private String DateTimeL;
    @Column(name = "AverageValue")//量程平均值
    private String AverageValue;
    @Column(name = "ZeroValue")//量程零点值
    private String ZeroValue;
    @Column(name = "SpanDrift")//量程漂移
    private String SpanDrift;

    public String getDateTimeXY() {
        return DateTimeXY;
    }

    public void setDateTimeXY(String dateTimeXY) {
        DateTimeXY = dateTimeXY;
    }

    public String getDateTimeL() {
        return DateTimeL;
    }

    public void setDateTimeL(String dateTimeL) {
        DateTimeL = dateTimeL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMinValue() {
        return MinValue;
    }

    public void setMinValue(String minValue) {
        MinValue = minValue;
    }

    public String getRangeValuesL() {
        return RangeValuesL;
    }

    public void setRangeValuesL(String rangeValuesL) {
        RangeValuesL = rangeValuesL;
    }

    public String getRowGuid() {
        return RowGuid;
    }

    public void setRowGuid(String rowGuid) {
        RowGuid = rowGuid;
    }

    public String getPollutantCode() {
        return PollutantCode;
    }

    public void setPollutantCode(String pollutantCode) {
        PollutantCode = pollutantCode;
    }

    public String getPollutantName() {
        return PollutantName;
    }

    public void setPollutantName(String pollutantName) {
        PollutantName = pollutantName;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public Integer getDecimalDigit() {
        return DecimalDigit;
    }

    public void setDecimalDigit(Integer decimalDigit) {
        DecimalDigit = decimalDigit;
    }

    public String getInstrumentGuid() {
        return InstrumentGuid;
    }

    public void setInstrumentGuid(String instrumentGuid) {
        InstrumentGuid = instrumentGuid;
    }

    public String getInstrumentName() {
        return InstrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        InstrumentName = instrumentName;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getFrequency() {
        return Frequency;
    }

    public void setFrequency(String frequency) {
        Frequency = frequency;
    }

    public String getActualValue() {
        return ActualValue;
    }

    public void setActualValue(String actualValue) {
        ActualValue = actualValue;
    }

    public String getStandardValue() {
        return StandardValue;
    }

    public void setStandardValue(String standardValue) {
        StandardValue = standardValue;
    }

    public String getResponseTime() {
        return ResponseTime;
    }

    public void setResponseTime(String responseTime) {
        ResponseTime = responseTime;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }

    public String getInitialValue() {
        return InitialValue;
    }

    public void setInitialValue(String initialValue) {
        InitialValue = initialValue;
    }

    public String getMaxValue() {
        return MaxValue;
    }

    public void setMaxValue(String maxValue) {
        MaxValue = maxValue;
    }

    public String getRangeValues() {
        return RangeValues;
    }

    public void setRangeValues(String rangeValues) {
        RangeValues = rangeValues;
    }

    public String getZeroDrift() {
        return ZeroDrift;
    }

    public void setZeroDrift(String zeroDrift) {
        ZeroDrift = zeroDrift;
    }

    public String getFrequencyL() {
        return FrequencyL;
    }

    public void setFrequencyL(String frequencyL) {
        FrequencyL = frequencyL;
    }

    public String getActualValueL() {
        return ActualValueL;
    }

    public void setActualValueL(String actualValueL) {
        ActualValueL = actualValueL;
    }

    public String getStandardValueL() {
        return StandardValueL;
    }

    public void setStandardValueL(String standardValueL) {
        StandardValueL = standardValueL;
    }

    public String getAverageValue() {
        return AverageValue;
    }

    public void setAverageValue(String averageValue) {
        AverageValue = averageValue;
    }

    public String getZeroValue() {
        return ZeroValue;
    }

    public void setZeroValue(String zeroValue) {
        ZeroValue = zeroValue;
    }

    public String getSpanDrift() {
        return SpanDrift;
    }

    public void setSpanDrift(String spanDrift) {
        SpanDrift = spanDrift;
    }
}
