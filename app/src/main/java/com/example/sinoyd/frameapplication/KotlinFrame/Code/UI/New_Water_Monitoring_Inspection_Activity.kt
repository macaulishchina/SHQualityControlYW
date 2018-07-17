package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.app.AlertDialog
import android.app.Instrumentation
import android.os.Bundle
import android.util.Log
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Adatper.GridviewAdapter
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Control.getdataforsqlite
import com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass.GetInspectionInfo
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTask
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Weekly_inspection.*
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dataclass.gson
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dialog.DateTimePickerControl
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.Networkrequestmodel
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.View.*
import com.example.sinoyd.frameapplication.KotlinFrame.UI.BaseActivity
import com.example.sinoyd.frameapplication.R
import com.example.sinoyd.frameapplication.R.id.*
import com.example.sinoyd.jiaxingywapplication.Myapplication
import com.macaulish.top.coconut.util.DateKits
import com.macaulish.top.velvet.util.Logger
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
import kotlin.collections.ArrayList

/***巡检**/
class New_Water_Monitoring_Inspection_Activity : BaseActivity() {

    lateinit var db: DbManager

    lateinit var inspectionForm: GetInspectionInfo

    var rowGuid = ""
    var pointId = ""
    var formtask = FormTask()

    /**
     * 用户修改数据
     */
    var checkStatus = ArrayList<Check2clean>()
    var instruments = ArrayList<Instrument>()
    var consumables = ArrayList<Consumables>()
    var reagentsChanges = ArrayList<StandardSolutionChange>()
    var checkInstruments = ArrayList<CheckInstrument>()
    var fixInstruments = ArrayList<FixInstrument>()


    //var date = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_water__monitoring_inspection_)
        db = x.getDb(Myapplication().getDaoConfig())
        //强制隐藏软键盘
        DisplayorhideSoftkeyboard.hideSoftkeyboard(this)
        //先获取携带值
        rowGuid = intent.getStringExtra("rowGuid")
        pointId = intent.getStringExtra("pointId")

        Logger.i("rowGuid = $rowGuid, pointId = $pointId")
        if (rowGuid == "新建临时任务") {
            Logger.i("PointId = " + pointId)
            formtask.formtype = "新建临时任务"
            formtask.taskTypeName = "水质巡检"
            formtask.rowGuid = UUID.randomUUID().toString()
            rowGuid = formtask.rowGuid
            formtask.taskType = "周期任务"
            formtask.startDate = DateKits.getNow("yyyy/MM/dd hh:mm:ss")
            formtask.taskName = "水质巡检"
            formtask.formName = "水质巡检"
            formtask.formCode = "WeekInspection"
            formtask.pointId = pointId.toInt()
            formtask.pointName = SharedPreferencesFactory.getdata(this, "PointName")
            formtask.userid = SharedPreferencesFactory.getdata(this, "RowGuid")
            getInspectionInfo()
        } else {
            //根据rowGuid取数据库值 获取表单

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
        //增加监听事件
        setlisteners()
    }


    /**
     * 获取表单数据请求
     */
    fun getInspectionInfo() {
        showdialog("loadshow")
        var ntrequest = Networkrequestmodel()
        ntrequest.setMethod(Networkrequestmodel.GETREQUEST)
                .settag("getInspectionInfo")
                .seturl(Networkrequestaddress.URL_GetInspectionInfo)
                .addparam("userguid", SharedPreferencesFactory.getdata(this, "RowGuid"))
                .addparam("pointid", pointId)
                .start(this)
    }


    override fun requestSuccess(response: Response, TAG: String) {
        super.requestSuccess(response, TAG)
        when (TAG) {
            "getInspectionInfo" -> {
                inspectionForm = gson.fromJson(responsestr, GetInspectionInfo::class.java)
                //界面搭建
                creatview()
                //恢复页面
                recoverView()
                //设置监听器
                setlisteners()
            }
        }
    }


