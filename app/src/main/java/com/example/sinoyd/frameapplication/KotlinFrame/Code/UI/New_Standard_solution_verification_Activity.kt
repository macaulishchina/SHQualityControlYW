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
import android.widget.*
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
import com.example.sinoyd.jiaxingywapplication.Myapplication
import com.sinoyd.Code.Until.DisplayorhideSoftkeyboard
import com.sinoyd.Code.Until.Networkrequestaddress
import com.sinoyd.Code.Until.SharedPreferencesFactory
import com.sinoyd.Code.Until.showdialog
import com.sinoyd.environmentsz.Kotlin.getToday
import kotlinx.android.synthetic.main.activity_new__standard_solution_verification_.*
import okhttp3.Response
import org.jetbrains.anko.*
import org.xutils.DbManager
import org.xutils.x
import java.util.*

/**标样核查**/
class New_Standard_solution_verification_Activity : BaseActivity() {
    var myapplication: Myapplication = Myapplication()
    var db: DbManager? = null
    var rowGuid = ""
    var formtask = FormTask()
    var formtaskfactorlist: MutableList<FormTaskFactor> = ArrayList()

    var date: Date = Date()
    var pointInfo: PointInfo = PointInfo()
    var pointinfolist: MutableList<PointInfo.PointDataBean> = ArrayList()
    var factorInfo: FactorInfo = com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass.FactorInfo()
    var rl_creatview_with = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new__standard_solution_verification_)
        rl_creatview_with = SharedPreferencesFactory.getdata(act, "width").toInt() - 40
        db = x.getDb(myapplication.getDaoConfig())
        //强制隐藏软键盘
        DisplayorhideSoftkeyboard.hideSoftkeyboard(this)
        //先获取携带值
        rowGuid = intent.getStringExtra("rowGuid")
        if (rowGuid == "新建临时任务") {
            //新建操作
            //站点可以选择
            tv_station_name.enabled = true
            iv_arrow.visibility = View.VISIBLE
            //采样人员
            name.text = SharedPreferencesFactory.getdata(this, "DisplayName")
            //发送请求获取站点
            requestpointinfo()

        } else {
            //站点不可以选择
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
        //时间监听时间
        ll_time.onClick {
            var dateDialog = DateTimePickerControl(act, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            dateDialog.SetDateSelectListener { datestring ->
                ll_time.text = datestring
            }
            dateDialog.show()
        }
        //退出
        tv_exit.onClick {
            tv_save.performClick()
            finish()
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
                //新建状态下保存
                //新建一个任务
                formtask = FormTask()
                formtaskfactorlist = ArrayList()
                //新建状态下保存
                //TODO 判断是否必填项

                //生成一个GUID
                var guid = java.util.UUID.randomUUID().toString()
                formtask.rowGuid = guid
                formtask.formtype = "新建临时任务"
                formtask.formName = "标样核查"//给它表单类型，后面"详情"查看数据的时候，需要用到
                formtask.formCode = "StanLiquidVerification"//给它表单类型，后面"详情"查看数据的时候，需要用到
                for (item in listdview) {
                    var formtaskfactor = FormTaskFactor()
                    formtaskfactor.taskGuid = guid//任务id，关联使用
                    formtaskfactor.stanLiquidCode = item.StanLiquidCode//标液编号*
                    formtaskfactor.dilutionMethod = item.DilutionMethod//稀释方式
                    formtaskfactor.UseAmount = item.ettyl.text.toString()//用量*
                    formtaskfactor.pollutantValue = item.ettcs.text.toString()//测试值*
                    formtaskfactor.pollutantName = item.PollutantName//因子名称*
                    formtaskfactor.pollutantCode = item.pollutantCode//因子名称code*
                    formtaskfactor.unit = item.unit//单位
                    formtaskfactor.remainderUnit = item.RemainderUnit//用量单位
                    formtaskfactor.stanValue = item.StanValue//标准值  *
                    formtaskfactor.surplusAmount = item.SurplusAmount//剩余量  *
                    formtaskfactorlist.add(formtaskfactor)
                }
                formtask.pointName = tv_station_name.text.toString()//站点名称
                if (ll_time.text.toString() == "") {//采样时间
                    formtask.endTime = date.getToday("yyyy/MM/dd HH:mm:ss")
                } else {
                    formtask.endTime = ll_time.text.toString()
                }
                formtask.username = name.text.toString()//采样人员
                formtask.userid = SharedPreferencesFactory.getdata(this, "RowGuid")//用户名id，后面取数据的时候，是判断条件之一
                formtask.taskStatusName = "未上传"


                //进行保存
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
                //TODO 判断是否必填项目
                formtask.formtype = "下发任务"
                formtask.taskStatus = "03"
                if (ll_time.text.toString() == "") {//采样时间
                    formtask.endTime = date.getToday("yyyy/MM/dd HH:mm:ss")
                } else {
                    formtask.endTime = ll_time.text.toString()
                }
                formtask.taskStatusName = "未上传"
                for ((index, item) in listdview.withIndex()) {
                    formtaskfactorlist[index].stanLiquidCode = item.StanLiquidCode
                    formtaskfactorlist[index].pollutantCode = item.pollutantCode
                    formtaskfactorlist[index].DilutionMethod = item.DilutionMethod
                    formtaskfactorlist[index].UseAmount = item.ettyl.text.toString()
                    formtaskfactorlist[index].pollutantValue = item.ettcs.text.toString()
                    formtaskfactorlist[index].stanvalueUnit = item.StanvalueUnit
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

    //赋值页面数据
    private fun setview() {
        tv_station_name.text = formtask.pointName
        ll_time.text = formtask.endTime
        name.text = formtask.username
        createview(formtaskfactorlist)
    }

    //根据rowGuid取数据库值
    private fun getdata4squlite() {
        formtask = getdataforsqlite(db, rowGuid)
        formtaskfactorlist = formtask.getFormTaskFactor(db!!)
    }

    //存放当前界面的因子名称及其因子控件的
    var listdview: MutableList<BYFactorview> = java.util.ArrayList()
    var titlelist = arrayListOf(
            "测试因子",
            "因子单位",
            "标液编号",
            "稀释方式",
//            "扫描", 
            "用量",
            "用量单位",
            "测试值")

    //因子显示情况[动态生成布局]
    data class BYFactorview(
            var PollutantName: String,
            var pollutantCode: String,
            var unit: String,
            var StanLiquidCode: String,
            var DilutionMethod: String,
            var ettyl: EditText,
            var RemainderUnit: String,
            var ettcs: EditText,
            var StanValue: String,
            var SurplusAmount: String,
            var StanvalueUnit: String)

    //因子显示情况[动态生成]
    @SuppressLint("NewApi")
    private fun <T : IFormTaskFactor> createview(formtaskfactorlist: MutableList<T>) {
        ll_title.removeAllViews()
        ll_create.removeAllViews()
        listdview = java.util.ArrayList()
        //每格的布局参数
        val paramsonlyone = RelativeLayout.LayoutParams(Math.floor(rl_creatview_with / (titlelist.size.toDouble())).toInt() - 5, ViewGroup.LayoutParams.WRAP_CONTENT)
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
            aa.gravity = Gravity.CENTER_VERTICAL
            aa.setPadding(0, 5, 0, 5)
            aa.id = index + 1
            aa.orientation = LinearLayout.HORIZONTAL

            //搭建一个TextView   测试因子
            val tv = TextView(act)
            tv.text = item.PollutantName + ":"
            tv.setTextColor(Color.BLACK)
            tv.textSize = 15f
            tv.gravity = Gravity.CENTER
            //将tv装入aa中
            aa.addView(tv, paramsonlyone)

            //搭建一个TextView  单位
            val tvb = TextView(act)
            tvb.text = item.unit
            tvb.setTextColor(Color.BLACK)
            tvb.textSize = 15f
            tvb.gravity = Gravity.CENTER
            //将tv装入aa中
            aa.addView(tvb, paramsonlyone)

            //搭建一个TextView  标液编号
            val tvbyno = TextView(act)
            tvbyno.text = item.StanLiquidCode
            tvbyno.setTextColor(Color.BLACK)
            tvbyno.textSize = 15f
            tvbyno.gravity = Gravity.CENTER
            //将tv装入aa中
            aa.addView(tvbyno, paramsonlyone)

            //搭建一个TextView  释放方式
            val tvsffs = TextView(act)
            tvsffs.text = item.DilutionMethod
            tvsffs.setTextColor(Color.BLACK)
            tvsffs.textSize = 15f
            tvsffs.gravity = Gravity.CENTER
            //将tv装入aa中
            aa.addView(tvsffs, paramsonlyone)


            //扫描   隐藏
            val im = ImageView(act)
            im.setImageResource(R.drawable.title_photo)
            im.padding = 5
            im.onClick {
                toast("扫描")
            }
            im.visibility = View.GONE
            aa.addView(im, paramsonlyone)

            //搭建一个Editview  用量
            val ettyl = EditText(act)
            ettyl.id = index + 100
            ettyl.setSingleLine(true)
            ettyl.gravity = Gravity.CENTER
            ettyl.background = act.baseContext.resources.getDrawable(R.drawable.shape_ed_bold)
            ettyl.setText(item.UseAmount)
            ettyl.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            aa.addView(ettyl, paramsonlyone)

            //搭建一个TextView  单位
            val tvb2 = TextView(act)
            tvb2.text = item.RemainderUnit
            tvb2.setTextColor(Color.BLACK)
            tvb2.textSize = 15f
            tvb2.gravity = Gravity.CENTER
            //将tv装入aa中
            aa.addView(tvb2, paramsonlyone)

            //搭建一个Editview  测试值
            val ettcs = EditText(act)
            ettcs.id = index + 200
            ettcs.setSingleLine(true)
            ettcs.gravity = Gravity.CENTER
            ettcs.background = act.baseContext.resources.getDrawable(R.drawable.shape_ed_bold)
            ettcs.setText(item.PollutantValue)
            ettcs.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            aa.addView(ettcs, paramsonlyone)


            listdview.add(BYFactorview(
                    item.PollutantName,
                    item.PollutantCode,
                    item.unit,
                    item.StanLiquidCode,
                    item.DilutionMethod,
                    ettyl,
                    item.RemainderUnit,
                    ettcs,
                    item.StanValue,
                    item.SurplusAmount,
                    item.StanvalueUnit

            ))
            val params = RelativeLayout.LayoutParams(rl_creatview_with, ViewGroup.LayoutParams.WRAP_CONTENT)
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
        //获取因子信息
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
