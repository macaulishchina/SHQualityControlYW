package com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 作者： hyd
 * 创建时间： 2018/6/25
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass
 */

@Table(name = "SignInInfo")
public class SignInInfo {

    @Column(name = "id",isId = true)
    private int id;
    @Column(name = "RowGuid")
    private String RowGuid = "";
    @Column(name = "UserGuid")
    private String UserGuid = "";
    @Column(name = "UserName")
    private String UserName = "";
    @Column(name = "PointId")
    private int PointId;
    @Column(name = "PointName")
    private String PointName;
    @Column(name = "SignInTime")
    private String SignInTime = "";
    @Column(name = "SignOutTime")
    private String SignOutTime = "";
    @Column(name="SignState")
    private String SignState;

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

    public String getUserGuid() {
        return UserGuid;
    }

    public void setUserGuid(String userGuid) {
        UserGuid = userGuid;
    }

    public int getPointId() {
        return PointId;
    }

    public void setPointId(int pointId) {
        PointId = pointId;
    }

    public String getSignInTime() {
        return SignInTime;
    }

    public void setSignInTime(String signInTime) {
        SignInTime = signInTime;
    }

    public String getSignOutTime() {
        return SignOutTime;
    }

    public void setSignOutTime(String signOutTime) {
        SignOutTime = signOutTime;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPointName() {
        return PointName;
    }

    public void setPointName(String pointName) {
        PointName = pointName;
    }

    public String getSignState() {
        return SignState;
    }

    public void setSignState(String signState) {
        SignState = signState;
    }
}