    /**
     * 构建界面：用[inspectionForm]中数据填充页面，不区分新建任务和查看任务
     */
    private fun creatview() {
        gv_Instrument_condition.adapter = GridviewAdapter(inspectionForm.aroundInspectStatus, this)
        gv_Equipment_condition.adapter = GridviewAdapter(inspectionForm.equipment, this)
        gv_Electrode_cleaning.adapter = GridviewAdapter(inspectionForm.electrodeCleaning, this)
        gv_System_cleaning.adapter = GridviewAdapter(inspectionForm.sysCleaning, this)
        gv_Cleaning_of_instrument_pipe.adapter = GridviewAdapter(inspectionForm.pipelineCleaning, this)
        if (inspectionForm.instrument.size > 0) {
            //仪器更换
            tv_InstrumentName.text = inspectionForm.instrument[0].instrumentName
            tv_OldProductNumber.text = inspectionForm.instrument[0].oldProductNumber
            tv_NewProductNumber.setText("")
            tv_Reason.setText("")

            //仪器校准
            check_tv_InstrumentName.text = inspectionForm.instrument[0].instrumentName
            check_tv_last_time.text = inspectionForm.instrument[0].lastCheckTime
            check_tv_this_time.text = DateKits.getNow()
            check_tv_explain.setText("")
            check_tv_pass.text = ""

            //仪器维修维护
            fix_tv_InstrumentName.text = inspectionForm.instrument[0].instrumentName
            fix_tv_error_time.text = ""
            fix_et_fault_detail.setText("")
            fix_tv_repair_time.text = ""
            fix_et_fix_explain.setText("")
        }
        if (inspectionForm.consumables.size > 0) {
            tv_Consumableyiqi.text = inspectionForm.consumables[0].instrumentName//仪器名称
            tv_Consumablename.text = inspectionForm.consumables[0].toString().split("$")[0]//耗材name
            tv_Consumable_yongliang.setText("")//用量
            tv_Consumable_shuoming.setText("")//原因
        }
        if (inspectionForm.standardSolutionChange.size > 0) {
            sj_tv_InstrumentName.text = inspectionForm.standardSolutionChange[0].instrumentName//仪器名称
            sj_tv_biaoyename.text = inspectionForm.standardSolutionChange[0].stanLiquidName.split("$")[0].split("#")[0]//试剂标液名称
            sj_tv_OldProductNumber.text = inspectionForm.standardSolutionChange[0].stanLiquidName.split("$")[0].split("#")[1]//老系统编号
            sj_tv_NewProductNumber.setText("")//新系统编号
            tv_sj_yongliang.setText("")//用量
            tv_sj_Reason.setText("")//原因
        }
    }


