package com.example.sinoyd.frameapplication.KotlinFrame.Code.db;


import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.sql.Blob;

/**
 * 作者： hyd
 * 创建时间： 2018/6/28
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.db
 */

@Table(name = "FormTaskPicture")
public class FormTaskPicture {

    //任务编号(TaskCode)  点位(PointId) 运维人员 上传时间  分类名称
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "RowGuid")
    private String RowGuid = "";
    @Column(name = "TaskGuid")
    private String TaskGuid = "";
    @Column(name = "TaskCode")
    private String TaskCode = "";
    @Column(name = "PointId")
    private int PointId;
    @Column(name = "Username")
    private String Username = "";
    @Column(name = "TakeTime")
    private String TakeTime ="";
    @Column(name = "UploadTime")
    private String UploadTime ="";
    @Column(name = "Cate")
    private String Cate = "";
    @Column(name = "CachePath")
    private String LocalCachePath;

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

    public String getTaskGuid() {
        return TaskGuid;
    }

    public void setTaskGuid(String taskGuid) {
        TaskGuid = taskGuid;
    }

    public String getTaskCode() {
        return TaskCode;
    }

    public void setTaskCode(String taskCode) {
        TaskCode = taskCode;
    }

    public int getPointId() {
        return PointId;
    }

    public void setPointId(int pointId) {
        PointId = pointId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getTakeTime() {
        return TakeTime;
    }

    public void setTakeTime(String takeTime) {
        TakeTime = takeTime;
    }

    public String getUploadTime() {
        return UploadTime;
    }

    public void setUploadTime(String uploadTime) {
        UploadTime = uploadTime;
    }

    public String getCate() {
        return Cate;
    }

    public void setCate(String cate) {
        Cate = cate;
    }

    public String getLocalCachePath() {
        return LocalCachePath;
    }

    public void setLocalCachePath(String localCachePath) {
        LocalCachePath = localCachePath;
    }
}
