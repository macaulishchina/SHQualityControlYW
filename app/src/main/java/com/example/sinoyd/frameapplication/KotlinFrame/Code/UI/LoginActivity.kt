package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.os.Bundle
import android.os.Looper
import com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass.User
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dataclass.gson
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.JudgNetwork
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.Networkrequestmodel
import com.example.sinoyd.frameapplication.KotlinFrame.UI.BaseActivity
import com.example.sinoyd.frameapplication.R
import com.sinoyd.Code.Until.DisplayorhideSoftkeyboard
import com.sinoyd.Code.Until.Networkrequestaddress
import com.sinoyd.Code.Until.SharedPreferencesFactory
import com.sinoyd.Code.Until.showdialog
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.Response
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.net.URLEncoder
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.SystemUtil
import com.example.sinoyd.frameapplication.R.id.ed_middle
import com.example.sinoyd.frameapplication.R.id.et_password
import java.util.*
import java.util.UUID.randomUUID


class LoginActivity : BaseActivity() {
    var user: User = com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass.User()
    var LoginId: String = ""
    var Password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //获取屏幕的宽度 //后面为了布局用
        SystemUtil.getScreen(this)
        //填充账号密码
        LoginId = SharedPreferencesFactory.getdata(this, "LoginId")
        Password = SharedPreferencesFactory.getdata(this, "Password")
        if (LoginId != "") {
            ed_middle.setText(LoginId)
        }
        if (Password != "") {
            et_password.setText(Password)
        }
        //隐藏键盘
        DisplayorhideSoftkeyboard.hideSoftkeyboard(this)
        //设置监听事件
        setlisteners()
    }

    private fun setlisteners() {
        //登陆按钮
        btn_login.onClick {
            LoginId = ed_middle.text.toString()
            Password = et_password.text.toString()
            if (LoginId == "" || Password == "") {
                toast("账号密码不允许为空")
            } else {
                if (JudgNetwork(this)) {
                    localLogin()
                    sendlogin()
                } else {
                    toast("当前网络故障或无网络")
                }
            }
        }
    }

    /**
     * 无网络时本地登入用
     */
    private fun localLogin(){
        if(LoginId == "?local"&&Password == "123456"){
            SharedPreferencesFactory.savedata(this, "LoginId", LoginId)
            SharedPreferencesFactory.savedata(this, "Password", Password)
            SharedPreferencesFactory.savedata(this, "RowGuid", UUID.fromString("81be15c0-1fc5-4d69-9ecf-2a41fcf174fc").toString())
            SharedPreferencesFactory.savedata(this, "DisplayName", "本地账号")
            startActivity<MainActivity>()
            finish()
        }
    }

    //发送登陆请求
    fun sendlogin() {
        showdialog("loadshow")
        var ntrequest = Networkrequestmodel()
        ntrequest.setMethod(Networkrequestmodel.GETREQUEST)
                .settag("loginImpl")
                .seturl(Networkrequestaddress.URL_User)
                .addparam("LoginId", LoginId)
                .addparam("Password", URLEncoder.encode(Password))
                .start(this)
    }

    override fun requestSuccess(response: Response, TAG: String) {
        super.requestSuccess(response, TAG)
        when (TAG) {
            "loginImpl" -> {
                try {
                    user = gson.fromJson(responsestr, User::class.java)
                    if (user.result == "True") {
                        //登陆成功
                        SharedPreferencesFactory.savedata(this, "LoginId", LoginId)
                        SharedPreferencesFactory.savedata(this, "Password", Password)
                        SharedPreferencesFactory.savedata(this, "RowGuid", user.data[0].rowGuid)
                        SharedPreferencesFactory.savedata(this, "DisplayName", user.data[0].displayName)
                        startActivity<MainActivity>()
                        finish()
                    } else {
                        Looper.prepare()
                        toast(user.message)
                        Looper.loop()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


}