    /**
     * 恢复页面：用真实数据填充页面，区分新建任务和查看任务
     */
    private fun recoverView() {
        when (rowGuid) {
            "新建临时任务" -> {
                formtask = FormTask()
                formtask.formtype = "新建临时任务"
                formtask.taskTypeName = "水质巡检"
                formtask.rowGuid = UUID.randomUUID().toString()
                rowGuid = formtask.rowGuid
                formtask.taskType = "周期任务"
                formtask.startDate = DateKits.getNow("yyyy/MM/dd hh:mm:ss")
                formtask.taskName = "水质巡检"
                formtask.formName = "水质巡检"
                formtask.formCode = "WeekInspection"
                formtask.pointId = pointId.toInt()
                formtask.pointName = SharedPreferencesFactory.getdata(this, "PointName")
                formtask.userid = SharedPreferencesFactory.getdata(this, "RowGuid")
            }
            else -> {
                try {
                    formtask = db.selector(FormTask::class.java)
                            .where("RowGuid", "=", rowGuid)
                            .findFirst()
                } catch (e: Exception) {
                    rowGuid = "新建临时任务"
                    recoverView()
                }
                /**
                 * 获得所有本地数据
                 */
                checkStatus = db.selector(Check2clean::class.java)
                        .where("RowGuid", "=", rowGuid)
                        .findAll() as ArrayList<Check2clean>
                instruments = db.selector(Instrument::class.java)
                        .where("RowGuid", "=", rowGuid)
                        .and("PointId", "=", pointId.toInt())
                        .findAll() as ArrayList<Instrument>
                consumables = db.selector(Consumables::class.java)
                        .where("RowGuid", "=", rowGuid)
                        .and("PointId", "=", pointId.toInt())
                        .findAll() as ArrayList<Consumables>
                reagentsChanges = db.selector(StandardSolutionChange::class.java)
                        .where("RowGuid", "=", rowGuid)
                        .and("PointId", "=", pointId.toInt())
                        .findAll() as ArrayList<StandardSolutionChange>
                checkInstruments = db.selector(CheckInstrument::class.java)
                        .where("RowGuid", "=", rowGuid)
                        .and("PointId", "=", pointId.toInt())
                        .findAll() as ArrayList<CheckInstrument>
                fixInstruments = db.selector(FixInstrument::class.java)
                        .where("RowGuid", "=", rowGuid)
                        .and("PointId", "=", pointId.toInt())
                        .findAll() as ArrayList<FixInstrument>

                /**
                 * 更新显示数据
                 */
                inspectionForm.aroundInspectStatus.clear()
                inspectionForm.equipment.clear()
                inspectionForm.electrodeCleaning.clear()
                inspectionForm.sysCleaning.clear()
                inspectionForm.pipelineCleaning.clear()
                for (status in checkStatus) {
                    val bean = GetInspectionInfo.InspectStatusBean()
                    bean.itemName = status.itemName
                    bean.monitorItemCode = status.monitorItemCode
                    bean.monitorItemText = status.monitorItemText
                    bean.monitorItemType = status.monitorItemType
                    bean.monitorItemTypeName = status.monitorItemTypeName
                    bean.monitorItemValue = status.monitorItemValue
                    bean.monitorReason = status.monitorReason
                    bean.stationID = status.stationID
                    bean.stationName = status.stationName
                    when (bean.monitorItemTypeName) {
                        "仪器状况" -> {
                            inspectionForm.aroundInspectStatus.add(bean)
                        }
                        "设备状况" -> {
                            inspectionForm.equipment.add(bean)
                        }
                        "电极清洗" -> {
                            inspectionForm.electrodeCleaning.add(bean)
                        }
                        "系统清洗" -> {
                            inspectionForm.sysCleaning.add(bean)
                        }
                        "仪表管路清洗" -> {
                            inspectionForm.pipelineCleaning.add(bean)
                        }
                    }
                }
                creatview()
                show_yiqi_change.run {
                    for(i in instruments){
                        val line = "站点名称：${i.monitoringPointName}  原系统编号：${i.oldProductNumber}  新系统编号：${i.newProductNumber}  更换原因：${i.reason}  更换时间：${DateKits.getNow()}${System.lineSeparator()}"
                        this.append(line)
                    }
                }
                show_Consumable_change.run {
                    for(i in consumables){
                        val line = "站点名称：${i.monitoringPointName}  原系统编号：${i.stanLiquidName}  新系统编号：${i.usageValue}  更换原因：${i.reason}  更换时间：${DateKits.getNow()}${System.lineSeparator()}"
                        this.append(line)
                    }
                }
                show_Consumable_change.run {
                    for(i in consumables){
                        val line = "站点名称：${i.monitoringPointName}  原系统编号：${i.stanLiquidName}  新系统编号：${i.usageValue}  更换原因：${i.reason}  更换时间：${DateKits.getNow()}${System.lineSeparator()}"
                        this.append(line)
                    }
                }
            }

        }
    }

