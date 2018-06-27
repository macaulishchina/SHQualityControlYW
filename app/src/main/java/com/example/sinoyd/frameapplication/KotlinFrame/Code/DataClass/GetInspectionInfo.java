package com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass;

import java.util.List;

/**
 * 作者： scj
 * 创建时间： 2018/5/31
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass
 */

public class GetInspectionInfo {
    private List<AroundInspectStatusBean> AroundInspectStatus; //仪器状况
    private List<EquipmentBean> Equipment;//设备状况
    private List<ElectrodeCleaningBean> ElectrodeCleaning;//电ji清理
    private List<SysCleaningBean> SysCleaning;//系统清理
    private List<PipelineCleaningBean> PipelineCleaning;//管路清洗
    private List<InstrumentBean> Instrument;//仪器更换
    private List<StandardSolutionChangeBean> StandardSolutionChange;//实剂标液更换
    private List<ConsumablesBean> Consumables; //耗材


    public List<ConsumablesBean> getConsumables() {
        return Consumables;
    }

    public void setConsumables(List<ConsumablesBean> consumables) {
        Consumables = consumables;
    }

    public List<AroundInspectStatusBean> getAroundInspectStatus() {
        return AroundInspectStatus;
    }

    public void setAroundInspectStatus(List<AroundInspectStatusBean> AroundInspectStatus) {
        this.AroundInspectStatus = AroundInspectStatus;
    }

    public List<EquipmentBean> getEquipment() {
        return Equipment;
    }

    public void setEquipment(List<EquipmentBean> Equipment) {
        this.Equipment = Equipment;
    }

    public List<ElectrodeCleaningBean> getElectrodeCleaning() {
        return ElectrodeCleaning;
    }

    public void setElectrodeCleaning(List<ElectrodeCleaningBean> ElectrodeCleaning) {
        this.ElectrodeCleaning = ElectrodeCleaning;
    }

    public List<SysCleaningBean> getSysCleaning() {
        return SysCleaning;
    }

    public void setSysCleaning(List<SysCleaningBean> SysCleaning) {
        this.SysCleaning = SysCleaning;
    }

    public List<PipelineCleaningBean> getPipelineCleaning() {
        return PipelineCleaning;
    }

    public void setPipelineCleaning(List<PipelineCleaningBean> PipelineCleaning) {
        this.PipelineCleaning = PipelineCleaning;
    }

    public List<InstrumentBean> getInstrument() {
        return Instrument;
    }

    public void setInstrument(List<InstrumentBean> Instrument) {
        this.Instrument = Instrument;
    }

    public List<StandardSolutionChangeBean> getStandardSolutionChange() {
        return StandardSolutionChange;
    }

    public void setStandardSolutionChange(List<StandardSolutionChangeBean> StandardSolutionChange) {
        this.StandardSolutionChange = StandardSolutionChange;
    }

    public static class AroundInspectStatusBean {
        /**
         * StationID :
         * StationName :
         * ItemName : 状况
         * MonitorItemType : 1
         * MonitorItemTypeName : 仪器状况
         * MonitorItemCode : 1
         * MonitorItemText : 五参数
         * MonitorItemValue : 1
         * MonitorReason :
         */

        private String StationID;
        private String StationName;
        private String ItemName;
        private String MonitorItemType;
        private String MonitorItemTypeName;
        private String MonitorItemCode;
        private String MonitorItemText;
        private String MonitorItemValue;
        private String MonitorReason;

        public String getStationID() {
            return StationID;
        }

        public void setStationID(String StationID) {
            this.StationID = StationID;
        }

        public String getStationName() {
            return StationName;
        }

        public void setStationName(String StationName) {
            this.StationName = StationName;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String ItemName) {
            this.ItemName = ItemName;
        }

        public String getMonitorItemType() {
            return MonitorItemType;
        }

        public void setMonitorItemType(String MonitorItemType) {
            this.MonitorItemType = MonitorItemType;
        }

        public String getMonitorItemTypeName() {
            return MonitorItemTypeName;
        }

        public void setMonitorItemTypeName(String MonitorItemTypeName) {
            this.MonitorItemTypeName = MonitorItemTypeName;
        }

        public String getMonitorItemCode() {
            return MonitorItemCode;
        }

