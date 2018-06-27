package com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass;


import java.util.ArrayList;
import java.util.List;

/**
 * 作者： scj
 * 创建时间： 2018/5/8
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass
 */


public class Task {


    /**
     * Result : True
     * message :
     * Task : [{"TaskCode":"Test20180508","PointId":45,"PointName":"南港大桥","TaskName":"质控测试","FormGuid":"417e8fc3-8a07-41c2-bfba-3f270f1cf9b0","FormName":"实样比对","EndTime":"","TaskStatus":"AlreadyDown","TaskStatusName":"已下发","TaskType":"e5a86c8e-87bb-49fd-b7e0-2a85c3c1a570","TaskTypeName":"飞行检查","OperationCompany":"62e03aa2-6079-4a25-a392-a643a5823a6b","UserName":"雷磁","StartDate":"2018/5/8 0:00:00","EndDate":"2018/5/10 23:00:00","RowGuid":"AD9C98EE-A27D-4F5D-BD78-C789153CE66C","TaskData":[{"TaskGuid":"AD9C98EE-A27D-4F5D-BD78-C789153CE66C","PollutantCode":"W01001","StaLiquidName":"9259249C-D547-4DC9-A8C8-0836F972141B"},{"TaskGuid":"AD9C98EE-A27D-4F5D-BD78-C789153CE66C","PollutantCode":"W01010","StaLiquidName":"321002AA-18A5-4B9D-A800-6FB38585562B"}]}]
     */

    private String Result;
    private String message;
    private List<TaskBean> Task = new ArrayList();

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

    public List<TaskBean> getTask() {
        return Task;
    }

    public void setTask(List<TaskBean> Task) {
        this.Task = Task;
    }

    public static class TaskBean {
        /**
         * TaskCode : Test20180508
         * PointId : 45
         * PointName : 南港大桥
         * TaskName : 质控测试
         * FormGuid : 417e8fc3-8a07-41c2-bfba-3f270f1cf9b0
         * FormName : 实样比对
         * EndTime :
         * TaskStatus : AlreadyDown
         * TaskStatusName : 已下发
         * TaskType : e5a86c8e-87bb-49fd-b7e0-2a85c3c1a570
         * TaskTypeName : 飞行检查
         * OperationCompany : 62e03aa2-6079-4a25-a392-a643a5823a6b
         * UserName : 雷磁
         * StartDate : 2018/5/8 0:00:00
         * EndDate : 2018/5/10 23:00:00
         * RowGuid : AD9C98EE-A27D-4F5D-BD78-C789153CE66C
         * TaskData : [{"TaskGuid":"AD9C98EE-A27D-4F5D-BD78-C789153CE66C","PollutantCode":"W01001","StaLiquidName":"9259249C-D547-4DC9-A8C8-0836F972141B"},{"TaskGuid":"AD9C98EE-A27D-4F5D-BD78-C789153CE66C","PollutantCode":"W01010","StaLiquidName":"321002AA-18A5-4B9D-A800-6FB38585562B"}]
         */

        private String TaskCode;
        private int PointId;
        private String PointName;
        private String TaskName;
        private String FormGuid;
        private String FormName;
        private String FormCode;
        private String EndTime;
        private String TaskStatus;
        private String TaskStatusName;
        private String TaskType;
        private String TaskTypeName;
        private String OperationCompany;
        private String UserName;
        private String StartDate;
        private String EndDate;
        private String RowGuid;
        private List<TaskDataBean> TaskData = new ArrayList();


        public String getFormCode() {
            return FormCode;
        }

        public void setFormCode(String formCode) {
            FormCode = formCode;
        }

        public String getTaskCode() {
            return TaskCode;
        }

        public void setTaskCode(String TaskCode) {
            this.TaskCode = TaskCode;
        }

        public int getPointId() {
            return PointId;
        }

        public void setPointId(int PointId) {
            this.PointId = PointId;
        }

        public String getPointName() {
            return PointName;
        }

        public void setPointName(String PointName) {
            this.PointName = PointName;
        }

        public String getTaskName() {
            return TaskName;
        }

        public void setTaskName(String TaskName) {
            this.TaskName = TaskName;
        }

        public String getFormGuid() {
            return FormGuid;
        }

        public void setFormGuid(String FormGuid) {
            this.FormGuid = FormGuid;
        }

        public String getFormName() {
            return FormName;
        }

        public void setFormName(String FormName) {
            this.FormName = FormName;
        }