    /**
     * 设置页面监听器
     */
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
            db!!.saveOrUpdate(formtask)
            toast("保存成功")
        }


    }


    private fun setview仪器更换(list: ArrayList<Instrument>) {
        var curremtInstrument = Instrument()
        //仪器名称
        runOnUiThread {
            if (list.size > 0) {
                curremtInstrument = list[0]
                tv_InstrumentName.text = list[0].instrumentName
                tv_OldProductNumber.text = list[0].oldProductNumber
                tv_NewProductNumber.setText(list[0].newProductNumber)
                tv_Reason.setText(list[0].reason)
                show_yiqi_change.text = list[0].recordStr
            }
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
                curremtInstrument.recordStr = "站点名称：${curremtInstrument.monitoringPointName}    原系统编号：${curremtInstrument.oldProductNumber}    新系统编号：${tv_NewProductNumber.text}    更换原因：${tv_Reason.text}    更换时间：${date.getToday("yyyy/MM/dd HH:mm:ss")}"
                db!!.update(curremtInstrument)
                //显示 仪器更换 的gridview
                var list = db!!.selector(Instrument::class.java)
                        .where("RowGuid", "=", rowGuid)
                        //.and("PointId", "=", pointId)
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
            if (list.size > 0) {
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
        sj_ll_biaoyename.onClick {
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
                curremtStandardSolutionChange.usageValue = tv_sj_yongliang.text.toString()
                curremtStandardSolutionChange.recordStr = "站点名称：${curremtStandardSolutionChange.monitoringPointName}    原系统编号：${curremtStandardSolutionChange.oldStanLiquidNumber}    新系统编号：${sj_tv_NewProductNumber.text}    用量：${tv_sj_yongliang.text}    更换原因：${tv_sj_Reason.text}    更换时间：${date.getToday("yyyy/MM/dd HH:mm:ss")}"
                db!!.saveOrUpdate(curremtStandardSolutionChange)
                //显示 仪器更换 的gridview
                var list = db!!.selector(StandardSolutionChange::class.java)
                        .where("RowGuid", "=", rowGuid)
                        //.and("PointId", "=", pointId)
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
            if (list.size > 0) {
                currConsumables = list[0]
                tv_Consumableyiqi.text = list[0].instrumentName//仪器名称
                curremtarray = list[0].stanLiquidName.split("$")//耗材list
                tv_Consumablename.text = curremtarray[0].toString()//耗材name
                tv_Consumable_yongliang.setText(list[0].usageValue)//用量
                tv_Consumable_shuoming.setText(list[0].reason)//原因
                show_Consumable_change.text = list[0].recordStr//记录
                currConsumables.consumablesresult = curremtarray[0]
            }
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
                currConsumables.recordStr = "站点名称：${currConsumables.monitoringPointName}    耗材名称：${currConsumables.consumablesresult}    用量：${currConsumables.usageValue}    说明：${currConsumables.reason}    更换时间：${date.getToday("yyyy/MM/dd HH:mm:ss")}"
                db!!.update(currConsumables)
                //显示 仪器更换 的gridview
                var list = db!!.selector(currConsumables::class.java)
                        .where("RowGuid", "=", rowGuid)
                        //.and("PointId", "=", pointId)
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

    private fun setviewCheckInstrument(list: List<CheckInstrument>) {
        var currentCheckInstrument = CheckInstrument()
        runOnUiThread {
            if (list.size > 0) {
                currentCheckInstrument = list[0]
                check_tv_InstrumentName.text = list[0].instrumentName
                check_tv_last_time.text = list[0].lastCheckTime
                check_tv_this_time.text = list[0].thisCheckTime
                check_tv_explain.setText(list[0].checkDetail)
                check_tv_pass.text = list[0].isPass
                show_check_yiqi_change.text = list[0].recordStr
            }
        }
        check_tv_this_time.onClick {
            var dateDialog = DateTimePickerControl(act, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            dateDialog.SetDateSelectListener { datestring ->
                check_tv_this_time.text = datestring
            }
            dateDialog.show()
        }
        check_ll_pass.setOnClickListener {
            val choices = arrayListOf("合格", "不合格")
            var comm = CommonSelectorString2(act, choices, object : CommonSelectorString2.OnSelectClickListener {
                override fun onCommonItemSelect(postions: Int) {
                    check_tv_pass.text = choices[postions]
                }
            })
            comm.showPop()
        }
        check_ll_InstrumentName.onClick {
            var com = CommonSelectorInstrument(act, list as ArrayList<Instrument>, object : CommonSelectorInstrument.OnSelectClickListener {
                override fun onCommonItemSelect(postions: Int) {
                    currentCheckInstrument = list[postions]
                    check_tv_InstrumentName.text = currentCheckInstrument.instrumentName
                    check_tv_last_time.text = currentCheckInstrument.lastCheckTime
                    check_tv_this_time.text = currentCheckInstrument.thisCheckTime
                    check_tv_explain.setText(currentCheckInstrument.checkDetail)
                    check_tv_pass.text = currentCheckInstrument.isPass
                }
            })
            com.showPop()
        }
        //添加
        check_tv_add.onClick {
            if (check_tv_this_time.text.toString() == "" || check_tv_explain.text.toString() == "" || check_tv_pass.text.toString() == "") {
                toast("请输入数据")
            } else {
                currentCheckInstrument.instrumentName = check_tv_InstrumentName.text.toString()
                currentCheckInstrument.lastCheckTime = check_tv_last_time.text.toString()
                currentCheckInstrument.thisCheckTime = check_tv_this_time.text.toString()
                currentCheckInstrument.checkDetail = check_tv_explain.text.toString()
                currentCheckInstrument.isPass = check_tv_pass.text.toString()

                currentCheckInstrument.recordStr = "站点名称：${currentCheckInstrument.monitoringPointName}    上次校准时间：${currentCheckInstrument.lastCheckTime}    本次校准时间：${currentCheckInstrument.thisCheckTime}    校准情况说明：${currentCheckInstrument.checkDetail}    是否合格：${currentCheckInstrument.isPass}"

                db!!.update(currentCheckInstrument)
                var list = db!!.selector(CheckInstrument::class.java)
                        .where("RowGuid", "=", rowGuid)
                        //.and("PointId", "=", pointId)
                        .findFirst() as CheckInstrument
                refushCheckInstrument(currentCheckInstrument)

            }
        }
    }

    fun refushCheckInstrument(list: CheckInstrument) {
        runOnUiThread {
            show_check_yiqi_change.text = list.recordStr.toString()
        }
    }

    private fun setviewFixInstrument(list: List<FixInstrument>) {
        var currentFixInstrument = FixInstrument()
        runOnUiThread {
            if (list.size > 0) {
                currentFixInstrument = list[0]
                fix_tv_InstrumentName.text = list[0].instrumentName
                fix_tv_error_time.text = list[0].faultHappenTime
                fix_et_fault_detail.setText(list[0].faultDetail)
                fix_tv_repair_time.text = list[0].faultFixTime
                fix_et_fix_explain.setText(list[0].fixDetail)
                show_fix_tv_change.text = list[0].recordStr
            }
        }
        fix_ll_error_time.onClick {
            var dateDialog = DateTimePickerControl(act, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            dateDialog.SetDateSelectListener { datestring ->
                fix_tv_error_time.text = datestring
            }
            dateDialog.show()
        }

        fix_ll_repair_time.onClick {
            var dateDialog = DateTimePickerControl(act, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            dateDialog.SetDateSelectListener { datestring ->
                fix_tv_repair_time.text = datestring
            }
            dateDialog.show()
        }

        fix_ll_InstrumentName.onClick {
            var com = CommonSelectorInstrument(act, list as ArrayList<Instrument>, object : CommonSelectorInstrument.OnSelectClickListener {
                override fun onCommonItemSelect(postions: Int) {
                    currentFixInstrument = list[postions]
                    fix_tv_InstrumentName.text = currentFixInstrument.instrumentName
                    fix_tv_error_time.text = currentFixInstrument.faultHappenTime
                    fix_et_fault_detail.setText(currentFixInstrument.faultDetail)
                    fix_tv_repair_time.text = currentFixInstrument.faultFixTime
                    fix_et_fix_explain.setText(currentFixInstrument.fixDetail)
                }
            })
            com.showPop()
        }
        //添加
        fix_tv_add.onClick {
            if (fix_tv_error_time.text.toString() == "" || fix_et_fault_detail.text.toString() == "" || fix_tv_repair_time.text.toString() == "" || fix_et_fix_explain.text.toString() == "") {
                toast("请输入数据")
            } else {
                currentFixInstrument.instrumentName = fix_tv_InstrumentName.text.toString()
                currentFixInstrument.faultHappenTime = fix_tv_error_time.text.toString()
                currentFixInstrument.faultDetail = fix_et_fault_detail.text.toString()
                currentFixInstrument.faultFixTime = fix_tv_repair_time.text.toString()
                currentFixInstrument.fixDetail = fix_et_fix_explain.text.toString()

                currentFixInstrument.recordStr = "站点名称：${currentFixInstrument.monitoringPointName}    故障时间：${currentFixInstrument.faultHappenTime}    故障说明：${currentFixInstrument.faultDetail}    维修时间：${currentFixInstrument.faultFixTime}    维修说明：${currentFixInstrument.fixDetail}"

                db!!.update(currentFixInstrument)
                var list = db!!.selector(FixInstrument::class.java)
                        .where("RowGuid", "=", rowGuid)
                        //.and("PointId", "=", pointId)
                        .findFirst() as FixInstrument
                refushFixInstrument(currentFixInstrument)

            }
        }
    }

    fun refushFixInstrument(list: FixInstrument) {
        runOnUiThread {
            show_fix_tv_change.text = list.recordStr.toString()
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
            inst.pointId = pointId.toInt()
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
            ssc.pointId = pointId.toInt()
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
            con.pointId = pointId.toInt()
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

        //保存仪器校准信息
        val checkInstruments = ArrayList<CheckInstrument>()
        for (item in inspectionInfo.instrument) {
            val check = CheckInstrument()
            check.rowGuid = rowGuid
            check.monitoringinstrumentUid = item.monitoringinstrumentUid
            check.pointId = pointId.toInt()
            check.monitoringPointUid = item.monitoringPointUid
            check.monitoringPointName = item.monitoringPointName
            check.instrumentUid = item.instrumentUid
            check.instrumentName = item.instrumentName
            check.oldProductNumber = item.oldProductNumber
            check.reason = item.reason
            check.newProductNumber = item.newProductNumber
            check.recordStr = item.recordStr
            check.lastCheckTime = date.getToday("yyyy/MM/dd hh:hh:mm")
            checkInstruments.add(check)
        }
        try {
            db!!.save(checkInstruments)
            Log.i("scj", "【检查仪器】表单保存成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("scj", "【检查仪器】表单保存失败")
        }

        //保存维修维护仪器信息
        val fixInstruments = ArrayList<FixInstrument>()
        for (item in inspectionInfo.instrument) {
            val fix = FixInstrument()
            fix.rowGuid = rowGuid
            fix.monitoringinstrumentUid = item.monitoringinstrumentUid
            fix.pointId = pointId.toInt()
            fix.monitoringPointUid = item.monitoringPointUid
            fix.monitoringPointName = item.monitoringPointName
            fix.instrumentUid = item.instrumentUid
            fix.instrumentName = item.instrumentName
            fix.oldProductNumber = item.oldProductNumber
            fix.reason = item.reason
            fix.newProductNumber = item.newProductNumber
            fix.recordStr = item.recordStr
            fix.faultHappenTime = ""
            fix.faultFixTime = ""
            fix.fixDetail = ""
            fix.faultDetail = ""
            fixInstruments.add(fix)
        }
        try {
            db!!.save(fixInstruments)
            Log.i("scj", "【维修仪器】表单保存成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("scj", "【维修仪器】表单保存失败")
        }


    }

}
