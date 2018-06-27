package com.example.sinoyd.frameapplication.KotlinFrame.Code.db;


import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * 作者： scj
 * 创建时间： 2018/5/9
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.db
 */

@Table(name = "FormTask")
public class FormTask {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "formtype")
    private String formtype;
    @Column(name = "RowGuid")
    private String RowGuid = "";
    @Column(name = "OperationCompany")
    private String OperationCompany = "";
    @Column(name = "TaskName")
    private String TaskName = "";
    @Column(name = "PointId")
    private int PointId;
    @Column(name = "FormGuid")
    private String FormGuid = "";
    @Column(name = "FormCode")
    private String FormCode = "";
    @Column(name = "EndTime")
    private String EndTime = "";
    @Column(name = "StartDate")
    private String StartDate = "";
    @Column(name = "EndDate")
    private String EndDate = "";
    @Column(name = "TaskType")
    private String TaskType = "";
    @Column(name = "TaskStatus")
    private String TaskStatus = "";


    @Column(name = "TaskCode")
    private String TaskCode = "";
    @Column(name = "PointName")
    private String PointName = "";
    @Column(name = "FormName")
    private String FormName = "";
    @Column(name = "TaskStatusName")
    private String TaskStatusName = "";
    @Column(name = "TaskTypeName")
    private String TaskTypeName = "";
    @Column(name = "upload")
    private Boolean upload = false;
    //账号id
    @Column(name = "Userid")
    private String Userid;
    @Column(name = "Username")
    private String Username;

    //仪器
    @Column(name = "InstrumentName")
    private String InstrumentName = "";
    //仪器GUI
    @Column(name = "InstrumentGuid")
    private String InstrumentGuid = "";


    public String getFormtype() {
        return formtype;
    }

    public void setFormtype(String formtype) {
        this.formtype = formtype;
    }

    public String getFormCode() {
        return FormCode;
    }

    public void setFormCode(String formCode) {
        FormCode = formCode;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public List<FormTaskFactor> getFormTaskFactor(DbManager db) throws DbException {
        return db.selector(FormTaskFactor.class).where("TaskGuid", "=", this.RowGuid).findAll();
    }

    public int getPointId() {
        return PointId;
    }

    public void setPointId(int pointId) {
        PointId = pointId;
    }

    public String getFormGuid() {
        return FormGuid;
    }

    public void setFormGuid(String formGuid) {
        FormGuid = formGuid;
    }

    public String getTaskStatusName() {
        return TaskStatusName;
    }

    public void setTaskStatusName(String taskStatusName) {
        TaskStatusName = taskStatusName;
    }

    public String getTaskTypeName() {
        return TaskTypeName;
    }

    public void setTaskTypeName(String taskTypeName) {
        TaskTypeName = taskTypeName;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskCode() {
        return TaskCode;
    }

    public void setTaskCode(String taskCode) {
        TaskCode = taskCode;
    }

    public String getPointName() {
        return PointName;
    }

    public void setPointName(String pointName) {
        PointName = pointName;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public String getFormName() {
        return FormName;
    }

    public void setFormName(String formName) {
        FormName = formName;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getTaskStatus() {
        return TaskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        TaskStatus = taskStatus;
    }

    public String getTaskType() {
        return TaskType;
    }

    public void setTaskType(String taskType) {
        TaskType = taskType;
    }

    public String getOperationCompany() {
        return OperationCompany;
    }

    public void setOperationCompany(String operationCompany) {
        OperationCompany = operationCompany;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getRowGuid() {
        return RowGuid;
    }

    public void setRowGuid(String rowGuid) {
        RowGuid = rowGuid;
    }

    public Boolean getUpload() {
        return upload;
    }

    public void setUpload(Boolean upload) {
        this.upload = upload;
    }


}
