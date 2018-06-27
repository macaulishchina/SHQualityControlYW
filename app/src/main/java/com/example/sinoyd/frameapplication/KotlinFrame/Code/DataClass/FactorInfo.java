package com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass;

import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.IFormTaskFactor;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;
import org.xutils.db.annotation.Column;

import java.util.List;

/**
 * 作者： scj
 * 创建时间： 2018/5/11
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass
 */


public class FactorInfo {

    /**
     * Result : True
     * message :
     * PollutantData : [{"UserUid":"6619344b-50b4-4c97-9a97-84cbce2b97ec","MonitoringPointUid":"788C5599-6C13-45F1-9735-D1A8F4FD0E12","PointId":5,"MonitoringPointName":"长兴岛","PollutantUid":"38EA62F6-724B-4CC3-B5AA-632266370D28","PollutantCode":"w02003","PollutantName":"粪大肠菌群","DecimalDigit":0,"EnableCustomOrNot":true,"MeasureUnit":"个/L","Category":"生物指标"},{"UserUid":"6619344b-50b4-4c97-9a97-84cbce2b97ec","MonitoringPointUid":"788C5599-6C13-45F1-9735-D1A8F4FD0E12","PointId":5,"MonitoringPointName":"长兴岛","PollutantUid":"35AA3DBC-C594-4F81-B10A-651F7C5C05FC","PollutantCode":"w22001","PollutantName":"石油类","DecimalDigit":3,"EnableCustomOrNot":true,"MeasureUnit":"mg/L","Category":"常规监测项"},{"UserUid":"6619344b-50b4-4c97-9a97-84cbce2b97ec","MonitoringPointUid":"788C5599-6C13-45F1-9735-D1A8F4FD0E12","PointId":5,"MonitoringPointName":"长兴岛","PollutantUid":"2C7F0091-C456-43BA-9F28-E3619B033B78","PollutantCode":"w23002","PollutantName":"挥发酚","DecimalDigit":3,"EnableCustomOrNot":true,"MeasureUnit":"mg/L","Category":"其他"},{"UserUid":"6619344b-50b4-4c97-9a97-84cbce2b97ec","MonitoringPointUid":"788C5599-6C13-45F1-9735-D1A8F4FD0E12","PointId":5,"MonitoringPointName":"长兴岛","PollutantUid":"B86CFB83-0519-422D-BE9E-7E7438BFE334","PollutantCode":"w24004","PollutantName":"三氯甲烷","DecimalDigit":3,"EnableCustomOrNot":true,"MeasureUnit":"μg/L","Category":"Vocs"},{"UserUid":"6619344b-50b4-4c97-9a97-84cbce2b97ec","MonitoringPointUid":"788C5599-6C13-45F1-9735-D1A8F4FD0E12","PointId":5,"MonitoringPointName":"长兴岛","PollutantUid":"89203CDD-52CE-4AFC-8CBC-00F9CC0D104A","PollutantCode":"w24017","PollutantName":"1,2-二氯乙烷","DecimalDigit":3,"EnableCustomOrNot":true,"MeasureUnit":"μg/L","Category":"Vocs"},{"UserUid":"6619344b-50b4-4c97-9a97-84cbce2b97ec","MonitoringPointUid":"788C5599-6C13-45F1-9735-D1A8F4FD0E12","PointId":5,"MonitoringPointName":"长兴岛","PollutantUid":"89974E8D-17A8-4774-A977-1BB9F46DDFCA","PollutantCode":"w24027","PollutantName":"1,2-二氯丙烷","DecimalDigit":3,"EnableCustomOrNot":true,"MeasureUnit":"μg/L","Category":"Vocs"},{"UserUid":"6619344b-50b4-4c97-9a97-84cbce2b97ec","MonitoringPointUid":"788C5599-6C13-45F1-9735-D1A8F4FD0E12","PointId":5,"MonitoringPointName":"长兴岛","PollutantUid":"BC89D6D4-20E5-4550-81BB-DD6BC10FF0C8","PollutantCode":"w24048","PollutantName":"顺-1,2-二氯乙烯","DecimalDigit":3,"EnableCustomOrNot":true,"MeasureUnit":"μg/L","Category":"Vocs"},{"UserUid":"6619344b-50b4-4c97-9a97-84cbce2b97ec","MonitoringPointUid":"788C5599-6C13-45F1-9735-D1A8F4FD0E12","PointId":5,"MonitoringPointName":"长兴岛","PollutantUid":"FFCF1499-A64F-4974-B8FC-AF993488B386","PollutantCode":"w24049","PollutantName":"三氯乙烯","DecimalDigit":3,"EnableCustomOrNot":true,"MeasureUnit":"μg/L","Category":"Vocs"},{"UserUid":"6619344b-50b4-4c97-9a97-84cbce2b97ec","MonitoringPointUid":"788C5599-6C13-45F1-9735-D1A8F4FD0E12","PointId":5,"MonitoringPointName":"长兴岛","PollutantUid":"F48CA7EC-03F0-4AFF-9C3D-32CF4CA88D41","PollutantCode":"w24050","PollutantName":"四氯乙烯","DecimalDigit":3,"EnableCustomOrNot":true,"MeasureUnit":"μg/L","Category":"Vocs"},{"UserUid":"6619344b-50b4-4c97-9a97-84cbce2b97ec","MonitoringPointUid":"788C5599-6C13-45F1-9735-D1A8F4FD0E12","PointId":5,"MonitoringPointName":"长兴岛","PollutantUid":"9E98F7AF-CC7D-46CC-AE67-8ED2309182AD","PollutantCode":"w25002","PollutantName":"苯","DecimalDigit":3,"EnableCustomOrNot":true,"MeasureUnit":"μg/L","Category":"Vocs"},{"UserUid":"6619344b-50b4-4c97-9a97-84cbce2b97ec","MonitoringPointUid":"788C5599-6C13-45F1-9735-D1A8F4FD0E12","PointId":5,"MonitoringPointName":"长兴岛","PollutantUid":"F5F47654-123F-41A7-9BCD-5A8A2AA1A675","PollutantCode":"w25003","PollutantName":"甲苯","DecimalDigit":3,"EnableCustomOrNot":true,"MeasureUnit":"μg/L","Category":"Vocs"},{"UserUid":"6619344b-50b4-4c97-9a97-84cbce2b97ec","MonitoringPointUid":"788C5599-6C13-45F1-9735-D1A8F4FD0E12","PointId":5,"MonitoringPointName":"长兴岛","PollutantUid":"D1D83371-309F-45D0-B28C-F0B3B658F40C","PollutantCode":"w25004","PollutantName":"乙苯","DecimalDigit":3,"EnableCustomOrNot":true,"MeasureUnit":"μg/L","Category":"Vocs"},{"UserUid":"6619344b-50b4-4c97-9a97-84cbce2b97ec","MonitoringPointUid":"788C5599-6C13-45F1-9735-D1A8F4FD0E12","PointId":5,"MonitoringPointName":"长兴岛","PollutantUid":"D8383708-A37A-4A2F-991C-C24E5720C2CC","PollutantCode":"w25006","PollutantName":"邻二甲苯","DecimalDigit":3,"EnableCustomOrNot":true,"MeasureUnit":"μg/L","Category":"Vocs"},{"UserUid":"6619344b-50b4-4c97-9a97-84cbce2b97ec","MonitoringPointUid":"788C5599-6C13-45F1-9735-D1A8F4FD0E12","PointId":5,"MonitoringPointName":"长兴岛","PollutantUid":"B44E4CC9-830D-40C0-AA37-371F32C34190","PollutantCode":"w25010","PollutantName":"氯苯","DecimalDigit":3,"EnableCustomOrNot":true,"MeasureUnit":"μg/L","Category":"Vocs"},{"UserUid":"6619344b-50b4-4c97-9a97-84cbce2b97ec","MonitoringPointUid":"788C5599-6C13-45F1-9735-D1A8F4FD0E12","PointId":5,"MonitoringPointName":"长兴岛","PollutantUid":"884034BB-9E4A-4CB9-97CB-0AB4057BB2D6","PollutantCode":"w25011","PollutantName":"1,2-二氯苯","DecimalDigit":3,"EnableCustomOrNot":true,"MeasureUnit":"μg/L","Category":"Vocs"},{"UserUid":"6619344b-50b4-4c97-9a97-84cbce2b97ec","MonitoringPointUid":"788C5599-6C13-45F1-9735-D1A8F4FD0E12","PointId":5,"MonitoringPointName":"长兴岛","PollutantUid":"9AE774CD-8EA0-4F81-88A8-2DC36E41C2D9","PollutantCode":"w25013","PollutantName":"1,4-二氯苯","DecimalDigit":3,"EnableCustomOrNot":true,"MeasureUnit":"μg/L","Category":"Vocs"},{"UserUid":"6619344b-50b4-4c97-9a97-84cbce2b97ec","MonitoringPointUid":"788C5599-6C13-45F1-9735-D1A8F4FD0E12","PointId":5,"MonitoringPointName":"长兴岛","PollutantUid":"D6A5AD2D-ED7F-4A49-AF21-361D74A2B775","PollutantCode":"w25034","PollutantName":"异丙苯","DecimalDigit":3,"EnableCustomOrNot":true,"MeasureUnit":"μg/L","Category":"Vocs"},{"UserUid":"6619344b-50b4-4c97-9a97-84cbce2b97ec","MonitoringPointUid":"788C5599-6C13-45F1-9735-D1A8F4FD0E12","PointId":5,"MonitoringPointName":"长兴岛","PollutantUid":"EC0A45C6-5A07-4AA2-84A7-D348FB26868C","PollutantCode":"w25038","PollutantName":"苯乙烯","DecimalDigit":3,"EnableCustomOrNot":true,"MeasureUnit":"μg/L","Category":"Vocs"},{"UserUid":"6619344b-50b4-4c97-9a97-84cbce2b97ec","MonitoringPointUid":"788C5599-6C13-45F1-9735-D1A8F4FD0E12","PointId":5,"MonitoringPointName":"长兴岛","PollutantUid":"CE24397E-2938-4B39-BDB9-0F7A9FF9778D","PollutantCode":"w98736","PollutantName":"二氯甲烷","DecimalDigit":3,"EnableCustomOrNot":true,"MeasureUnit":"μg/L","Category":"Vocs"},{"UserUid":"6619344b-50b4-4c97-9a97-84cbce2b97ec","MonitoringPointUid":"788C5599-6C13-45F1-9735-D1A8F4FD0E12","PointId":5,"MonitoringPointName":"长兴岛","PollutantUid":"F6313AD5-AC1D-4D0D-86C3-F3D77615F8AE","PollutantCode":"w98752","PollutantName":"反-1,2-二氯乙烯","DecimalDigit":3,"EnableCustomOrNot":true,"MeasureUnit":"μg/L","Category":"Vocs"},{"UserUid":"6619344b-50b4-4c97-9a97-84cbce2b97ec","MonitoringPointUid":"788C5599-6C13-45F1-9735-D1A8F4FD0E12","PointId":5,"MonitoringPointName":"长兴岛","PollutantUid":"6C8E6724-7DCD-4D8D-92E5-B492224DE56F","PollutantCode":"w98754","PollutantName":"间对二甲苯","DecimalDigit":3,"EnableCustomOrNot":true,"MeasureUnit":"μg/L","Category":"Vocs"}]
     */

