package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.os.Looper
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.shenchuanjiang.kotlin1013test.CommonSelector
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Control.getdataforsqlite
import com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass.FactorInfo
import com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass.PointInfo
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTask
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTaskFactor
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.IFormTaskFactor
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dataclass.gson
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dialog.DateTimePickerControl
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.Networkrequestmodel
import com.example.sinoyd.frameapplication.KotlinFrame.UI.BaseActivity
import com.example.sinoyd.frameapplication.R
import com.example.sinoyd.frameapplication.R.id.*
import com.example.sinoyd.jiaxingywapplication.Myapplication
import com.sinoyd.Code.Until.DisplayorhideSoftkeyboard
import com.sinoyd.Code.Until.Networkrequestaddress
import com.sinoyd.Code.Until.SharedPreferencesFactory
import com.sinoyd.Code.Until.showdialog
import com.sinoyd.environmentsz.Kotlin.getToday
import kotlinx.android.synthetic.main.activity_new__realsample_comparison_.*
import okhttp3.Response
import org.jetbrains.anko.*
import org.xutils.DbManager
import org.xutils.x
import java.util.*

/**实样比对**/
class New_Realsample_comparison_Activity : BaseActivity() {

    var myapplication: Myapplication = Myapplication()
    var db: DbManager? = null
    var rowGuid = ""
    var formtask = FormTask()
    var formtaskfactorlist: MutableList<FormTaskFactor> = ArrayList()
    var pointInfo: PointInfo = PointInfo()
    var pointinfolist: MutableList<PointInfo.PointDataBean> = ArrayList()
    var factorInfo: FactorInfo = com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass.FactorInfo()
    var date = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new__realsample_comparison_)
        db = x.getDb(myapplication.getDaoConfig())
        //强制隐藏软键盘
        DisplayorhideSoftkeyboard.hideSoftkeyboard(this)
        //先获取携带值
        rowGuid = intent.getStringExtra("rowGuid")
        if (rowGuid == "新建临时任务") {
            //新建操作
            tv_station_name.enabled = true
            iv_arrow.visibility = View.VISIBLE
            name.text = SharedPreferencesFactory.getdata(this, "DisplayName")
            //发送请求获取站点
            requestpointinfo()

        } else {
            tv_station_name.enabled = false
            //修改操作
            //根据rowGuid取数据库值
            getdata4squlite()
            //赋值页面数据
            setview()
        }

        //设置监听事件
        setlisteners()

    }

    //设置监听事件
    private fun setlisteners() {
        //设置监听采样时间
        ll_time.onClick {
            var dateDialog = DateTimePickerControl(act, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            dateDialog.SetDateSelectListener { datestring ->
                ll_time.text = datestring
            }
            dateDialog.show()
        }

        //选择站点
        tv_station_name.onClick {
            if (pointinfolist.size == 0 || pointinfolist == null) {
                toast("未获取到站点")
            } else {
                var comm = CommonSelector(act, pointinfolist as ArrayList, object : CommonSelector.OnSelectClickListener {
                    override fun onCommonItemSelect(postions: Int) {
                        tv_station_name.text = pointinfolist[postions].namevalue
                        //发送请求获取因子
                        requsetFactorInfo(pointinfolist[postions].idkey)
                    }
                })
                comm.showPop()
            }
        }
        //退出
        tv_exit.onClick {
            tv_save.performClick()
            finish()
        }
        /**
         * 标准
         * 标样考核【StanLiquidVerification】
         * 实样比对【SampleComparison】
         * 性能考核【XingNengKH】
         * 周巡检【WeekInspection】
         * 加标回收【JiaBiaoHS】
         * **/

        //保存
        tv_save.onClick {
            if (rowGuid == "新建临时任务") {
                //新建一个任务
                formtask = FormTask()
                formtaskfactorlist = ArrayList()
                //新建状态下保存
                //TODO 判断是否必填项

                //生成一个GUID
                var guid = java.util.UUID.randomUUID().toString()
                formtask.formtype = "新建临时任务"
                formtask.rowGuid = guid
                formtask.formName = "实样比对"//给它表单类型，后面"详情"查看数据的时候，需要用到
                formtask.formCode = "SampleComparison"//给它表单类型，后面"详情"查看数据的时候，需要用到
                formtask.taskName = "实样对比" //默认任务名称
                formtask.taskTypeName = "实样对比"
                //TODO 可能后续增加 任务要求完成时、任务编号
                 for (item in listdview) {
                    var formtaskfactor = FormTaskFactor()
                    formtaskfactor.taskGuid = guid//任务id，关联使用
                    formtaskfactor.pollutantCode = item.pollutantCode//因子编号
                    formtaskfactor.pollutantName = item.pollutantName//因子名称
                    formtaskfactor.pollutantValue = item.ettt.text.toString()//因子值
                    formtaskfactor.unit = item.unit//单位
                    formtaskfactor.stanValue = item.StanValue//标准值
                    formtaskfactor.surplusAmount = item.SurplusAmount//剩余量
                    formtaskfactorlist.add(formtaskfactor)
                }
                formtask.pointName = tv_station_name.text.toString()//站点名称
                if (ll_time.text.toString() == "") {//采样时间
                    formtask.startDate = date.getToday("yyyy/MM/dd HH:mm:ss")
                } else {
                    formtask.startDate = ll_time.text.toString()
                }

                formtask.username = name.text.toString()//采样人员
                formtask.userid = SharedPreferencesFactory.getdata(this, "RowGuid")//用户名id，后面取数据的时候，是判断条件之一
                formtask.taskStatusName = "未上传"

                var a = false//表示表单因子是否保存成功
                var b = false//表示表单是否保存成功
                a = try {
                    db!!.save(formtaskfactorlist)
                    Log.i("scj", "实样比对因子保存成功")
                    true
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.i("scj", "实样比对因子保存失败")
                    false
                }

                b = try {
                    db!!.save(formtask)
                    Log.i("scj", "实样比对表单保存成功")
                    true
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.i("scj", "实样比对表单保存失败")
                    false
                }

                if (a && b) {
                    toast("保存成功")
                } else if (a) {
                    toast("该表单保存失败")
                } else {
                    toast("该表单因子保存失败")
                }


            } else {
                //修改状态下保存
                //TODO 判断是否必填项
                formtask.formtype = "下发任务"
                formtask.taskStatus = "03"
                if (ll_time.text.toString() == "") {//采样时间
                    formtask.endTime = date.getToday("yyyy/MM/dd HH:mm:ss")
                } else {
                    formtask.endTime = ll_time.text.toString()
                }
                formtask.taskStatusName = "未上传"
                for ((index, item) in listdview.withIndex()) {
                    formtaskfactorlist[index].pollutantValue = item.ettt.text.toString()
                }
                //更新本地数据库
                var a = false//表示表单因子是否保存成功
                var b = false//表示表单是否保存成功
                a = try {
                    db!!.update(formtaskfactorlist)
                    Log.i("scj", "实样比对因子更新成功")
                    true
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.i("scj", "实样比对因子更新失败")
                    false
                }

                b = try {
                    db!!.update(formtask)
                    Log.i("scj", "实样比对表单更新成功")
                    true
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.i("scj", "实样比对表单更新失败")
                    false
                }

                if (a && b) {
                    toast("更新成功")
                } else if (a) {
                    toast("该表单更新失败")
                } else {
                    toast("该表单因子更新失败")
                }

            }
        }
    }

    //根据rowGuid取数据库值
    private fun getdata4squlite() {
        formtask = getdataforsqlite(db, rowGuid)
        formtaskfactorlist = formtask.getFormTaskFactor(db!!)
    }

    //赋值页面数据
    private fun setview() {
        //站点名称显示
        tv_station_name.text = formtask.pointName
        ll_time.text = formtask.endTime
        name.text = formtask.username
        //因子显示情况[动态生成]
        createview(formtaskfactorlist)
    }

    //因子显示情况[动态生成布局]
    data class Factorview(var pollutantCode: String, var pollutantName: String, var unit: String, var ettt: EditText, var StanValue: String, var SurplusAmount: String)

    //存放当前界面的因子名称及其因子控件的
    var listdview: MutableList<Factorview> = java.util.ArrayList()
    var titlelist = arrayListOf("测试因子", "因子单位", "测试值")


    @SuppressLint("NewApi")
    private fun <T : IFormTaskFactor> createview(formtaskfactorlist: MutableList<T>) {
        ll_title.removeAllViews()
        ll_create.removeAllViews()
        listdview = ArrayList()
        //每格的布局参数
        val paramsonlyone = RelativeLayout.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT)

        for (item in titlelist) {
            //搭建一个TextView
            val tv = TextView(act)
            tv.text = item
            tv.setTextColor(Color.BLACK)
            tv.textSize = 15f
            tv.gravity = Gravity.CENTER
            ll_title.addView(tv, paramsonlyone)
        }

        for ((index, item) in formtaskfactorlist.withIndex()) {
            //每一行LinearLayout  一个linearlayout包含一个TV+ET
            val aa = LinearLayout(act)
            aa.gravity = Gravity.CENTER
            aa.setPadding(0, 5, 0, 5)
            aa.id = index + 1
            aa.orientation = LinearLayout.HORIZONTAL

            //搭建一个TextView
            val tv = TextView(act)
            tv.text = item.PollutantName + ":"
            tv.setTextColor(Color.BLACK)
            tv.textSize = 15f
            tv.gravity = Gravity.CENTER
            //将tv装入aa中
            aa.addView(tv, paramsonlyone)

            //搭建一个TextView
            val tvb = TextView(act)
            tvb.text = item.unit
            tvb.setTextColor(Color.BLACK)
            tvb.textSize = 15f
            tvb.gravity = Gravity.CENTER
            //将tv装入aa中
            aa.addView(tvb, paramsonlyone)

            //搭建一个Editview
            val ett = EditText(act)
            ett.id = index + 100
            ett.setSingleLine(true)
            ett.gravity = Gravity.CENTER
            ett.background = act.baseContext.resources.getDrawable(R.drawable.shape_ed_bold)
            ett.setText(item.PollutantValue)
            ett.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            aa.addView(ett, paramsonlyone)

            listdview.add(Factorview(item.PollutantCode, item.PollutantName, item.unit, ett, item.StanValue, item.SurplusAmount))

            val params = RelativeLayout.LayoutParams(600, ViewGroup.LayoutParams.WRAP_CONTENT)
            //指定每一个tv的相对位置  每行两列
//            if (index % 2 != 0) {
//                //要添加“右侧”规则
//                params.addRule(RelativeLayout.RIGHT_OF, index)
//            }
//            if (index >= 2) {
//                //要添加“下方”规则
//                params.addRule(RelativeLayout.BELOW, index - 1)
//            }
//            params.setMargins(0, 0, 0, 10)
            aa.layoutParams = params

            //构造好的布局一一加入xml上去
            ll_create.addView(aa)
        }


    }

    //获取站点请求
    fun requestpointinfo() {
        showdialog("loadshow")
        var ntrequest = Networkrequestmodel()
        ntrequest.setMethod(Networkrequestmodel.GETREQUEST)
                .settag("getPointInfo")
                .seturl(Networkrequestaddress.URL_PointInfo)
                .addparam("userGuid", SharedPreferencesFactory.getdata(this, "RowGuid"))
                .start(this)
    }

    //获取因子
    fun requsetFactorInfo(pointId: String) {
        showdialog("loadshow")
        var ntrequest = Networkrequestmodel()
        ntrequest.setMethod(Networkrequestmodel.GETREQUEST)
                .settag("getFactorInfo")
                .seturl(Networkrequestaddress.URL_FactorInfo)
                .addparam("userGuid", SharedPreferencesFactory.getdata(this, "RowGuid"))
                .addparam("pointId", pointId)
                .start(this)
    }

    override fun requestSuccess(response: Response, TAG: String) {
        super.requestSuccess(response, TAG)
        when (TAG) {
        //获取站点信息
            "getPointInfo" -> {
                try {
                    pointInfo = gson.fromJson(responsestr, PointInfo::class.java)
                    if (pointInfo.result == "True") {
                        pointinfolist = ArrayList()
                        pointinfolist = pointInfo.pointData
                    } else {
                        Looper.prepare()
                        toast(pointInfo.message)
                        Looper.loop()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            "getFactorInfo" -> {
                try {
                    factorInfo = gson.fromJson(responsestr, FactorInfo::class.java)
                    if (factorInfo.result == "True") {
                        //搭建界面
                        var viewlist = factorInfo.pollutantData
                        //更新UI
                        runOnUiThread { createview(viewlist) }
                    } else {
                        Looper.prepare()
                        toast(pointInfo.message)
                        Looper.loop()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }
}
