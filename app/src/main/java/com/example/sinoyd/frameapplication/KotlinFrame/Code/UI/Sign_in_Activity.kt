package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.content.Intent
import android.os.Bundle
import android.util.JsonReader
import com.example.sinoyd.frameapplication.R
import kotlinx.android.synthetic.main.activity_sign_in_.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast
import com.google.zxing.integration.android.IntentIntegrator
import android.util.Log
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Adatper.SigninAdapter
import com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass.SignInInfo
import com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass.User
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTask
import com.example.sinoyd.frameapplication.KotlinFrame.Code.jso.JsonSignInInfo
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.Networkrequestmodel
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.ToastUtil
import com.example.sinoyd.frameapplication.KotlinFrame.UI.BaseActivity
import com.example.sinoyd.frameapplication.R.id.*
import com.example.sinoyd.jiaxingywapplication.Myapplication
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sinoyd.Code.Until.Networkrequestaddress
import com.sinoyd.Code.Until.SharedPreferencesFactory
import com.sinoyd.environmentsz.Kotlin.getToday
import okhttp3.Response
import org.json.JSONObject
import org.xutils.DbManager
import org.xutils.x
import java.util.*
import kotlin.collections.ArrayList



/**签到**/
class Sign_in_Activity : BaseActivity() {

    var myapplication: Myapplication = Myapplication()
    var db:DbManager? = null


    //从签到表中获得的数据
    var signInList:MutableList<SignInInfo> = ArrayList()
    var signInAdapter:SigninAdapter ? = null

    var date = Date()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_)
        db = x.getDb(myapplication.getDaoConfig())
        //设置监听
        setlisteners()
        getDateSignInfoLocal()
    }



    private fun setlisteners() {
        //返回主页
        iv_home.onClick {
            finish()
        }
        //扫描
        iv_scan.onClick {
            val integrator = IntentIntegrator(this@Sign_in_Activity)
            // 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
            integrator.captureActivity = ScanActivity::class.java
            integrator.setPrompt("请扫描二维码") //底部的提示文字，设为""可以置空
            integrator.setCameraId(0) //前置或者后置摄像头
            integrator.setBeepEnabled(true) //扫描成功的「哔哔」声，默认开启
            integrator.setBarcodeImageEnabled(false)//是否保留扫码成功时候的截图
            integrator.initiateScan()
        }
    }

    //扫描返回的结果
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0) {
            toast("未进行扫描操作")
        } else {
            val scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (scanResult != null) {
                val result = scanResult.contents

                Log.e("扫描结果", result)
                toast(result)

                var jsonResult:JSONObject = JSONObject(result)
                var signInfo:SignInInfo = SignInInfo()
                /*
                {
                    "站点编号":"9",
                    "站点名称": "长江口朝阳农场（海标）",
                    "点位类型": "海标站",
                    "MN号": "213100000200000000300005",
                    "实施单位": "市局",
                    "运维商": "上海环监装备",
                    "经度": "121.87623",
                    "纬度": "31.19809",
                    "所在区县": "入海断面长江",
                    "区域": "上海市",
                    "河道": "长江",
                    "流域": "长江流域"
                }
                 */
                signInfo.pointId = jsonResult.getInt("站点编号")
                signInfo.userGuid = SharedPreferencesFactory.getdata(this,"RowGuid")
                signInfo.pointName = jsonResult.getString("站点名称")
                signInfo.userName = SharedPreferencesFactory.getdata(this,"DisplayName")

                //判断是否已经签到，如果已经签到则签退，没有签到则签到
                signInOrOut(signInfo)

            }
        }

    }


    /**
     * 签到或签退方法（自动判断）
     */
    private fun signInOrOut(signInfo:SignInInfo){

        var res = db!!.selector(SignInInfo::class.java)
                .where("UserGuid", "=", signInfo.userGuid)//当前用户
                .and("PointId", "=", signInfo.pointId)
                .and("SignState","=","已签到")
                .orderBy("SignInTime", true)
                .findFirst()
        if(res == null){ //签到操作
            res = signInfo
            res.rowGuid = UUID.randomUUID().toString()
            res.signInTime = date.getToday("yyyy/MM/dd HH:mm:ss")
            res.signState = "已签到"
        }else{  //签退操作
            res.signOutTime = date.getToday("yyyy/MM/dd HH:mm:ss")
            res.signState = "已签退"
        }

        val a = try {
            db!!.saveOrUpdate(res)
            Log.i("scj", "站点签到信息表单保存成功")
            true
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("scj", "站点签到信息表单保存成功")
            false
        }
        if(a){
            getDateSignInfoLocal()
        }

        val jsonSign = JsonSignInInfo()
        jsonSign.signIn = res
        var json = JSONObject(jsonSign.toString()).getJSONObject("SignIn").toString()

        val request = Networkrequestmodel()
                .setMethod(Networkrequestmodel.POSTREQUEST)
                .settag("signIn")
                .seturl(Networkrequestaddress.URL_SignIn)
                .addparam("json",json)
                .start(this)
    }

    override fun requestSuccess(response: Response, TAG: String) {
        super.requestSuccess(response, TAG)
        when(TAG){
            "signIn"->{
                Log.i("hyd","签到信息已经同步到服务器")
            }
        }
    }

    //显示界面
    private fun setView(){
        signInAdapter = SigninAdapter(this,signInList)
        lv_sign_in.adapter = signInAdapter
    }

    private fun getDateSignInfoLocal(){
        try {
            signInList = db!!.selector(SignInInfo::class.java)
                    .where("UserGuid", "=", SharedPreferencesFactory.getdata(this, "RowGuid"))
                    .orderBy("SignInTime",true)
                    .findAll()
            Log.i("scj", "取签到记录成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("scj", "取签到记录失败" + e.message)
        }
        setView()
    }

}