    private String Result;
    private String message;
    private List<PollutantDataBean> PollutantData;

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PollutantDataBean> getPollutantData() {
        return PollutantData;
    }

    public void setPollutantData(List<PollutantDataBean> PollutantData) {
        this.PollutantData = PollutantData;
    }

    public static class PollutantDataBean implements IFormTaskFactor {
        /**
         * UserUid : 6619344b-50b4-4c97-9a97-84cbce2b97ec
         * MonitoringPointUid : 788C5599-6C13-45F1-9735-D1A8F4FD0E12
         * PointId : 5
         * MonitoringPointName : 长兴岛
         * PollutantUid : 38EA62F6-724B-4CC3-B5AA-632266370D28
         * PollutantCode : w02003
         * PollutantName : 粪大肠菌群
         * DecimalDigit : 0
         * EnableCustomOrNot : true
         * MeasureUnit : 个/L
         * Category : 生物指标
         */

        private String TaskGuid = "";
        private String PollutantCode = "";
        private String PollutantValue = "";
        private String PollutantValue2 = "";
        private String PollutantValue3 = "";
        private String PollutantValue4 = "";
        private String PollutantValue5 = "";
        private String PollutantValue6 = "";
        private String PollutantName = "";
        @SerializedName("MeasureUnit")
        private String unit = "";