        public void setMonitorItemCode(String MonitorItemCode) {
            this.MonitorItemCode = MonitorItemCode;
        }

        public String getMonitorItemText() {
            return MonitorItemText;
        }

        public void setMonitorItemText(String MonitorItemText) {
            this.MonitorItemText = MonitorItemText;
        }

        public String getMonitorItemValue() {
            return MonitorItemValue;
        }

        public void setMonitorItemValue(String MonitorItemValue) {
            this.MonitorItemValue = MonitorItemValue;
        }

        public String getMonitorReason() {
            return MonitorReason;
        }

        public void setMonitorReason(String MonitorReason) {
            this.MonitorReason = MonitorReason;
        }
    }

    public static class EquipmentBean {
        /**
         * StationID :
         * StationName :
         * ItemName : 状况
         * MonitorItemType : 2
         * MonitorItemTypeName : 设备状况
         * MonitorItemCode : 1
         * MonitorItemText : 稳压电源
         * MonitorItemValue : 1
         * MonitorReason :
         */

        private String StationID;
        private String StationName;
        private String ItemName;
        private String MonitorItemType;
        private String MonitorItemTypeName;
        private String MonitorItemCode;
        private String MonitorItemText;
        private String MonitorItemValue;
        private String MonitorReason;

        public String getStationID() {
            return StationID;
        }

        public void setStationID(String StationID) {
            this.StationID = StationID;
        }

        public String getStationName() {
            return StationName;
        }

        public void setStationName(String StationName) {
            this.StationName = StationName;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String ItemName) {
            this.ItemName = ItemName;
        }

        public String getMonitorItemType() {
            return MonitorItemType;
        }

        public void setMonitorItemType(String MonitorItemType) {
            this.MonitorItemType = MonitorItemType;
        }

        public String getMonitorItemTypeName() {
            return MonitorItemTypeName;
        }

        public void setMonitorItemTypeName(String MonitorItemTypeName) {
            this.MonitorItemTypeName = MonitorItemTypeName;
        }

        public String getMonitorItemCode() {
            return MonitorItemCode;
        }

        public void setMonitorItemCode(String MonitorItemCode) {
            this.MonitorItemCode = MonitorItemCode;
        }

        public String getMonitorItemText() {
            return MonitorItemText;
        }

        public void setMonitorItemText(String MonitorItemText) {
            this.MonitorItemText = MonitorItemText;
        }

        public String getMonitorItemValue() {
            return MonitorItemValue;
        }

        public void setMonitorItemValue(String MonitorItemValue) {
            this.MonitorItemValue = MonitorItemValue;
        }

        public String getMonitorReason() {
            return MonitorReason;
        }

        public void setMonitorReason(String MonitorReason) {
            this.MonitorReason = MonitorReason;
        }
    }

    public static class ElectrodeCleaningBean {
        /**
         * StationID :
         * StationName :
         * ItemName : 状况
         * MonitorItemType : 3
         * MonitorItemTypeName : 电极清洗
         * MonitorItemCode : 1
         * MonitorItemText : 五参数
         * MonitorItemValue : 1
         * MonitorReason :
         */

        private String StationID;
        private String StationName;
        private String ItemName;
        private String MonitorItemType;
        private String MonitorItemTypeName;
        private String MonitorItemCode;
        private String MonitorItemText;
        private String MonitorItemValue;
        private String MonitorReason;

        public String getStationID() {
            return StationID;
        }

        public void setStationID(String StationID) {
            this.StationID = StationID;
        }

        public String getStationName() {
            return StationName;
        }

        public void setStationName(String StationName) {
            this.StationName = StationName;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String ItemName) {
            this.ItemName = ItemName;
        }

        public String getMonitorItemType() {
            return MonitorItemType;
        }

        public void setMonitorItemType(String MonitorItemType) {
            this.MonitorItemType = MonitorItemType;
        }

        public String getMonitorItemTypeName() {
            return MonitorItemTypeName;
        }

        public void setMonitorItemTypeName(String MonitorItemTypeName) {
            this.MonitorItemTypeName = MonitorItemTypeName;
        }

        public String getMonitorItemCode() {
            return MonitorItemCode;
        }

        public void setMonitorItemCode(String MonitorItemCode) {
            this.MonitorItemCode = MonitorItemCode;
        }

        public String getMonitorItemText() {
            return MonitorItemText;
        }

