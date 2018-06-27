package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Adatper.GridviewAdapter
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Control.getdataforsqlite
import com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass.GetInspectionInfo
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTask
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Weekly_inspection.Check2clean
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Weekly_inspection.Consumables
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Weekly_inspection.Instrument
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Weekly_inspection.StandardSolutionChange
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dataclass.gson
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.Networkrequestmodel
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.View.*
import com.example.sinoyd.frameapplication.KotlinFrame.UI.BaseActivity
import com.example.sinoyd.frameapplication.R
import com.example.sinoyd.jiaxingywapplication.Myapplication
import com.sinoyd.Code.Until.DisplayorhideSoftkeyboard
import com.sinoyd.Code.Until.Networkrequestaddress
import com.sinoyd.Code.Until.SharedPreferencesFactory
import com.sinoyd.Code.Until.showdialog
import com.sinoyd.environmentsz.Kotlin.getToday
import kotlinx.android.synthetic.main.activity_new_water__monitoring_inspection_.*
import okhttp3.Response
import org.jetbrains.anko.act
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast
import org.xutils.DbManager
import org.xutils.x
import java.util.*

/***巡检**/
class New_Water_Monitoring_Inspection_Activity : BaseActivity() {
    var myapplication: Myapplication = Myapplication()
    var db: DbManager? = null
    var rowGuid = ""
    var pointid = ""
    var formtask = FormTask()
    var date = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_water__monitoring_inspection_)
        db = x.getDb(myapplication.getDaoConfig())
        //强制隐藏软键盘
        DisplayorhideSoftkeyboard.hideSoftkeyboard(this)
        //先获取携带值
        rowGuid = intent.getStringExtra("rowGuid")
        pointid = intent.getStringExtra("pointId")

        if (rowGuid == "新建临时任务") {

        } else {
            //增加监听事件
            setlisteners()
            //根据rowGuid取数据库值 获取表单
            getdata4squlite()

            //去检查数据库有没有对应表格的配置信息
            var list = db!!.selector(Check2clean::class.java).findAll()
            if (list == null) {
                //发请求去获取配置
                getInspectionInfo()
            } else if (db!!.selector(Check2clean::class.java)
                            .where("RowGuid", "=", rowGuid)
                            .and("MonitorItemTypeName", "=", "仪器状况")
                            .findAll().size > 0) {
                //所有界面搭建
                creatview()
            } else {
                //发请求去获取配置
                getInspectionInfo()
            }
        }
    }

    //增加监听事件
    private fun setlisteners() {
        //退出
        tv_exit.onClick {
            finish()
        }
        //保存
        tv_save.onClick {
            formtask.taskStatusName = "未上传"
            formtask.formtype = "下发任务"
            formtask.taskStatus = "03"
            db!!.update(adapter仪器状况!!.newlist)
            db!!.update(adapter设备状况!!.newlist)
            db!!.update(adapter电极清洗!!.newlist)
            db!!.update(adapter系统清洗!!.newlist)
            db!!.update(adapter管路清洗!!.newlist)
            db!!.update(list仪器更换)
            db!!.update(list试剂标液更换)
            db!!.update(list耗材更换)
            db!!.update(formtask)
            toast("保存成功")
        }


    }

    //根据rowGuid取数据库值
    private fun getdata4squlite() {
        formtask = getdataforsqlite(db, rowGuid)
    }

    //获取界面配置
    fun getInspectionInfo() {
        showdialog("loadshow")
        var ntrequest = Networkrequestmodel()
        ntrequest.setMethod(Networkrequestmodel.GETREQUEST)
                .settag("getInspectionInfo")
                .seturl(Networkrequestaddress.URL_GetInspectionInfo)
                .addparam("userguid", SharedPreferencesFactory.getdata(this, "RowGuid"))
                .addparam("pointid", pointid)
                .start(this)
    }


    var getInspectionInfo = GetInspectionInfo()
    override fun requestSuccess(response: Response, TAG: String) {
        super.requestSuccess(response, TAG)
        when (TAG) {
            "getInspectionInfo" -> {
                getInspectionInfo = gson.fromJson(responsestr, GetInspectionInfo::class.java)
                //存数据去本地  所有【状况】 存入
                dbsave(getInspectionInfo)
                //界面搭建
                creatview()
            }
        }
    }


    //搭建界面
    private fun creatview() {
        /*********仪器状况************/
        list仪器状况 = db!!.selector(Check2clean::class.java)
                .where("RowGuid", "=", rowGuid)
                .and("MonitorItemTypeName", "=", "仪器状况")
                .findAll() as ArrayList<Check2clean>
        setview仪器状况(list仪器状况)
        /*********设备状况************/
        list设备状况 = db!!.selector(Check2clean::class.java)
                .where("RowGuid", "=", rowGuid)
                .and("MonitorItemTypeName", "=", "设备状况")
                .findAll() as ArrayList<Check2clean>
        setview设备状况(list设备状况)
        /*********电极清洗************/
        list电极清洗 = db!!.selector(Check2clean::class.java)
                .where("RowGuid", "=", rowGuid)
                .and("MonitorItemTypeName", "=", "电极清洗")
                .findAll() as ArrayList<Check2clean>
        setview电极清洗(list电极清洗)
        /*********系统清洗************/
        list系统清洗 = db!!.selector(Check2clean::class.java)
                .where("RowGuid", "=", rowGuid)
                .and("MonitorItemTypeName", "=", "系统清洗")
                .findAll() as ArrayList<Check2clean>
        setview系统清洗(list系统清洗)
        /*********管路清洗************/
        list管路清洗 = db!!.selector(Check2clean::class.java)
                .where("RowGuid", "=", rowGuid)
                .and("MonitorItemTypeName", "=", "仪表管路清洗")
                .findAll() as ArrayList<Check2clean>
        setview管路清洗(list管路清洗)
        /*********仪器更换************/
        list仪器更换 = db!!.selector(Instrument::class.java)
                .where("RowGuid", "=", rowGuid)
                .and("PointId", "=", pointid)
                .findAll() as ArrayList<Instrument>
        setview仪器更换(list仪器更换)
        /*********试剂标液更换************/
        list试剂标液更换 = db!!.selector(StandardSolutionChange::class.java)
                .where("RowGuid", "=", rowGuid)
                .and("PointId", "=", pointid)
                .findAll() as ArrayList<StandardSolutionChange>
        setview试剂标液更换(list试剂标液更换)
        /*********耗材更换************/
        list耗材更换 = db!!.selector(Consumables::class.java)
                .where("RowGuid", "=", rowGuid)
                .and("PointId", "=", pointid)
                .findAll() as ArrayList<Consumables>
        setview耗材更换(list耗材更换)
    }


    var list仪器状况: ArrayList<Check2clean> = ArrayList()
    var list设备状况: ArrayList<Check2clean> = ArrayList()
    var list电极清洗: ArrayList<Check2clean> = ArrayList()
    var list系统清洗: ArrayList<Check2clean> = ArrayList()
    var list管路清洗: ArrayList<Check2clean> = ArrayList()
    var list仪器更换: ArrayList<Instrument> = ArrayList()
    var list试剂标液更换: ArrayList<StandardSolutionChange> = ArrayList()
    var list耗材更换: ArrayList<Consumables> = ArrayList()
    var adapter仪器状况: GridviewAdapter? = null
    var adapter设备状况: GridviewAdapter? = null
    var adapter电极清洗: GridviewAdapter? = null
    var adapter系统清洗: GridviewAdapter? = null
    var adapter管路清洗: GridviewAdapter? = null

    private fun setview仪器状况(list: ArrayList<Check2clean>) {
        runOnUiThread {
            adapter仪器状况 = GridviewAdapter(list, this)
            gv_Instrument_condition.adapter = adapter仪器状况
        }
    }

    private fun setview设备状况(list: ArrayList<Check2clean>) {
        runOnUiThread {
            adapter设备状况 = GridviewAdapter(list, this)
            gv_Equipment_condition.adapter = adapter设备状况
        }
    }

    private fun setview电极清洗(list: ArrayList<Check2clean>) {
        runOnUiThread {
            adapter电极清洗 = GridviewAdapter(list, this)
            gv_Electrode_cleaning.adapter = adapter电极清洗
        }
    }

    private fun setview系统清洗(list: ArrayList<Check2clean>) {
        runOnUiThread {
            adapter系统清洗 = GridviewAdapter(list, this)
            gv_System_cleaning.adapter = adapter系统清洗
        }
    }

    private fun setview管路清洗(list: ArrayList<Check2clean>) {
        runOnUiThread {
            adapter管路清洗 = GridviewAdapter(list, this)
            gv_Cleaning_of_instrument_pipe.adapter = adapter管路清洗
        }
    }

    private fun setview仪器更换(list: ArrayList<Instrument>) {
        var curremtInstrument = Instrument()
        //仪器名称
        runOnUiThread {
            curremtInstrument = list[0]
            tv_InstrumentName.text = list[0].instrumentName
            tv_OldProductNumber.text = list[0].oldProductNumber
            tv_NewProductNumber.setText(list[0].newProductNumber)
            tv_Reason.setText(list[0].reason)
            show_yiqi_change.text = list[0].recordStr
        }
        ll_InstrumentName.onClick {
            var com = CommonSelectorInstrument(act, list, object : CommonSelectorInstrument.OnSelectClickListener {
                override fun onCommonItemSelect(postions: Int) {
                    curremtInstrument = list[postions]
                    tv_InstrumentName.text = curremtInstrument.instrumentName
                    tv_OldProductNumber.text = curremtInstrument.oldProductNumber
                    tv_NewProductNumber.setText(curremtInstrument.newProductNumber)
                    tv_Reason.setText(curremtInstrument.reason)
                    show_yiqi_change.text = curremtInstrument.recordStr
                }
            })
            com.showPop()
        }
        //添加
        yiqi_tv_add.onClick {
            if (tv_NewProductNumber.text.toString() == "" || tv_Reason.text.toString() == "") {
                toast("请输入数据")
            } else {
                curremtInstrument.newProductNumber = tv_NewProductNumber.text.toString()
                curremtInstrument.reason = tv_Reason.text.toString()
                curremtInstrument.recordStr = curremtInstrument.recordStr + "\n" + "站点名称：${curremtInstrument.monitoringPointName}    原系统编号：${curremtInstrument.oldProductNumber}    新系统编号：${tv_NewProductNumber.text}    更换原因：${tv_Reason.text}    更换时间：${date.getToday("yyyy/MM/dd HH:mm:ss")}"
                db!!.update(curremtInstrument)
                //显示 仪器更换 的gridview
                var list = db!!.selector(Instrument::class.java)
                        .where("RowGuid", "=", rowGuid)
                        .and("PointId", "=", pointid)
                        .and("InstrumentName", "=", curremtInstrument.instrumentName)
                        .findFirst() as Instrument
                refush仪器更换(list)
            }
        }

    }

    private fun refush仪器更换(list: Instrument) {
        runOnUiThread {
            show_yiqi_change.text = list.recordStr.toString()
        }
    }

    private fun setview试剂标液更换(list: ArrayList<StandardSolutionChange>) {
        var curremtStandardSolutionChange = StandardSolutionChange()
        var curremtarray: List<String> = ArrayList()
        //仪器名称
        runOnUiThread {
            curremtStandardSolutionChange = list[0]
            sj_tv_InstrumentName.text = list[0].instrumentName//仪器名称
            curremtarray = list[0].stanLiquidName.split("$")
            sj_tv_biaoyename.text = curremtarray[0].split("#")[0]//试剂标液名称
            sj_tv_OldProductNumber.text = curremtarray[0].split("#")[1]//老系统编号
            sj_tv_NewProductNumber.setText(list[0].newStanLiquidNumber)//新系统编号
            tv_sj_yongliang.setText(list[0].usageValue)//用量
            tv_sj_Reason.setText(list[0].reason)//原因
            show_sj_change.text = list[0].recordStr//记录
        }
        //仪器名称
        sj_ll_InstrumentName.onClick {
            var com = CommonSelectorStandardSolutionChange(act, list, object : CommonSelectorStandardSolutionChange.OnSelectClickListener {
                override fun onCommonItemSelect(postions: Int) {
                    curremtStandardSolutionChange = list[postions]
                    curremtarray = list[postions].stanLiquidName.split("$")
                    sj_tv_InstrumentName.text = curremtStandardSolutionChange.instrumentName//仪器名称
                    sj_tv_biaoyename.text = curremtarray[0].split("#")[0]//标液名称
                    sj_tv_OldProductNumber.text = curremtarray[0].split("#")[1]//老系统编号
                    sj_tv_NewProductNumber.setText(curremtStandardSolutionChange.newStanLiquidNumber)//新系统编号
                    tv_sj_yongliang.setText(curremtStandardSolutionChange.usageValue)//用量
                    tv_sj_Reason.setText(curremtStandardSolutionChange.reason)//原因
                    show_sj_change.text = curremtStandardSolutionChange.recordStr//记录
                }
            })
            com.showPop()
        }
        //试剂标液名称
        sj_tv_biaoyename.onClick {
            var comm = CommonSelectorString2(act, curremtarray as ArrayList<String>, object : CommonSelectorString2.OnSelectClickListener {
                override fun onCommonItemSelect(postions: Int) {
                    sj_tv_biaoyename.text = curremtarray[postions].split("#")[0]
                    sj_tv_OldProductNumber.text = curremtarray[postions].split("#")[1]
                }
            })
            comm.showPop()
        }
        //添加
        sj_tv_add.onClick {
            if (sj_tv_NewProductNumber.text.toString() == "" || tv_sj_Reason.text.toString() == "" || tv_sj_yongliang.text.toString() == "") {
                toast("请输入数据")
            } else {
                curremtStandardSolutionChange.newStanLiquidNumber = sj_tv_NewProductNumber.text.toString()
                curremtStandardSolutionChange.reason = tv_sj_Reason.text.toString()
                curremtStandardSolutionChange.recordStr = curremtStandardSolutionChange.recordStr + "\n" + "站点名称：${curremtStandardSolutionChange.monitoringPointName}    原系统编号：${curremtStandardSolutionChange.oldStanLiquidNumber}    新系统编号：${sj_tv_NewProductNumber.text}    用量：${tv_sj_yongliang.text}    更换原因：${tv_sj_Reason.text}    更换时间：${date.getToday("yyyy/MM/dd HH:mm:ss")}"
                curremtStandardSolutionChange.usageValue = tv_sj_yongliang.text.toString()
                db!!.update(curremtStandardSolutionChange)
                //显示 仪器更换 的gridview
                var list = db!!.selector(StandardSolutionChange::class.java)
                        .where("RowGuid", "=", rowGuid)
                        .and("PointId", "=", pointid)
                        .and("InstrumentName", "=", curremtStandardSolutionChange.instrumentName)
                        .findFirst() as StandardSolutionChange
                refush试剂更换(list)
            }
        }

    }

    fun refush试剂更换(list: StandardSolutionChange) {
        runOnUiThread {
            show_sj_change.text = list.recordStr.toString()
        }
    }


    private fun setview耗材更换(list: ArrayList<Consumables>) {
        var currConsumables = Consumables()
        var curremtarray: List<String> = ArrayList()
        //仪器名称
        runOnUiThread {
            currConsumables = list[0]
            tv_Consumableyiqi.text = list[0].instrumentName//仪器名称
            curremtarray = list[0].stanLiquidName.split("$")//耗材list
            tv_Consumablename.text = curremtarray[0].toString()//耗材name
            tv_Consumable_yongliang.setText(list[0].usageValue)//用量
            tv_Consumable_shuoming.setText(list[0].reason)//原因
            show_Consumable_change.text = list[0].recordStr//记录
            currConsumables.consumablesresult = curremtarray[0]
        }
        //仪器名称选择
        ll_Consumable.onClick {
            var conn = CommonSelectorConsumables(act, list, object : CommonSelectorConsumables.OnSelectClickListener {
                override fun onCommonItemSelect(postions: Int) {
                    tv_Consumableyiqi.text = list[postions].instrumentName//仪器名称
                    curremtarray = list[postions].stanLiquidName.split("$")
                    tv_Consumablename.text = curremtarray[postions].toString()//耗材name
                    tv_Consumable_yongliang.setText(list[postions].usageValue)//用量
                    tv_Consumable_shuoming.setText(list[postions].reason)//原因
                    show_Consumable_change.text = list[postions].recordStr//记录
                }
            })
            conn.showPop()
        }
        //耗材选择
        tv_Consumablename.onClick {
            var comm = CommonSelectorString(act, curremtarray as ArrayList<String>, object : CommonSelectorString.OnSelectClickListener {
                override fun onCommonItemSelect(postions: Int) {
                    tv_Consumablename.text = curremtarray[postions].toString()//耗材name
                }
            })
            comm.showPop()
        }
        //添加
        Consumable_tv_add.onClick {
            if (tv_Consumable_yongliang.text.toString() == "" || tv_Consumable_shuoming.text.toString() == "") {
                toast("请输入数据")
            } else {
                currConsumables.consumablesresult = tv_Consumablename.text.toString()
                currConsumables.usageValue = tv_Consumable_yongliang.text.toString()
                currConsumables.reason = tv_Consumable_shuoming.text.toString()
                currConsumables.recordStr = currConsumables.recordStr + "\n" + "站点名称：${currConsumables.monitoringPointName}    耗材名称：${currConsumables.consumablesresult}    用量：${currConsumables.usageValue}    说明：${currConsumables.reason}    更换时间：${date.getToday("yyyy/MM/dd HH:mm:ss")}"
                db!!.update(currConsumables)
                //显示 仪器更换 的gridview
                var list = db!!.selector(currConsumables::class.java)
                        .where("RowGuid", "=", rowGuid)
                        .and("PointId", "=", pointid)
                        .and("InstrumentName", "=", currConsumables.instrumentName)
                        .findFirst() as Consumables
                refush耗材更换(list)
            }
        }
    }

    fun refush耗材更换(list: Consumables) {
        runOnUiThread {
            show_Consumable_change.text = list.recordStr.toString()
        }
    }

    //存数据去本地
    private fun dbsave(inspectionInfo: GetInspectionInfo) {
        /***所有【状况】 存入***/
        var zklist: ArrayList<Check2clean> = ArrayList()
        //仪器状况
        for (item in inspectionInfo.aroundInspectStatus) {
            var cc = Check2clean()
            cc.rowGuid = rowGuid
            cc.stationID = item.stationID
            cc.stationName = item.stationName
            cc.itemName = item.itemName
            cc.monitorItemType = item.monitorItemType
            cc.monitorItemTypeName = item.monitorItemTypeName
            cc.monitorItemCode = item.monitorItemCode
            cc.monitorItemText = item.monitorItemText
            cc.monitorItemValue = item.monitorItemValue
            cc.monitorReason = item.monitorReason
            zklist.add(cc)
        }
        //设备状况
        for (item in inspectionInfo.electrodeCleaning) {
            var cc = Check2clean()
            cc.rowGuid = rowGuid
            cc.stationID = item.stationID
            cc.stationName = item.stationName
            cc.itemName = item.itemName
            cc.monitorItemType = item.monitorItemType
            cc.monitorItemTypeName = item.monitorItemTypeName
            cc.monitorItemCode = item.monitorItemCode
            cc.monitorItemText = item.monitorItemText
            cc.monitorItemValue = item.monitorItemValue
            cc.monitorReason = item.monitorReason
            zklist.add(cc)
        }
        //电ji清理
        for (item in inspectionInfo.equipment) {
            var cc = Check2clean()
            cc.rowGuid = rowGuid
            cc.stationID = item.stationID
            cc.stationName = item.stationName
            cc.itemName = item.itemName
            cc.monitorItemType = item.monitorItemType
            cc.monitorItemTypeName = item.monitorItemTypeName
            cc.monitorItemCode = item.monitorItemCode
            cc.monitorItemText = item.monitorItemText
            cc.monitorItemValue = item.monitorItemValue
            cc.monitorReason = item.monitorReason
            zklist.add(cc)
        }
        //系统清理
        for (item in inspectionInfo.sysCleaning) {
            var cc = Check2clean()
            cc.rowGuid = rowGuid
            cc.stationID = item.stationID
            cc.stationName = item.stationName
            cc.itemName = item.itemName
            cc.monitorItemType = item.monitorItemType
            cc.monitorItemTypeName = item.monitorItemTypeName
            cc.monitorItemCode = item.monitorItemCode
            cc.monitorItemText = item.monitorItemText
            cc.monitorItemValue = item.monitorItemValue
            cc.monitorReason = item.monitorReason
            zklist.add(cc)
        }
        //管路清洗
        for (item in inspectionInfo.pipelineCleaning) {
            var cc = Check2clean()
            cc.rowGuid = rowGuid
            cc.stationID = item.stationID
            cc.stationName = item.stationName
            cc.itemName = item.itemName
            cc.monitorItemType = item.monitorItemType
            cc.monitorItemTypeName = item.monitorItemTypeName
            cc.monitorItemCode = item.monitorItemCode
            cc.monitorItemText = item.monitorItemText
            cc.monitorItemValue = item.monitorItemValue
            cc.monitorReason = item.monitorReason
            zklist.add(cc)
        }
        try {
            db!!.save(zklist)
            Log.i("scj", "【状况】表单保存成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("scj", "【状况】表单保存失败")
        }

        /***所有【Instrument】 仪器  存入***/
        var yqlist: ArrayList<Instrument> = ArrayList()
        for (item in inspectionInfo.instrument) {
            var inst = Instrument()
            inst.rowGuid = rowGuid
            inst.monitoringinstrumentUid = item.monitoringinstrumentUid
            inst.pointId = item.pointId
            inst.monitoringPointUid = item.monitoringPointUid
            inst.monitoringPointName = item.monitoringPointName
            inst.instrumentUid = item.instrumentUid
            inst.instrumentName = item.instrumentName
            inst.oldProductNumber = item.oldProductNumber
            inst.reason = item.reason
            inst.newProductNumber = item.newProductNumber
            inst.recordStr = item.recordStr
            yqlist.add(inst)
        }
        try {
            db!!.save(yqlist)
            Log.i("scj", "【仪器】表单保存成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("scj", "【仪器】表单保存失败")
        }

        /***所有【StandardSolutionChange】 实剂标液更换  存入***/
        var byclist: ArrayList<StandardSolutionChange> = ArrayList()
        for (item in inspectionInfo.standardSolutionChange) {
            var ssc = StandardSolutionChange()
            ssc.rowGuid = rowGuid
            ssc.monitoringinstrumentUid = item.monitoringinstrumentUid
            ssc.pointId = item.pointId
            ssc.monitoringPointUid = item.monitoringPointUid
            ssc.monitoringPointName = item.monitoringPointName
            ssc.instrumentName = item.instrumentName
            ssc.instrumentUid = item.instrumentUid
            ssc.stanLiquidName = item.stanLiquidName
            ssc.stanLiquidGuid = item.stanLiquidGuid

            ssc.standSelectValue = item.standSelectValue
            ssc.oldStanLiquidNumber = item.oldStanLiquidNumber
            ssc.newStanLiquidNumber = item.newStanLiquidNumber
            ssc.usageValue = item.usageValue
            ssc.reason = item.reason
            ssc.recordStr = item.recordStr

            byclist.add(ssc)
        }
        try {
            db!!.save(byclist)
            Log.i("scj", "【标液更换】表单保存成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("scj", "【标液更换】表单保存失败")
        }


        /***所有【Consumables】 耗材更换  存入***/
        var Consumableslist: ArrayList<Consumables> = ArrayList()
        for (item in inspectionInfo.consumables) {
            var con = Consumables()
            con.rowGuid = rowGuid
            con.monitoringinstrumentUid = item.monitoringinstrumentUid
            con.pointId = item.pointId
            con.monitoringPointUid = item.monitoringPointUid
            con.monitoringPointName = item.monitoringPointName
            con.instrumentUid = item.instrumentUid
            con.instrumentName = item.instrumentName
            con.stanLiquidName = item.stanLiquidName
            con.consumablesresult = item.consumablesresult
            con.usageValue = item.usageValue
            con.reason = item.reason
            con.recordStr = item.recordStr
            Consumableslist.add(con)
        }

        try {
            db!!.save(Consumableslist)
            Log.i("scj", "【耗材更换】表单保存成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("scj", "【耗材更换】表单保存失败")
        }

    }


}
