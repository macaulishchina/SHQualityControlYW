package com.example.sinoyd.frameapplication.KotlinFrame.Code.db;


import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

import java.sql.Blob;
import java.util.List;

/**
 * 作者： hyd
 * 创建时间： 2018/6/28
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.db
 */

@Table(name = "FormTaskPicture")
public class FormTaskPicture {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "RowGuid")
    private String RowGuid = "";
    @Column(name = "TaskGuid")
    private String TaskGuid = "";
    @Column(name = "Picture")
    private Blob Picture;

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

    public Blob getPicture() {
        return Picture;
    }

    public void setPicture(Blob picture) {
        Picture = picture;
    }
}