        public void setMonitorItemText(String MonitorItemText) {
            this.MonitorItemText = MonitorItemText;
        }

        public String getMonitorItemValue() {
            return MonitorItemValue;
        }

        public void setMonitorItemValue(String MonitorItemValue) {
            this.MonitorItemValue = MonitorItemValue;
        }

        public String getMonitorReason() {
            return MonitorReason;
        }

        public void setMonitorReason(String MonitorReason) {
            this.MonitorReason = MonitorReason;
        }
    }

    public static class SysCleaningBean {
        /**
         * StationID :
         * StationName :
         * ItemName : 状况
         * MonitorItemType : 4
         * MonitorItemTypeName : 系统清洗
         * MonitorItemCode : 1
         * MonitorItemText : 取水管路
         * MonitorItemValue : 1
         * MonitorReason :
         */

        private String StationID;
        private String StationName;
        private String ItemName;
        private String MonitorItemType;
        private String MonitorItemTypeName;
        private String MonitorItemCode;
        private String MonitorItemText;
        private String MonitorItemValue;
        private String MonitorReason;

        public String getStationID() {
            return StationID;
        }

        public void setStationID(String StationID) {
            this.StationID = StationID;
        }

        public String getStationName() {
            return StationName;
        }

        public void setStationName(String StationName) {
            this.StationName = StationName;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String ItemName) {
            this.ItemName = ItemName;
        }

        public String getMonitorItemType() {
            return MonitorItemType;
        }

        public void setMonitorItemType(String MonitorItemType) {
            this.MonitorItemType = MonitorItemType;
        }

        public String getMonitorItemTypeName() {
            return MonitorItemTypeName;
        }

        public void setMonitorItemTypeName(String MonitorItemTypeName) {
            this.MonitorItemTypeName = MonitorItemTypeName;
        }

        public String getMonitorItemCode() {
            return MonitorItemCode;
        }

        public void setMonitorItemCode(String MonitorItemCode) {
            this.MonitorItemCode = MonitorItemCode;
        }

        public String getMonitorItemText() {
            return MonitorItemText;
        }

        public void setMonitorItemText(String MonitorItemText) {
            this.MonitorItemText = MonitorItemText;
        }

        public String getMonitorItemValue() {
            return MonitorItemValue;
        }

        public void setMonitorItemValue(String MonitorItemValue) {
            this.MonitorItemValue = MonitorItemValue;
        }

        public String getMonitorReason() {
            return MonitorReason;
        }

        public void setMonitorReason(String MonitorReason) {
            this.MonitorReason = MonitorReason;
        }
    }

    public static class PipelineCleaningBean {
        /**
         * StationID :
         * StationName :
         * ItemName : 状况
         * MonitorItemType : 5
         * MonitorItemTypeName : 仪表管路清洗
         * MonitorItemCode : 1
         * MonitorItemText : 进样管
         * MonitorItemValue : 1
         * MonitorReason :
         */

        private String StationID;
        private String StationName;
        private String ItemName;
        private String MonitorItemType;
        private String MonitorItemTypeName;
        private String MonitorItemCode;
        private String MonitorItemText;
        private String MonitorItemValue;
        private String MonitorReason;

        public String getStationID() {
            return StationID;
        }

        public void setStationID(String StationID) {
            this.StationID = StationID;
        }

        public String getStationName() {
            return StationName;
        }

        public void setStationName(String StationName) {
            this.StationName = StationName;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String ItemName) {
            this.ItemName = ItemName;
        }

        public String getMonitorItemType() {
            return MonitorItemType;
        }

        public void setMonitorItemType(String MonitorItemType) {
            this.MonitorItemType = MonitorItemType;
        }

        public String getMonitorItemTypeName() {
            return MonitorItemTypeName;
        }

        public void setMonitorItemTypeName(String MonitorItemTypeName) {
            this.MonitorItemTypeName = MonitorItemTypeName;
        }

        public String getMonitorItemCode() {
            return MonitorItemCode;
        }

        public void setMonitorItemCode(String MonitorItemCode) {
            this.MonitorItemCode = MonitorItemCode;
        }

        public String getMonitorItemText() {
            return MonitorItemText;
        }

        public void setMonitorItemText(String MonitorItemText) {
            this.MonitorItemText = MonitorItemText;
        }