        @NotNull
        @Override
        public String getPollutantValue2() {
            return PollutantValue2;
        }

        @Override
        public void setPollutantValue2(String pollutantValue2) {
            PollutantValue2 = pollutantValue2;
        }

        @NotNull
        @Override
        public String getPollutantValue3() {
            return PollutantValue3;
        }

        @Override
        public void setPollutantValue3(String pollutantValue3) {
            PollutantValue3 = pollutantValue3;
        }

        @NotNull
        @Override
        public String getPollutantValue4() {
            return PollutantValue4;
        }

        @Override
        public void setPollutantValue4(String pollutantValue4) {
            PollutantValue4 = pollutantValue4;
        }

        @NotNull
        @Override
        public String getPollutantValue5() {
            return PollutantValue5;
        }

        @Override
        public void setPollutantValue5(String pollutantValue5) {
            PollutantValue5 = pollutantValue5;
        }

        @NotNull
        @Override
        public String getPollutantValue6() {
            return PollutantValue6;
        }

        @Override
        public void setPollutantValue6(String pollutantValue6) {
            PollutantValue6 = pollutantValue6;
        }

        private String StanLiquidCode = "";
        private String DilutionMethod = "";
        private String RemainderUnit = "";
        private String StaLiquidGuid = "";

        private String UserUid = "";
        private String MonitoringPointUid = "";
        private int PointId;
        private String MonitoringPointName = "";
        private String PollutantUid = "";
        private boolean EnableCustomOrNot;

