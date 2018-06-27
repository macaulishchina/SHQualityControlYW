package com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Performance_assessment;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 作者： scj
 * 创建时间： 2018/6/7
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Performance_assessment
 */
@Table(name = "JianChuXianData")
public class JianChuXianData {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "RowGuid")//任务id
    private String RowGuid;
    @Column(name = "PollutantCode")//因子code
    private String PollutantCode;
    @Column(name = "PollutantName")//因子name
    private String PollutantName;
    @Column(name = "Unit")//单位
    private String Unit;
    @Column(name = "DecimalDigit")//小数位数
    private Integer DecimalDigit;
    @Column(name = "InstrumentGuid")//仪器GUID
    private String InstrumentGuid;
    @Column(name = "InstrumentName")//仪器名称
    private String InstrumentName;

    @Column(name = "StandardValue")//检出限空白溶液或低浓度标准溶
    private String StandardValue;
    @Column(name = "Frequency")//检出限监测次数   测试次数
    private String Frequency;
    @Column(name = "ActualValue")//检出限测试值   测试值
    private String ActualValue = "";
    @Column(name = "DateTime")//时间
    private String DateTime;
    @Column(name = "AverageValue")//检出限平均值
    private String AverageValue;
    @Column(name = "StandardDeviation")//检出限标准偏差
    private String StandardDeviation;
    @Column(name = "DetectionLimit") //最低检出限
    private String DetectionLimit;



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

    public String getDetectionLimit() {
        return DetectionLimit;
    }

    public void setDetectionLimit(String detectionLimit) {
        DetectionLimit = detectionLimit;
    }
}
