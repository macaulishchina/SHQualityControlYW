package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Button
import android.widget.RadioGroup
import com.baidu.location.*
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.example.sinoyd.frameapplication.KotlinFrame.Code.model.Site
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.Networkrequestmodel
import com.example.sinoyd.frameapplication.KotlinFrame.UI.BaseActivity
import com.example.sinoyd.frameapplication.R
import com.example.sinoyd.frameapplication.R.id.bmapView
import com.example.sinoyd.frameapplication.R.id.iv_home
import com.sinoyd.Code.Until.Networkrequestaddress
import com.sinoyd.Code.Until.SharedPreferencesFactory
import kotlinx.android.synthetic.main.activity_location.*
import kotlinx.android.synthetic.main.activity_site_location_.*
import okhttp3.Response
import org.jetbrains.anko.onClick

/**站点定位**/
class Site_location_Activity : BaseActivity(), SensorEventListener {

    companion object {
        //请求tag
        private const val REQUEST_FOR_GRANTED_SITES = "REQUEST_FOR_GRANTED_SITES"

        //what
        private const val DONE_GET_GRANTED_SITES = 1

    }

    val mHandler:Handler = @SuppressLint("HandlerLeak")
    object:Handler() {

        override fun handleMessage(msg: Message) {
            when(msg.what){
                DONE_GET_GRANTED_SITES ->{
                    displayMap()
                }
            }
        }
    }

    // 定位相关
    private lateinit var mLocClient: LocationClient
    var myListener = MyLocationListenner()
    private var mCurrentMode: MyLocationConfiguration.LocationMode? = null
    internal var mCurrentMarker: BitmapDescriptor? = null
    private val accuracyCircleFillColor = -0x55000078
    private val accuracyCircleStrokeColor = -0x55ff0100
    lateinit var mSensorManager: SensorManager
    private var lastX: Double = 0.0
    private var mCurrentDirection = 0
    private var mCurrentLat = 0.0
    private var mCurrentLon = 0.0
    private var mCurrentAccracy: Float = 0.toFloat()

    internal lateinit var mBaiduMap: BaiduMap

    // UI相关
    internal lateinit var requestLocButton: Button
    internal var isFirstLoc = true // 是否首次定位
    private var locData: MyLocationData? = null
    private val direction: Float = 0.toFloat()

    //数据
    private var mSites = mutableListOf<Site>()


    internal var bdA = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_gcoding)//异常
    internal var bdB = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_gcodingdone)//正常
    internal var bdair1 = BitmapDescriptorFactory
            .fromResource(R.drawable.air1)// 正常
    internal var bdair2 = BitmapDescriptorFactory
            .fromResource(R.drawable.air2)// 正常
    internal var bdair3 = BitmapDescriptorFactory
            .fromResource(R.drawable.air3)// 正常
    internal var bdair4 = BitmapDescriptorFactory
            .fromResource(R.drawable.air4)// 正常
    internal var bdair5 = BitmapDescriptorFactory
            .fromResource(R.drawable.air5)// 正常
    internal var bdair6 = BitmapDescriptorFactory
            .fromResource(R.drawable.air6)// 正常

    internal var bdwater1 = BitmapDescriptorFactory
            .fromResource(R.drawable.water1)// 正常

    internal var bdwater2 = BitmapDescriptorFactory
            .fromResource(R.drawable.water2)// 正常

    internal var bdwater3 = BitmapDescriptorFactory
            .fromResource(R.drawable.water3)// 正常
    internal var bdwater4 = BitmapDescriptorFactory
            .fromResource(R.drawable.water4)// 正常
    internal var bdwater5 = BitmapDescriptorFactory
            .fromResource(R.drawable.water5)// 正常
    internal var bdoffline = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_offline)// 正常


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_location_)
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager//获取传感器管理服务
        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS //地图模式-罗盘
        mBaiduMap = bmapView.map
        mBaiduMap.setMyLocationConfiguration(MyLocationConfiguration(mCurrentMode, true, mCurrentMarker))//默认定位图标
        // 开启定位图层
        mBaiduMap.isMyLocationEnabled = true
        // 定位初始化
        mLocClient = LocationClient(this)
        mLocClient.registerLocationListener(myListener)
        val option = LocationClientOption()
        option.isOpenGps = true // 打开gps
        option.setCoorType("bd09ll") // 设置坐标类型
        option.setScanSpan(1000)
        mLocClient.locOption = option
        mLocClient.start()

        //监听时间
        setlisteners()
    }

    private fun setlisteners() {
        //返回主页
        iv_home.onClick {
            finish()
        }
    }


    /**
     * 异步加载对用户[userGuide]授权的所有站点信息
     */
    private fun loadSitesBy(userGuid:String){
        val request = Networkrequestmodel()
        request.settag(REQUEST_FOR_GRANTED_SITES)
                .addparam("Operator",SharedPreferencesFactory.getdata(this,"RowGuid"))
                .seturl(Networkrequestaddress.URL_GrantedSites)
                .setMethod(Networkrequestmodel.GETREQUEST)
                .start(this)
    }

    override fun requestSuccess(response: Response, TAG: String) {
        super.requestSuccess(response, TAG)
        when(TAG){
            REQUEST_FOR_GRANTED_SITES ->{
                val res = response.body().toString()
                mSites = resolveSites(res)
                val msg = Message()
                msg.what = DONE_GET_GRANTED_SITES
                mHandler.sendMessage(msg)
                Looper.loop()
            }
        }
    }

    private fun resolveSites(json:String):MutableList<Site>{
        //TODO 解析数据保存到mSite
        return mutableListOf()
    }

    private fun displayMap(){

    }



    /**
     * 定位SDK监听函数
     */
    inner class MyLocationListenner : BDAbstractLocationListener() {

        override fun onReceiveLocation(location: BDLocation?) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || bmapView == null) {
                return
            }
            mCurrentLat = location.latitude
            mCurrentLon = location.longitude
            mCurrentAccracy = location.radius
            locData = MyLocationData.Builder()
                    .accuracy(location.radius)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection.toFloat()).latitude(location.latitude)
                    .longitude(location.longitude).build()
            mBaiduMap.setMyLocationData(locData)
            if (isFirstLoc) {
                isFirstLoc = false
                val ll = LatLng(location.latitude,
                        location.longitude)
                val builder = MapStatus.Builder()
                builder.target(ll).zoom(18.0f)
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
            }
        }

    }


    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(p0: SensorEvent) {
        val x = p0.values[SensorManager.DATA_X].toDouble()
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = x.toInt()
            locData = MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection.toFloat()).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build()
            mBaiduMap.setMyLocationData(locData)
        }
        lastX = x
    }

    override fun onPause() {
        bmapView.onPause()
        super.onPause()
    }

    override fun onResume() {
        bmapView.onResume()
        super.onResume()
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI)
    }

    override fun onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(this)
        super.onStop()
    }

    override fun onDestroy() {
        // 退出时销毁定位
        mLocClient.stop()
        // 关闭定位图层
        mBaiduMap.isMyLocationEnabled = false
        bmapView.onDestroy()
        super.onDestroy()
    }
}