        private String StanValue = "";
        private String SurplusAmount = "";
        private String UseAmount = "";


        private String AddValue = "";
        private String PollutantValueAdd = "";


        private String Capacity = "";//定容体积
        private String CapacityUnit = "";//定容体积单位
        private String StanvalueUnit = "";//标准值单位

        private String InstrumentName = "";//仪器
        private String InstrumentGuid = "";//仪器GUID


        private String AverageValue = "";//平均值

        private String StandardDeviation = "";//标准偏差

        private String AccuracyValue = "";//准确度

        private String PrecisionValue = "";//精密度

        private String DecimalDigit = "";//小数位数

        @NotNull
        @Override
        public String getDecimalDigit() {
            return DecimalDigit;
        }

        @Override
        public void setDecimalDigit(String decimalDigit) {
            DecimalDigit = decimalDigit;
        }

        @NotNull
        @Override
        public String getAverageValue() {
            return AverageValue;
        }

        @Override
        public void setAverageValue(String averageValue) {
            AverageValue = averageValue;
        }

        @NotNull
        @Override
        public String getStandardDeviation() {
            return StandardDeviation;
        }

        @Override
        public void setStandardDeviation(String standardDeviation) {
            StandardDeviation = standardDeviation;
        }

        @NotNull
        @Override
        public String getAccuracyValue() {
            return AccuracyValue;
        }

        @Override
        public void setAccuracyValue(String accuracyValue) {
            AccuracyValue = accuracyValue;
        }

        @NotNull
        @Override
        public String getPrecisionValue() {
            return PrecisionValue;
        }

        @Override
        public void setPrecisionValue(String precisionValue) {
            PrecisionValue = precisionValue;
        }

        @NotNull
        @Override
        public String getInstrumentName() {
            return InstrumentName;
        }

