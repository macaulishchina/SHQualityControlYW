package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import com.baidu.location.*
import com.baidu.location.LocationClientOption.LOC_SENSITIVITY_HIGHT
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.example.sinoyd.frameapplication.KotlinFrame.Code.model.Factor
import com.example.sinoyd.frameapplication.KotlinFrame.Code.model.Site
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.Networkrequestmodel
import com.example.sinoyd.frameapplication.KotlinFrame.UI.BaseActivity
import com.example.sinoyd.frameapplication.R
import com.sinoyd.Code.Until.Networkrequestaddress
import com.sinoyd.Code.Until.SharedPreferencesFactory
import kotlinx.android.synthetic.main.activity_site_location_.*
import kotlinx.android.synthetic.main.marker_layout.view.*
import kotlinx.android.synthetic.main.popup_layout.*
import kotlinx.android.synthetic.main.popup_layout.view.*
import okhttp3.Response
import org.jetbrains.anko.onClick
import org.json.JSONArray

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
                    displayMap(mSites)
                }
            }
        }
    }

    // 定位相关
    private lateinit var mLocClient: LocationClient
    var myListener = MyLocationListenner()
    private var mCurrentMode: MyLocationConfiguration.LocationMode? = null
    internal var mCurrentMarker: BitmapDescriptor? = null
    lateinit var mSensorManager: SensorManager
    private var lastX: Double = 0.0
    private var mCurrentDirection = 0
    private var mCurrentLat = 0.0
    private var mCurrentLon = 0.0
    private var mCurrentAccracy: Float = 0.toFloat()

    internal lateinit var mBaiduMap: BaiduMap

    // UI相关
    internal var isFirstLoc = true // 是否首次定位
    private var locData: MyLocationData? = null
    private val direction: Float = 0.toFloat()

    //数据
    private var mSites = mutableListOf<Site>()
    private var mMarkers = mutableListOf<Marker>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_location_)
        initBaiDuLocation()
        iv_home.onClick { finish() }
    }

    override fun onStart() {
        super.onStart()
        loadSitesBy(SharedPreferencesFactory.getdata(this,"RowGuid"))
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

    override fun requestSuccess(response: Response, TAG: String) {
        super.requestSuccess(response, TAG)
        when(TAG){
            REQUEST_FOR_GRANTED_SITES ->{
                val json = responsestr
                Log.i("hyd","response$json")
                mSites = resolveSites(json)
                val msg = Message()
                msg.what = DONE_GET_GRANTED_SITES
                mHandler.sendMessage(msg)
                Looper.prepare()
                Looper.loop()
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        //
    }

    /**
     * 初始百度定位
     */
    private fun initBaiDuLocation(){
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
        option.setOpenAutoNotifyMode(60,10,LOC_SENSITIVITY_HIGHT)
        mLocClient.locOption = option
        mLocClient.start()
    }


    /**
     * 异步加载对用户[userGuide]授权的所有站点信息
     */
    private fun loadSitesBy(userGuid:String){
        val request = Networkrequestmodel()
        request.settag(REQUEST_FOR_GRANTED_SITES)
                .addparam("Operator",userGuid)
                .seturl(Networkrequestaddress.URL_GrantedSites)
                .setMethod(Networkrequestmodel.GETREQUEST)
                .start(this)
    }

    /**
     * 解析json数据
     */
    private fun resolveSites(json:String):MutableList<Site>{
        val jsonSites = JSONArray(json)
        for(i in 0 until  jsonSites.length()-1){
            val jsonSite = jsonSites.getJSONObject(i)
            val site = Site()
            site.name = jsonSite.getString("MonitoringPointName")
            site.pointId = jsonSite.getString("PointId")
            site.latitude = jsonSite.getString("BaiduY")
            site.longitude = jsonSite.getString("BaiduX")
            val factor = Factor()
            factor.name = "airQuality"
            factor.value = "良好"
            factor.unit = ""
            site.addFactor(factor)
            mSites.add(site)
        }

        return mSites
    }

    /**
     *  绘制站点数据到地图上
     */
    private fun displayMap(sites : MutableList<Site>){
        clearMap()
        val overlaySite = LayoutInflater.from(this.applicationContext).inflate(R.layout.marker_layout, null)
        overlaySite.baidumap_custom_text.text = ""
        for(it in sites){
            val latLng = LatLng(it.latitude.toDouble(),it.longitude.toDouble())
            overlaySite.baidumap_custom_text.text = it.eqi
            val overlay = BitmapDescriptorFactory.fromView(overlaySite)
            val options = MarkerOptions().position(latLng).icon(overlay).zIndex(10).draggable(false)
            val marker = mBaiduMap.addOverlay(options) as Marker
            attachData(marker,it)
            mMarkers.add(marker)
        }
        setMarkerClickListener(mBaiduMap)

    }

    /**
     * 绑定数据[site]到标志点[marker]上
     */
    private fun attachData(marker:Marker,site:Site){
        val bundle =  Bundle()
        bundle.putString("pointName",site.name)
        bundle.putString("pointId",site.pointId)
        bundle.putString("airQuality",site.getValueWithUnit("airQuality"))
        bundle.putString("updateTime",site.getValueWithUnit("updateTime"))
        marker.extraInfo = bundle
    }

    /**
     * 设置地图[baiduMap]上覆盖物的点击事件
     */
    private fun setMarkerClickListener(baiduMap: BaiduMap){
        baiduMap.setOnMarkerClickListener {
            if(it.extraInfo == null) return@setOnMarkerClickListener false
            val info = it.extraInfo
            val pointName = info.getString("pointName")
            val pointId = info.getString("pointId")
            val airQuality = info.getString("airQuality")
            val updateTime = info.getString("updateTime")

            val bubbleLayout = LayoutInflater.from(this).inflate(R.layout.popup_layout, bmapView, false) as LinearLayout
            bubbleLayout.popup_tv_name.text = "站点名:$pointName"
            bubbleLayout.popup_tv_prop_1.text = "站点编号:$pointId"
            bubbleLayout.popup_tv_prop_2.text = "空气质量:$airQuality"
            bubbleLayout.popup_tv_time.text = "更新时间:$updateTime"


            mBaiduMap.showInfoWindow(InfoWindow(bubbleLayout,it.position,-80))
            return@setOnMarkerClickListener true
        }

        baiduMap.setOnMapClickListener(object :BaiduMap.OnMapClickListener{
            override fun onMapPoiClick(p0: MapPoi?): Boolean {
                return false
            }

            override fun onMapClick(p0: LatLng?) {
                baiduMap.hideInfoWindow()
            }
        })
    }



    /**
     * 清空覆盖物
     */
    private fun clearMap(){
        mBaiduMap.clear()
        mMarkers.clear()
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

}
