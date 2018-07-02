package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.Manifest
import android.content.pm.PackageManager
import android.os.*
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Adatper.TaskManagerAdapter
import com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass.Up
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTask
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTaskFactor
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTaskPicture
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Performance_assessment.BiaoZhunData
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Performance_assessment.JianChuXianData
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Performance_assessment.LingDianData
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Weekly_inspection.Check2clean
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Weekly_inspection.Consumables
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Weekly_inspection.Instrument
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Weekly_inspection.StandardSolutionChange
import com.example.sinoyd.frameapplication.KotlinFrame.Code.jso.JsonFormTask
import com.example.sinoyd.frameapplication.KotlinFrame.Code.jso.JsonTaskPicture
import com.example.sinoyd.frameapplication.KotlinFrame.Code.jso.JsonweekFormTask
import com.example.sinoyd.frameapplication.KotlinFrame.Code.jso.Jsonxingnengkaohe
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dataclass.gson
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.Networkrequestmodel
import com.example.sinoyd.frameapplication.KotlinFrame.UI.BaseActivity
import com.example.sinoyd.frameapplication.R
import com.example.sinoyd.jiaxingywapplication.Myapplication
import com.google.gson.Gson
import com.sinoyd.Code.Until.Networkrequestaddress
import com.sinoyd.Code.Until.SharedPreferencesFactory
import com.sinoyd.Code.Until.showdialog
import kotlinx.android.synthetic.main.activity_task_management_.*
import okhttp3.Response
import org.jetbrains.anko.act
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONObject
import org.xutils.DbManager
import org.xutils.x
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File


/**任务管理**/
class Task_management_Activity : BaseActivity() {


    private val WRITE_PERMISSION = 0x01

    //数据库
    var myapplication: Myapplication = Myapplication()
    var db: DbManager? = null

    //获取表FormTaks中全部的数据
    var taskList: MutableList<FormTask> = ArrayList()
    var taskListall: MutableList<FormTask> = ArrayList()
    var taskmanageradapter: TaskManagerAdapter? = null