        public String getMonitorItemValue() {
            return MonitorItemValue;
        }

        public void setMonitorItemValue(String MonitorItemValue) {
            this.MonitorItemValue = MonitorItemValue;
        }

        public String getMonitorReason() {
            return MonitorReason;
        }

        public void setMonitorReason(String MonitorReason) {
            this.MonitorReason = MonitorReason;
        }
    }

    public static class InstrumentBean {
        /**
         * MonitoringinstrumentUid : 08304E92-4000-4210-9638-4EC2B92A888C
         * PointId : 46
         * MonitoringPointUid : 29FA436B-4A5C-45AF-9DD9-BB91AAED37E0
         * MonitoringPointName : 七莘路
         * InstrumentUid : b0b16014-3257-47ca-8c50-58e83b5dcd2a
         * InstrumentName : 常规五参数监测仪
         */

        private String MonitoringinstrumentUid;
        private int PointId;
        private String MonitoringPointUid;
        private String MonitoringPointName;
        private String InstrumentUid;
        private String InstrumentName;
        private String OldProductNumber;
        private String Reason;
        private String NewProductNumber;
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

        public String getMonitoringinstrumentUid() {
            return MonitoringinstrumentUid;
        }

        public void setMonitoringinstrumentUid(String MonitoringinstrumentUid) {
            this.MonitoringinstrumentUid = MonitoringinstrumentUid;
        }

        public int getPointId() {
            return PointId;
        }

        public void setPointId(int PointId) {
            this.PointId = PointId;
        }

        public String getMonitoringPointUid() {
            return MonitoringPointUid;
        }

        public void setMonitoringPointUid(String MonitoringPointUid) {
            this.MonitoringPointUid = MonitoringPointUid;
        }

        public String getMonitoringPointName() {
            return MonitoringPointName;
        }

        public void setMonitoringPointName(String MonitoringPointName) {
            this.MonitoringPointName = MonitoringPointName;
        }

        public String getInstrumentUid() {
            return InstrumentUid;
        }

        public void setInstrumentUid(String InstrumentUid) {
            this.InstrumentUid = InstrumentUid;
        }

        public String getInstrumentName() {
            return InstrumentName;
        }

        public void setInstrumentName(String InstrumentName) {
            this.InstrumentName = InstrumentName;
        }
    }

    public static class StandardSolutionChangeBean {
        /**
         * MonitoringinstrumentUid : 08304E92-4000-4210-9638-4EC2B92A888C
         * PointId : 46
         * MonitoringPointUid : 29FA436B-4A5C-45AF-9DD9-BB91AAED37E0
         * MonitoringPointName : 七莘路
         * InstrumentUid : b0b16014-3257-47ca-8c50-58e83b5dcd2a
         * InstrumentName : 常规五参数监测仪
         * StanLiquidName :
         * StanLiquidGuid :
         */

        private String MonitoringinstrumentUid;
        private int PointId;
        private String MonitoringPointUid;
        private String MonitoringPointName;
        private String InstrumentUid;
        private String InstrumentName;
        private String StanLiquidName;
        private String StanLiquidGuid;
        private String StandSelectValue;
        private String OldStanLiquidNumber;
        private String NewStanLiquidNumber;
        private String UsageValue;
        private String Reason;
        private String RecordStr;

        public String getRecordStr() {
            return RecordStr;
        }

        public void setRecordStr(String recordStr) {
            RecordStr = recordStr;
        }

        public String getStandSelectValue() {
            return StandSelectValue;
        }

        public void setStandSelectValue(String standSelectValue) {
            StandSelectValue = standSelectValue;
        }

        public String getOldStanLiquidNumber() {
            return OldStanLiquidNumber;
        }

        public void setOldStanLiquidNumber(String oldStanLiquidNumber) {
            OldStanLiquidNumber = oldStanLiquidNumber;
        }

        public String getNewStanLiquidNumber() {
            return NewStanLiquidNumber;
        }