        public String getEndTime() {
            return EndTime;
        }

        public void setEndTime(String EndTime) {
            this.EndTime = EndTime;
        }

        public String getTaskStatus() {
            return TaskStatus;
        }

        public void setTaskStatus(String TaskStatus) {
            this.TaskStatus = TaskStatus;
        }

        public String getTaskStatusName() {
            return TaskStatusName;
        }

        public void setTaskStatusName(String TaskStatusName) {
            this.TaskStatusName = TaskStatusName;
        }

        public String getTaskType() {
            return TaskType;
        }

        public void setTaskType(String TaskType) {
            this.TaskType = TaskType;
        }

        public String getTaskTypeName() {
            return TaskTypeName;
        }

        public void setTaskTypeName(String TaskTypeName) {
            this.TaskTypeName = TaskTypeName;
        }

        public String getOperationCompany() {
            return OperationCompany;
        }

        public void setOperationCompany(String OperationCompany) {
            this.OperationCompany = OperationCompany;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getStartDate() {
            return StartDate;
        }

        public void setStartDate(String StartDate) {
            this.StartDate = StartDate;
        }

        public String getEndDate() {
            return EndDate;
        }

        public void setEndDate(String EndDate) {
            this.EndDate = EndDate;
        }

        public String getRowGuid() {
            return RowGuid;
        }

        public void setRowGuid(String RowGuid) {
            this.RowGuid = RowGuid;
        }

        public List<TaskDataBean> getTaskData() {
            return TaskData;
        }

        public void setTaskData(List<TaskDataBean> TaskData) {
            this.TaskData = TaskData;
        }

        public static class TaskDataBean {
            /**
             * TaskGuid : AD9C98EE-A27D-4F5D-BD78-C789153CE66C
             * PollutantCode : W01001
             * StaLiquidName : 9259249C-D547-4DC9-A8C8-0836F972141B
             */

            private String TaskGuid;//任务id
            private String PollutantCode;////测试因子
            private String PollutantName;//测试因子名称
            private String Unit;//因子单位
            private String StanValue;//标准值
            private String SurplusAmount;//剩余量
            private String TotalAmount;//标液总量
            private String StanLiquidCode;//标液编号
            private String DilutionMethod;//稀释方式
            private String RemainderUnit;//使用量单位
            private String StaLiquidGuid;//标液编号的GUID
            private String Capacity;//定容体积
            private String CapacityUnit;//定容体积单位
            private String StanvalueUnit;//标准值单位

            private String InstrumentName;//仪器
            private String InstrumentGuid;//仪器GUID

            private String DecimalDigit;//小数位数

            public String getDecimalDigit() {
                return DecimalDigit;
            }

            public void setDecimalDigit(String decimalDigit) {
                DecimalDigit = decimalDigit;
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

            public String getCapacity() {
                return Capacity;
            }

            public void setCapacity(String capacity) {
                Capacity = capacity;
            }

            public String getCapacityUnit() {
                return CapacityUnit;
            }

            public void setCapacityUnit(String capacityUnit) {
                CapacityUnit = capacityUnit;
            }

            public String getStanvalueUnit() {
                return StanvalueUnit;
            }

            public void setStanvalueUnit(String stanvalueUnit) {
                StanvalueUnit = stanvalueUnit;
            }

            public String getStanValue() {
                return StanValue;
            }

            public void setStanValue(String stanValue) {
                StanValue = stanValue;
            }

            public String getTotalAmount() {
                return TotalAmount;
            }

            public void setTotalAmount(String totalAmount) {
                TotalAmount = totalAmount;
            }

            public String getSurplusAmount() {
                return SurplusAmount;
            }

            public void setSurplusAmount(String surplusAmount) {
                SurplusAmount = surplusAmount;
            }

            public String getUnit() {
                return Unit;
            }

            public void setUnit(String unit) {
                Unit = unit;
            }

            public String getPollutantName() {
                return PollutantName;
            }

            public void setPollutantName(String pollutantName) {
                PollutantName = pollutantName;
            }

            public String getTaskGuid() {
                return TaskGuid;
            }

            public void setTaskGuid(String TaskGuid) {
                this.TaskGuid = TaskGuid;
            }

            public String getPollutantCode() {
                return PollutantCode;
            }

            public void setPollutantCode(String PollutantCode) {
                this.PollutantCode = PollutantCode;
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
        }
    }
}
