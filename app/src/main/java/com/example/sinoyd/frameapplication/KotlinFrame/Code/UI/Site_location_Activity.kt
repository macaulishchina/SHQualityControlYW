package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.baidu.location.*
import com.baidu.location.LocationClientOption.LOC_SENSITIVITY_HIGHT
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.example.sinoyd.frameapplication.KotlinFrame.Code.model.Factor
import com.example.sinoyd.frameapplication.KotlinFrame.Code.model.Site
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.Networkrequestmodel
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.PermissionKits
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.ToastUtil
import com.example.sinoyd.frameapplication.KotlinFrame.UI.BaseActivity
import com.example.sinoyd.frameapplication.R
import com.example.sinoyd.frameapplication.R.id.bmapView
import com.macaulish.top.coconut.util.FileKits
import com.macaulish.top.velvet.util.Logger
import com.sinoyd.Code.Until.Networkrequestaddress
import com.sinoyd.Code.Until.SharedPreferencesFactory
import kotlinx.android.synthetic.main.activity_site_location_.*
import kotlinx.android.synthetic.main.marker_layout.*
import kotlinx.android.synthetic.main.marker_layout.view.*
import kotlinx.android.synthetic.main.popup_layout.*
import kotlinx.android.synthetic.main.popup_layout.view.*
import okhttp3.Response
import org.jetbrains.anko.onClick
import org.jetbrains.anko.textColor
import org.json.JSONArray
import org.json.JSONObject

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
                    setMarkerClickListener(mBaiduMap)
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
        requestPermissions()
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
        option.setOpenAutoNotifyMode(600,100,LOC_SENSITIVITY_HIGHT)
        mLocClient.locOption = option
        mLocClient.start()
    }


    /**
     * 异步加载对用户[userGuide]授权的所有站点信息
     */
    private fun loadSitesBy(userGuid:String){
        val request = Networkrequestmodel()
        request.settag(REQUEST_FOR_GRANTED_SITES)
                .addparam("userguid",userGuid)
                .seturl(Networkrequestaddress.URL_GrantedSites)
                .setMethod(Networkrequestmodel.GETREQUEST)
                .start(this)
    }

    /**
     * 解析json数据
     */
    private fun resolveSites(json:String):MutableList<Site>{
        Logger.i("gis",json)
        Logger.i("gis","RowGuid"+SharedPreferencesFactory.getdata(this,"rowGuid").toString())
        val jsonSites = JSONObject(json).getJSONArray("data")
        for(i in 0 until  jsonSites.length()){
            val jsonSite = jsonSites.getJSONObject(i)
            val site = Site()
            site.name = jsonSite.getString("name")
            site.pointId = jsonSite.getString("id")
            site.grade = jsonSite.getString("grade")
            site.qualifiedStatus = jsonSite.getString("qualifiedStatus")
            site.latitude = jsonSite.getString("latitude")
            site.longitude = jsonSite.getString("longitude")
            val jsonFactors = jsonSite.getJSONArray("factors")
            for(j in 0 until jsonFactors.length()){
                val jsonFactor = jsonFactors.getJSONObject(j)
                val factor = Factor()
                factor.name = jsonFactor.getString("name")
                factor.value = jsonFactor.getString("value")
                factor.unit = jsonFactor.getString("unit")
                site.addFactor(factor)
            }
            mSites.add(site)
        }
        return mSites
    }

    /**
     *  绘制站点数据到地图上
     */
    private fun displayMap(sites : MutableList<Site>){
        clearMap()

        for(it in sites){
            val overlaySite = LayoutInflater.from(this.applicationContext).inflate(R.layout.marker_layout, null)
            val latLng = LatLng(it.latitude.toDouble(),it.longitude.toDouble())
            overlaySite.baidumap_custom_text.text = it.grade
            when (it.grade) {
                "I" -> overlaySite.baidumap_custom_img.setImageResource(R.drawable.water1)
                "II" -> overlaySite.baidumap_custom_img.setImageResource(R.drawable.water1)
                "III" -> overlaySite.baidumap_custom_img.setImageResource(R.drawable.water2)
                "IV" -> overlaySite.baidumap_custom_img.setImageResource(R.drawable.water3)
                "V" -> overlaySite.baidumap_custom_img.setImageResource(R.drawable.water4)
                "劣V" -> overlaySite.baidumap_custom_img.setImageResource(R.drawable.water5)
                else -> {
                    overlaySite.baidumap_custom_img.setImageResource(R.drawable.water5)
                    //overlaySite.baidumap_custom_img.setColorFilter(Color.GRAY)
                }
            }


            val overlay = BitmapDescriptorFactory.fromView(overlaySite)
            val options = MarkerOptions().position(latLng).icon(overlay).zIndex(10).draggable(false)
            val marker = mBaiduMap.addOverlay(options) as Marker
            attachData(marker,it)
            mMarkers.add(marker)
        }
    }

    /**
     * 绑定数据[site]到标志点[marker]上
     */
    private fun attachData(marker:Marker,site:Site){
        val bundle =  Bundle()
        bundle.putString("pointName",site.name)
        bundle.putString("pointId",site.pointId)
        bundle.putString("qualifiedStatus",site.qualifiedStatus)
        bundle.putString("grade",site.grade)
        marker.extraInfo = bundle
    }

    /**
     * 设置地图[baiduMap]上覆盖物的点击事件
     */
    private fun setMarkerClickListener(baiduMap: BaiduMap) {
        baiduMap.setOnMarkerClickListener { marker ->
            if (marker == null || marker.extraInfo == null) return@setOnMarkerClickListener false
            val info = marker.extraInfo
            val pointName = try {
                info.getString("pointName")
            } catch (e: Exception) {
                "--"
            }
            val pointId = try {
                info.getString("pointId")
            } catch (e: Exception) {
                "--"
            }
            val qualifiedStatus = try {
                info.getString("qualifiedStatus")
            } catch (e: Exception) {
                "--"
            }
            val grade = try {
                info.getString("grade")
            } catch (e: Exception) {
                "--"
            }
            val ll = marker.position

            val bubbleLayout = LayoutInflater.from(this).inflate(R.layout.popup_layout, bmapView, false) as LinearLayout

            bubbleLayout.popup_tv_point_name.text = pointName
            bubbleLayout.popup_tv_prop_1.text = "站点编号：$pointId"
            bubbleLayout.popup_tv_prop_2.text = "合格状态：$qualifiedStatus"
            bubbleLayout.popup_tv_prop_3.text = when (grade) {
                "I" -> Html.fromHtml("<font color='#BADEEE'>等级：</font><font color='#4EFFFE'>$grade</font>")
                "II" -> Html.fromHtml("<font color='#BADEEE'>等级：</font><font color='#4EFFFE'>$grade</font>")
                "III" -> Html.fromHtml("<font color='#BADEEE'>等级：</font><font color='#4BFF00'>$grade</font>")
                "IV" -> Html.fromHtml("<font color='#BADEEE'>等级：</font><font color='#FDFF04'>$grade</font>")
                "V" -> Html.fromHtml("<font color='#BADEEE'>等级：</font><font color='#F67A13'>$grade</font>")
                "劣V" -> Html.fromHtml("<font color='#BADEEE'>等级：</font><font color='#F30115'>$grade</font>")
                else -> Html.fromHtml("<font color='#BADEEE'>等级：</font><font color='GRAY'>$grade</font>")
            }
            bubbleLayout.popup_tv_prop_4.text = ">>查看详情<<"

            bubbleLayout.popup_tv_prop_4.onClick {
                //Todo 查看详情 跳转到详情页面
                ToastUtil.showShort(this,"详情页面开发中")
            }
            val mInfoWindow = InfoWindow(bubbleLayout, ll, -100)
            bmapView.map.showInfoWindow(mInfoWindow)
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

    /**
     * 请求相关运行时权限
     */
    private fun requestPermissions() {
        val permissionKits = PermissionKits(this)
        val permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        permissionKits.request(permissions, 1,object : PermissionKits.OnRequestStartListener{
            override fun onRequestStart(permissions: Array<String>, requestCode: Int) {

            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
            }
        }
    }

}