    var myHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                1 -> {
                    getdata4sqlite()
                }
                2 -> {

                }
            }
            super.handleMessage(msg)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_management_)
        db = x.getDb(myapplication.getDaoConfig())
        //设置监听
        setlisteners()
        requestWritePermission()

    }

    override fun onResume() {
        super.onResume()
        //从数据库取数据
        getdata4sqlite()
    }

    //从数据库取数据
    private fun getdata4sqlite() {
        try {
            taskList = db!!.selector(FormTask::class.java).where("Userid", "=", SharedPreferencesFactory.getdata(this, "RowGuid")).findAll()
            taskListall = db!!.findAll(FormTask::class.java)
            Log.i("scj", "取任务成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("scj", "取任务失败" + e.message)
        }
        //显示界面
        setview()
    }

    //显示界面
    private fun setview() {
        taskmanageradapter = TaskManagerAdapter(act, taskList)
        lv_task_management.adapter = taskmanageradapter
    }


    override fun requestSuccess(response: Response, TAG: String) {
        super.requestSuccess(response, TAG)
        when (TAG) {
            "postUp" -> {
                try {
                    var up = Up()
                    up = gson.fromJson(responsestr, Up::class.java)

                    if (up.result == "True") {
                        //上传成功，需要更新本地数据库
                        formtask.taskStatusName = "完成"
                        formtask.upload = true
                        try {
                            db!!.update(formtask)
                            Log.i("scj", "本地数据更新成功")
                            val message = Message()
                            message.what = 1
                            myHandler.sendMessage(message)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Log.i("scj", "本地数据更新失败" + e.message)
                        }
                    }

                    Looper.prepare()
                    toast(up.message)
                    Looper.loop()

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            "PicUp" ->{
                //val up = gson.fromJson(responsestr,Up::class.java)
                val message = Message()
                message.what = 2
                myHandler.sendMessage(message)
                Looper.prepare()
                Looper.loop()
            }
            "PerforUp" -> {

            }

        }
    }


    private fun setlisteners() {
        //返回主页
        iv_home.onClick {
            finish()
        }
    }

    /**
     * 标准
     * 标样考核【StanLiquidVerification】
     * 实样比对【SampleComparison】
     * 性能考核【XingNengKH】
     * 周巡检【WeekInspection】
     * 加标回收【JiaBiaoHS】
     * 准确度和精密度 【AccuracyAndPrecision】
     * **/


    //跳转界面
    fun goto(type: String, guid: String, tasktypename: String, stationid: String) {
        when (type) {
        //实样比对
            "SampleComparison" -> {
                startActivity<New_Realsample_comparison_Activity>("rowGuid" to guid)
            }
        //周巡检
            "WeekInspection" -> {
                startActivity<New_Water_Monitoring_Inspection_Activity>("rowGuid" to guid, "pointId" to stationid)
            }
        //标样考核
            "StanLiquidVerification" -> {
                startActivity<New_Standard_solution_verification_Activity>("rowGuid" to guid)
            }
        //加标回收
            "JiaBiaoHS" -> {
                startActivity<New_Standard_Recovery_Activity>("rowGuid" to guid)
            }
        //性能考核  年考核
            "XingNengKH" -> {
                startActivity<Reagent_manager_Activity>("rowGuid" to guid, "pointId" to stationid)
            }
        //准确度和精密度
            "AccuracyAndPrecision" -> {
                startActivity<New_Accuracy_and_precision_Activity>("rowGuid" to guid, "TaskTypeName" to tasktypename)
            }
        //其他
            else -> {
                toast("没有与之匹配的表单【$type】")
            }
        }
    }

    /**
     * 跳转到添加图片界面
     */
    fun gotoAddPic(guid:String){
        startActivity<AddOrUpdate_Picture_Activity>("RowGuid" to guid)
    }

    /**
     * 判断这条记录本地是否有待上传图片
     */
    fun containPicture(guid : String):Boolean{
        val res = db!!.selector(FormTaskPicture::class.java).where("TaskGuid", "=", guid).findAll()
        return !(res == null || res.isEmpty())
    }

    /**
     * 上传与任务相关的所有本地图片
     */
    fun uploadPictures(taskGuid:String){
        var jsonTaskPictures = getTaskPicturesJson(taskGuid)

        for(item in jsonTaskPictures){
            val request = Networkrequestmodel()
                    .setMethod(Networkrequestmodel.POSTREQUEST)
                    .settag("PicUp")
                    .seturl(Networkrequestaddress.URL_PicUp)
                    .addparam("json",item)
            val json = Gson()
            Log.i("hyd","上传图片附带json数据=$item")
            var localPath = JSONObject(item).getJSONObject("picture").getString("LocalCachePath")
            localPath = localPath.substring(7,localPath.length-1)
            val file = File(localPath)
            Log.i("hyd","file path = $localPath 文件是否存在：${file.exists()}")
            if(file.exists()){
                request.file = file
                Luban.with(this)
                        .filter { path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")) }
                        .ignoreBy(1024)
                        .setCompressListener(object : OnCompressListener {
                            override fun onSuccess(file: File?) {
                                Log.i("hyd","文件${file!!.name}压缩完成")
                                Log.i("hyd","开始发送文件${file.name}")
                                request.start(this@Task_management_Activity)
                            }

                            override fun onError(e: Throwable?) {
                                Log.i("hyd","文件${file.name}压缩失败")
                            }

                            override fun onStart() {
                                Log.i("hyd","开始压缩文件${file.name}")
                            }

                        }).launch()
            }
        }
    }



    //上传任务
    fun upload(type: String, guid: String, pointId: Int) {


        var json = when (type) {
            "SampleComparison" -> {
                getFormTaskJson(guid)
            }
            "WeekInspection" -> {
                getFormTaskJson2(guid, pointId)
            }
            "StanLiquidVerification" -> {
                getFormTaskJson(guid)
            }
            "JiaBiaoHS" -> {
                getFormTaskJson(guid)
            }
            "XingNengKH" -> {
                getFormTaskJson3(guid)
            }
            "AccuracyAndPrecision" -> {
                getFormTaskJson(guid)
            }
            else -> {
                ""
            }
        }

//        if (!Checkisnull) {//全部填写了才能提交
        //发送请求
        showdialog("loadshow")
        var ntrequest = Networkrequestmodel()
        ntrequest.setMethod(Networkrequestmodel.POSTREQUEST)
                .settag("postUp")
                .seturl(Networkrequestaddress.URL_Up)
                .addparam("json", json)
                .start(this)
//        }
    }

    //上传任务
    fun upload2(type: String, guid: String, pointId: Int) {


        var json = getFormTaskJson3(guid)

//        if (!Checkisnull) {//全部填写了才能提交
        //发送请求
        showdialog("loadshow")
        var ntrequest = Networkrequestmodel()
        ntrequest.setMethod(Networkrequestmodel.POSTREQUEST)
                .settag("PerforUp")
                .seturl(Networkrequestaddress.URL_PerforUp)
                .addparam("json", json)
                .start(this)
//        }
    }

    private fun getFormTaskJson3(guid: String): String {
        formtask = db!!.selector(FormTask::class.java).where("RowGuid", "=", guid).findFirst()
        var bzdata: ArrayList<BiaoZhunData> = ArrayList()
        bzdata = db!!.selector(BiaoZhunData::class.java).where("RowGuid", "=", guid)
                .findAll() as ArrayList<BiaoZhunData>
        var jcxdata: ArrayList<JianChuXianData> = ArrayList()
        jcxdata = db!!.selector(JianChuXianData::class.java).where("RowGuid", "=", guid)
                .findAll() as ArrayList<JianChuXianData>
        var lddata: ArrayList<LingDianData> = ArrayList()
        lddata = db!!.selector(LingDianData::class.java).where("RowGuid", "=", guid)
                .findAll() as ArrayList<LingDianData>

        var jsonnow = Jsonxingnengkaohe()
        jsonnow.bzdata = bzdata
        jsonnow.jcxdata = jcxdata
        jsonnow.lddata = lddata
        jsonnow.task = formtask

        return jsonnow.toString()
    }

    private fun getFormTaskJson2(guid: String, pointId: Int): String {
        formtask = db!!.selector(FormTask::class.java).where("RowGuid", "=", guid).findFirst()
        var cc: ArrayList<Check2clean> = ArrayList()
        cc = db!!.selector(Check2clean::class.java).where("RowGuid", "=", guid)
                .findAll() as ArrayList<Check2clean>
        var inst: ArrayList<Instrument> = ArrayList()
        inst = db!!.selector(Instrument::class.java).where("RowGuid", "=", guid)
                .findAll() as ArrayList<Instrument>
        var ssc: ArrayList<StandardSolutionChange> = ArrayList()
        ssc = db!!.selector(StandardSolutionChange::class.java).where("RowGuid", "=", guid)
                .findAll() as ArrayList<StandardSolutionChange>
        var com: ArrayList<Consumables> = ArrayList()
        com = db!!.selector(Consumables::class.java).where("RowGuid", "=", guid)
                .findAll() as ArrayList<Consumables>
        var jsonw = JsonweekFormTask()
        jsonw.check2cleandata = cc
        jsonw.instrumentdata = inst
        jsonw.standardSolutionChangedata = ssc
        jsonw.consumablesdata = com
        jsonw.task = formtask
        return jsonw.toString()
    }


    //实样比对json
    var formtask = FormTask()
    var formtaskfacotr: MutableList<FormTaskFactor> = ArrayList()
    var Checkisnull = false
    private fun getFormTaskJson(guid: String): String {
        var jso = ""
        Checkisnull = false
        try {
            formtask = db!!.selector(FormTask::class.java).where("RowGuid", "=", guid).findFirst()
            formtaskfacotr = formtask.getFormTaskFactor(db!!)
            Log.i("scj", "数据库取任务成功【FormTask】")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("scj", "数据库取任务失败【FormTask】" + e.message)
        }

        //判断测试值是否全部填写
        for (item in formtaskfacotr) {
            if (item.pollutantValue == "") {
                toast("${item.pollutantName}还未填写完整")
                Checkisnull = true
            }
        }

        //为了拼json建的对象
        var jsformtask = JsonFormTask()
        jsformtask.task = formtask
        jsformtask.taskData = formtaskfacotr
        //tostring被重写了
        jso = jsformtask.toString()
        return jso
    }

    private fun getTaskPicturesJson(guid:String):MutableList<String>{
        var json:MutableList<String> = mutableListOf()
        var taskPictures :MutableList<FormTaskPicture>? = mutableListOf()
        try {
            taskPictures = db!!.selector(FormTaskPicture::class.java).where("TaskGuid", "=", guid).findAll()
            Log.i("scj", "数据库取任务成功【FormTaskPicture】")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("scj", "数据库取任务失败【FormTaskPicture】" + e.message)
        }
        if (taskPictures != null) {
            for (item in taskPictures) {
                val jsonTaskPicture = JsonTaskPicture()
                jsonTaskPicture.picture = item
                json.add(jsonTaskPicture.toString())
            }
        }
        return json
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == WRITE_PERMISSION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("hyd", "Write Permission Failed")
                Toast.makeText(this, "You must allow permission write external storage to your mobile device.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestWritePermission(){
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),WRITE_PERMISSION)
        }
    }
}