        public void setNewStanLiquidNumber(String newStanLiquidNumber) {
            NewStanLiquidNumber = newStanLiquidNumber;
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

        public String getMonitoringinstrumentUid() {
            return MonitoringinstrumentUid;
        }

        public void setMonitoringinstrumentUid(String MonitoringinstrumentUid) {
            this.MonitoringinstrumentUid = MonitoringinstrumentUid;
        }

        public int getPointId() {
            return PointId;
        }

        public void setPointId(int PointId) {
            this.PointId = PointId;
        }

        public String getMonitoringPointUid() {
            return MonitoringPointUid;
        }

        public void setMonitoringPointUid(String MonitoringPointUid) {
            this.MonitoringPointUid = MonitoringPointUid;
        }

        public String getMonitoringPointName() {
            return MonitoringPointName;
        }

        public void setMonitoringPointName(String MonitoringPointName) {
            this.MonitoringPointName = MonitoringPointName;
        }

        public String getInstrumentUid() {
            return InstrumentUid;
        }

        public void setInstrumentUid(String InstrumentUid) {
            this.InstrumentUid = InstrumentUid;
        }

        public String getInstrumentName() {
            return InstrumentName;
        }

        public void setInstrumentName(String InstrumentName) {
            this.InstrumentName = InstrumentName;
        }

        public String getStanLiquidName() {
            return StanLiquidName;
        }

        public void setStanLiquidName(String StanLiquidName) {
            this.StanLiquidName = StanLiquidName;
        }

        public String getStanLiquidGuid() {
            return StanLiquidGuid;
        }

        public void setStanLiquidGuid(String StanLiquidGuid) {
            this.StanLiquidGuid = StanLiquidGuid;
        }
    }

    public static class ConsumablesBean {
        /**
         * MonitoringinstrumentUid : 137F23E3-C310-403D-8C9C-676E1190D1A5
         * PointId : 113
         * MonitoringPointUid : 250D8B3D-87FB-414C-8837-E0F261A36659
         * MonitoringPointName : 青村
         * InstrumentUid : b0b16014-3257-47ca-8c50-58e83b5dcd2a
         * InstrumentName : 常规五参数监测仪
         * StanLiquidName : 常规五参数监测仪ceshi耗材1$常规五参数监测仪ceshi耗材2
         * Consumablesresult :
         * UsageValue :
         * Reason :
         * RecordStr :
         */

        private String MonitoringinstrumentUid;
        private int PointId;
        private String MonitoringPointUid;
        private String MonitoringPointName;
        private String InstrumentUid;
        private String InstrumentName;
        private String StanLiquidName;
        private String Consumablesresult;
        private String UsageValue;
        private String Reason;
        private String RecordStr;

        public String getMonitoringinstrumentUid() {
            return MonitoringinstrumentUid;
        }

        public void setMonitoringinstrumentUid(String MonitoringinstrumentUid) {
            this.MonitoringinstrumentUid = MonitoringinstrumentUid;
        }

        public int getPointId() {
            return PointId;
        }

        public void setPointId(int PointId) {
            this.PointId = PointId;
        }

        public String getMonitoringPointUid() {
            return MonitoringPointUid;
        }

        public void setMonitoringPointUid(String MonitoringPointUid) {
            this.MonitoringPointUid = MonitoringPointUid;
        }

        public String getMonitoringPointName() {
            return MonitoringPointName;
        }

        public void setMonitoringPointName(String MonitoringPointName) {
            this.MonitoringPointName = MonitoringPointName;
        }

        public String getInstrumentUid() {
            return InstrumentUid;
        }

        public void setInstrumentUid(String InstrumentUid) {
            this.InstrumentUid = InstrumentUid;
        }

        public String getInstrumentName() {
            return InstrumentName;
        }

        public void setInstrumentName(String InstrumentName) {
            this.InstrumentName = InstrumentName;
        }

        public String getStanLiquidName() {
            return StanLiquidName;
        }

        public void setStanLiquidName(String StanLiquidName) {
            this.StanLiquidName = StanLiquidName;
        }

        public String getConsumablesresult() {
            return Consumablesresult;
        }

        public void setConsumablesresult(String Consumablesresult) {
            this.Consumablesresult = Consumablesresult;
        }

        public String getUsageValue() {
            return UsageValue;
        }

        public void setUsageValue(String UsageValue) {
            this.UsageValue = UsageValue;
        }

        public String getReason() {
            return Reason;
        }

        public void setReason(String Reason) {
            this.Reason = Reason;
        }

        public String getRecordStr() {
            return RecordStr;
        }

        public void setRecordStr(String RecordStr) {
            this.RecordStr = RecordStr;
        }
    }
}
