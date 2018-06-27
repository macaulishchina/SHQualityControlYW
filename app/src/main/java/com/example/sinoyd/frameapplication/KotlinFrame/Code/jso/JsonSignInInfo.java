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

    private SignInInfo SignIn;
    private List<SingleDateSelectDialog> SignInData;

    public SignInInfo getSignIn() {
        return SignIn;
    }

    public void setSignIn(SignInInfo signIn) {
        SignIn = signIn;
    }

    public List<SingleDateSelectDialog> getSignInData() {
        return SignInData;
    }

    public void setSignInData(List<SingleDateSelectDialog> signInData) {
        SignInData = signInData;
    }

    @Override
    public String toString(){
        Gson gs = new Gson();
        return gs.toJson(this);
    }
}
