package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.example.sinoyd.frameapplication.R
import kotlinx.android.synthetic.main.activity_sign_in_.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast
import com.google.zxing.integration.android.IntentIntegrator
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.Poi
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.DistanceUtil
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Adatper.SigninAdapter
import com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass.SignInInfo
import com.example.sinoyd.frameapplication.KotlinFrame.Code.jso.JsonSignInInfo
import com.example.sinoyd.frameapplication.KotlinFrame.Code.service.LocationService
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.Networkrequestmodel
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.PermissionKits
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.ToastUtil
import com.example.sinoyd.frameapplication.KotlinFrame.UI.BaseActivity
import com.example.sinoyd.frameapplication.R.style.dialog
import com.example.sinoyd.jiaxingywapplication.Myapplication
import com.macaulish.top.velvet.util.Logger
import com.macaulish.top.velvet.view.SimpleDialog
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
    val dialog = SimpleDialog(this@Sign_in_Activity)


    //从签到表中获得的数据
    var signInList:MutableList<SignInInfo> = ArrayList()
    var signInAdapter:SigninAdapter ? = null

    var date = Date()
    //定位服务
    var locationService: LocationService? = null


    var mHandler :Handler = @SuppressLint("HandlerLeak")
    object:Handler(){

        var signInfo:SignInInfo? = null
        lateinit var point : LatLng
        var hasSignRequest = false

        override fun handleMessage(msg: Message) {
            when(msg.what){
                ASK_FOR_SIGN_IN ->{
                    hasSignRequest = true
                    signInfo = (msg.obj!! as SignInInfo)
                    point = LatLng(msg.data.getDouble("latitude"),msg.data.getDouble("longitude"))
                    Log.i("hyd","签到请求：站点位置$point")
                    dialog.alert("扫码成功，请等待位置验证。")
                }
                LOCATION_SUCCESS ->{
                    //获得定位数据
                    val current = LatLng(msg.data.getDouble("latitude"),msg.data.getDouble("longitude"))
                    Log.i("hyd","定位数据： $current")
                    if(hasSignRequest){
                        hasSignRequest = false
                        Log.i("hyd","处理签到请求：站点位置： $current")
                        val distance = DistanceUtil.getDistance(point,current)
                        if(distance < MAX_DISTANCE){
                            signInOrOut(signInfo!!)
                            dialog.alert("签到验证成功")
                        }else{
                            dialog.alert("签到验证失败，你可能不在站点附近。\nps:你的经纬度为 经度：${current.longitude} 经度：${current.latitude}")
                        }
                    }
                }

            }

        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_)
        db = x.getDb(myapplication.getDaoConfig())
        requestPermissions()
        /*
         * 测试用
         */
        tv_sign_in_test.text = if(SharedPreferencesFactory.getdata(this,"SignState") == "True") "已签到" else "未签到"
        tv_sign_in_test.onClick{
            val tv = (it as TextView)
            when(tv.text){
                "未签到" ->{
                    tv.text = "已签到"
                    SharedPreferencesFactory.savedata(this,"SignState","True")
                }
                "已签到" ->{
                    tv.text = "未签到"
                    SharedPreferencesFactory.savedata(this,"SignState","False")
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        locationService = (application as Myapplication).locationService
        //设置监听
        setlisteners()
        getDateSignInfoLocal()
        // -----------location config ------------
        val type = intent.getIntExtra("from", 0)
        if (type == 0) {
            locationService!!.setLocationOption(locationService!!.defaultLocationClientOption)
        } else if (type == 1) {
            locationService!!.setLocationOption(locationService!!.option)
        }
        locationService!!.start()
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
        locationService!!.registerListener(mListener)
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
                    "经度": "120.522734",
                    "纬度": "31.872493",
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

                var message = Message()
                message.what = ASK_FOR_SIGN_IN
                message.obj = signInfo
                message.data.putDouble("longitude",jsonResult.getDouble("经度"))
                message.data.putDouble("latitude",jsonResult.getDouble("纬度"))
                mHandler.sendMessage(message)
                Looper.loop()

            }
        }

    }

    /**
     * 签到或签退方法（自动判断）
     * 判断是否在点位附近，如果不在定位附近，不可以签到
     */
    private fun signInOrOut(signInfo:SignInInfo){

        var res = db!!.selector(SignInInfo::class.java)
                .where("UserGuid", "=", signInfo.userGuid)//当前用户
                .and("PointId", "=", signInfo.pointId)
                .and("SignState","=","已签到")
                .orderBy("SignInTime", true)
                .findFirst()
        SharedPreferencesFactory.savedata(this,"PointId",signInfo.pointId.toString())
        SharedPreferencesFactory.savedata(this,"PointName",signInfo.pointName.toString())
        if(res == null){ //签到操作
            res = signInfo
            res.rowGuid = UUID.randomUUID().toString()
            res.signInTime = date.getToday("yyyy/MM/dd HH:mm:ss")
            res.signState = "已签到"
            SharedPreferencesFactory.savedata(this,"SignState","True")
        }else{  //签退操作
            res.signOutTime = date.getToday("yyyy/MM/dd HH:mm:ss")
            res.signState = "已签退"
            SharedPreferencesFactory.savedata(this,"SignState","False")
        }

        val a = try {
            db!!.saveOrUpdate(res)
            Log.i("scj", "站点签到信息表单保存成功")
            true
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("scj", "站点签到信息表单保存失败")
            false
        }
        if(a){
            getDateSignInfoLocal()
        }

        val jsonSign = JsonSignInInfo()
        jsonSign.signIn = res
        val request = Networkrequestmodel()
                .setMethod(Networkrequestmodel.POSTREQUEST)
                .settag("signIn")
                .seturl(Networkrequestaddress.URL_SignIn)
                .addparam("json",jsonSign.toString())
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
                    .findAll() ?: mutableListOf()

            Log.i("scj", "取签到记录成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("scj", "取签到记录失败" + e.message)
        }
        setView()
    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private val mListener = object : BDAbstractLocationListener() {


        override fun onReceiveLocation(location: BDLocation?) {
            Log.i("hyd","定位回掉")
            if (null != location && location.locType != BDLocation.TypeServerError) {
                val message = Message()
                message.what = LOCATION_SUCCESS
                message.data.putDouble("longitude",location.longitude)
                message.data.putDouble("latitude",location.latitude)
                mHandler.sendMessage(message)
            }

            if (null != location && location.locType != BDLocation.TypeServerError) {
                val sb = StringBuffer(256)
                sb.append("time : ")
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.time)
                sb.append("\nlocType : ")// 定位类型
                sb.append(location.locType)
                sb.append("\nlocType description : ")// *****对应的定位类型说明*****
                sb.append(location.locTypeDescription)
                sb.append("\nlatitude : ")// 纬度
                sb.append(location.latitude)
                sb.append("\nlontitude : ")// 经度
                sb.append(location.longitude)
                sb.append("\nradius : ")// 半径
                sb.append(location.radius)
                sb.append("\nCountryCode : ")// 国家码
                sb.append(location.countryCode)
                sb.append("\nCountry : ")// 国家名称
                sb.append(location.country)
                sb.append("\ncitycode : ")// 城市编码
                sb.append(location.cityCode)
                sb.append("\ncity : ")// 城市
                sb.append(location.city)
                sb.append("\nDistrict : ")// 区
                sb.append(location.district)
                sb.append("\nStreet : ")// 街道
                sb.append(location.street)
                sb.append("\naddr : ")// 地址信息
                sb.append(location.addrStr)
                sb.append("\nUserIndoorState: ")// *****返回用户室内外判断结果*****
                sb.append(location.userIndoorState)
                sb.append("\nDirection(not all devices have value): ")
                sb.append(location.direction)// 方向
                sb.append("\nlocationdescribe: ")
                sb.append(location.locationDescribe)// 位置语义化信息
                sb.append("\nPoi: ")// POI信息
                if (location.poiList != null && !location.poiList.isEmpty()) {
                    for (i in 0 until location.poiList.size) {
                        val poi = location.poiList[i] as Poi
                        sb.append(poi.name + ";")
                    }
                }
                if (location.locType == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ")
                    sb.append(location.speed)// 速度 单位：km/h
                    sb.append("\nsatellite : ")
                    sb.append(location.satelliteNumber)// 卫星数目
                    sb.append("\nheight : ")
                    sb.append(location.altitude)// 海拔高度 单位：米
                    sb.append("\ngps status : ")
                    sb.append(location.gpsAccuracyStatus)// *****gps质量判断*****
                    sb.append("\ndescribe : ")
                    sb.append("gps定位成功")
                } else if (location.locType == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ")
                        sb.append(location.altitude)// 单位：米
                    }
                    sb.append("\noperationers : ")// 运营商信息
                    sb.append(location.operators)
                    sb.append("\ndescribe : ")
                    sb.append("网络定位成功")
                } else if (location.locType == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ")
                    sb.append("离线定位成功，离线定位结果也是有效的")
                } else if (location.locType == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ")
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因")
                } else if (location.locType == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ")
                    sb.append("网络不同导致定位失败，请检查网络是否通畅")
                } else if (location.locType == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ")
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机")
                }
                Log.i("gis",sb.toString())
            }
            Looper.loop()
        }

    }

    /***
     * Stop location service
     */
    override fun onStop() {
        // TODO Auto-generated method stub
        locationService!!.unregisterListener(mListener) //注销掉监听
        locationService!!.stop() //停止定位服务
        super.onStop()
    }

    /**
     * 请求相关运行时权限
     */
    private fun requestPermissions() {
        val permissionKits = PermissionKits(this)
        val permissions = arrayOf(Manifest.permission.CAMERA,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)
        permissionKits.request(permissions, PERMISSION_REQUEST_CODE_DEFAULT,object :PermissionKits.OnRequestStartListener{
            override fun onRequestStart(permissions: Array<String>, requestCode: Int) {
                iv_scan.isEnabled = false
                Logger.i("disable 扫码按钮")
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE_DEFAULT -> {
                val ack = grantResults.size == permissions.size
                var grantAll = true
                for(grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        grantAll = false
                        break
                    }
                }
                Logger.i("enable $grantAll 扫码按钮")
                iv_scan.isEnabled = grantAll
            }
        }
    }

    companion object {
        const val PERMISSION_REQUEST_CODE_DEFAULT = 0

        const val RESULT_ACTIVITY_MEDIA_CAMERA = 100

        const val SALMON_DIRECTORY_NAME = "salmon"

        const val ASK_FOR_SIGN_IN = 0
        const val LOCATION_SUCCESS = 1
        const val MAX_DISTANCE = 1000

    }
}