        @Override
        public void setInstrumentName(String instrumentName) {
            InstrumentName = instrumentName;
        }

        @NotNull
        @Override
        public String getInstrumentGuid() {
            return InstrumentGuid;
        }

        @Override
        public void setInstrumentGuid(String instrumentGuid) {
            InstrumentGuid = instrumentGuid;
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

        @NotNull
        @Override
        public String getUseAmount() {
            return UseAmount;
        }

        @Override
        public void setUseAmount(String useAmount) {
            UseAmount = useAmount;
        }

        @NotNull
        @Override
        public String getStanValue() {
            return StanValue;
        }

        @Override
        public void setStanValue(String stanValue) {
            StanValue = stanValue;
        }

        @NotNull
        @Override
        public String getSurplusAmount() {
            return SurplusAmount;
        }

        @Override
        public void setSurplusAmount(String surplusAmount) {
            SurplusAmount = surplusAmount;
        }

        private String Category;

        public String getUserUid() {
            return UserUid;
        }

        public void setUserUid(String UserUid) {
            this.UserUid = UserUid;
        }

        public String getMonitoringPointUid() {
            return MonitoringPointUid;
        }

        public void setMonitoringPointUid(String MonitoringPointUid) {
            this.MonitoringPointUid = MonitoringPointUid;
        }

        public int getPointId() {
            return PointId;
        }

        public void setPointId(int PointId) {
            this.PointId = PointId;
        }

        public String getMonitoringPointName() {
            return MonitoringPointName;
        }

        public void setMonitoringPointName(String MonitoringPointName) {
            this.MonitoringPointName = MonitoringPointName;
        }

        public String getPollutantUid() {
            return PollutantUid;
        }

        public void setPollutantUid(String PollutantUid) {
            this.PollutantUid = PollutantUid;
        }

        public String getPollutantCode() {
            return PollutantCode;
        }

        public void setPollutantCode(String PollutantCode) {
            this.PollutantCode = PollutantCode;
        }

        public String getPollutantName() {
            return PollutantName;
        }

        public void setPollutantName(String PollutantName) {
            this.PollutantName = PollutantName;
        }


        public boolean isEnableCustomOrNot() {
            return EnableCustomOrNot;
        }

        public void setEnableCustomOrNot(boolean EnableCustomOrNot) {
            this.EnableCustomOrNot = EnableCustomOrNot;
        }


        public String getCategory() {
            return Category;
        }

        public void setCategory(String Category) {
            this.Category = Category;
        }

        @NotNull
        @Override
        public String getTaskGuid() {
            return TaskGuid;
        }

        @Override
        public void setTaskGuid(String taskGuid) {
            TaskGuid = taskGuid;
        }

        @NotNull
        @Override
        public String getPollutantValue() {
            return PollutantValue;
        }

        @Override
        public void setPollutantValue(String pollutantValue) {
            PollutantValue = pollutantValue;
        }

        @NotNull
        @Override
        public String getUnit() {
            return unit;
        }

        @Override
        public void setUnit(String unit) {
            this.unit = unit;
        }

        @NotNull
        @Override
        public String getStanLiquidCode() {
            return StanLiquidCode;
        }

        @Override
        public void setStanLiquidCode(String stanLiquidCode) {
            StanLiquidCode = stanLiquidCode;
        }

        @NotNull
        @Override
        public String getDilutionMethod() {
            return DilutionMethod;
        }

        @Override
        public void setDilutionMethod(String dilutionMethod) {
            DilutionMethod = dilutionMethod;
        }

        @NotNull
        @Override
        public String getRemainderUnit() {
            return RemainderUnit;
        }

        @Override
        public void setRemainderUnit(String remainderUnit) {
            RemainderUnit = remainderUnit;
        }

        @NotNull
        @Override
        public String getStaLiquidGuid() {
            return StaLiquidGuid;
        }

        @Override
        public void setStaLiquidGuid(String staLiquidGuid) {
            StaLiquidGuid = staLiquidGuid;
        }


    }
}
