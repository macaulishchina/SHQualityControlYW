package com.example.sinoyd.frameapplication.KotlinFrame.Code.jso;

import com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass.SignInInfo;
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dialog.SingleDateSelectDialog;
import com.google.gson.Gson;

import java.util.List;

/**
 * 作者： hyd
 * 创建时间： 2018/6/25
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.jso
 */

public class JsonSignInInfo {

    //{"id":3,"PointName":"长江口朝阳农场（海标）","message":"","SignState":"已签退","PointId":9,"SignOutTime":"2018\/07\/03 16:31:44","RowGuid":"9edf9572-11dd-4764-a2e2-8135b17e64dd","UserGuid":"c7017a1d-fea4-447b-803b-62d0e0d33233","SignInTime":"2018\/07\/03 16:21:46","UserName":"大虞环保"}
    private SignInInfo SignIn;

    public SignInInfo getSignIn() {
        return SignIn;
    }

    public void setSignIn(SignInInfo signIn) {
        SignIn = signIn;
    }

    @Override
    public String toString(){
        Gson gs = new Gson();
        return gs.toJson(this);
    }
}
