package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
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
import com.example.sinoyd.frameapplication.KotlinFrame.Code.jso.JsonweekFormTask
import com.example.sinoyd.frameapplication.KotlinFrame.Code.jso.Jsonxingnengkaohe
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dataclass.gson
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.Networkrequestmodel
import com.example.sinoyd.frameapplication.KotlinFrame.UI.BaseActivity
import com.example.sinoyd.frameapplication.R
import com.example.sinoyd.frameapplication.R.id.iv_home
import com.example.sinoyd.frameapplication.R.id.lv_task_management
import com.example.sinoyd.jiaxingywapplication.Myapplication
import com.sinoyd.Code.Until.Networkrequestaddress
import com.sinoyd.Code.Until.SharedPreferencesFactory
import com.sinoyd.Code.Until.showdialog
import kotlinx.android.synthetic.main.activity_task_management_.*
import okhttp3.Response
import org.jetbrains.anko.act
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.xutils.DbManager
import org.xutils.x


/**任务管理**/
class Task_management_Activity : BaseActivity() {
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
            }
            super.handleMessage(msg)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_management_)
        db = x.getDb(myapplication.getDaoConfig())
        //设置监听
        setlisteners()
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
        startActivity<AddOrUpdate_Picture_Activity>("rowGuid" to guid)
    }

    /**
     * 判断这条记录本地是否有待上传图片
     */
    fun containPicture(guid : String):Boolean{
        val res = db!!.selector(FormTaskPicture::class.java).where("RowGuid", "=", guid).findAll()
        return !(res == null || res.isEmpty())
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


}
