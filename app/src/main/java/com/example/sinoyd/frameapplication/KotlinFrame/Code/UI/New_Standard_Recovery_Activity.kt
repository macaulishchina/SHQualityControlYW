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
import kotlinx.android.synthetic.main.activity_new__standard__recovery_.*
import okhttp3.Response
import org.jetbrains.anko.*
import org.xutils.DbManager
import org.xutils.x
import java.util.*

/**加标回收**/
class New_Standard_Recovery_Activity : BaseActivity() {
    var myapplication: Myapplication = Myapplication()
    var db: DbManager? = null
    var rowGuid = ""
    var formtask = FormTask()
    var date = Date()

    var pointInfo: PointInfo = PointInfo()
    var pointinfolist: MutableList<PointInfo.PointDataBean> = ArrayList()
    var factorInfo: FactorInfo = com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass.FactorInfo()
    var formtaskfactorlist: MutableList<FormTaskFactor> = ArrayList()
    var formtaskfactorlistall: MutableList<FormTaskFactor> = ArrayList()
    var rl_creatview_with = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new__standard__recovery_)
        rl_creatview_with = SharedPreferencesFactory.getdata(act, "width").toInt() - 40
        db = x.getDb(myapplication.getDaoConfig())
        //强制隐藏软键盘
        DisplayorhideSoftkeyboard.hideSoftkeyboard(this)
        //先获取携带值
        rowGuid = intent.getStringExtra("rowGuid")

        if (rowGuid == "新建临时任务") {
            //新建操作
            tv_station_name.enabled = true
            iv_arrow.visibility= View.VISIBLE
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
    }

    override fun onResume() {
        super.onResume()
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
        tv_sign_out.onClick {
            finish()
            Log.i("scj", "退出")
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
        tv_preservation.onClick {
            if (rowGuid == "新建临时任务") {
                //新建一个任务
                formtask = FormTask()
                formtaskfactorlist = ArrayList()
                //新建状态下保存
                //TODO 判断是否必填项

                //生成一个GUID
                var guid = java.util.UUID.randomUUID().toString()
                formtask.rowGuid = guid
                formtask.formtype = "新建临时任务"
                formtask.formName = "加标回收"//给它表单类型，后面"详情"查看数据的时候，需要用到
                formtask.formCode = "JiaBiaoHS"//给它表单类型，后面"详情"查看数据的时候，需要用到
                //TODO 可能后续增加 任务编号，任务名称，任务类型，任务要求完成时间，
                for (item in listdview) {
                    var formtaskfactor = FormTaskFactor()
                    formtaskfactor.taskGuid = guid//任务id，关联使用
                    formtaskfactor.pollutantCode = item.PollutantName//因子编号
                    formtaskfactor.pollutantName = item.PollutantName//因子名称
                    formtaskfactor.unit = item.unit//单位
                    formtaskfactor.stanValue = item.StanValue//标准值
                    //标液编号
                    formtaskfactor.StanLiquidCode = item.StanLiquidCode
                    //稀释方式
                    formtaskfactor.DilutionMethod = item.DilutionMethod
                    //标样值
                    formtaskfactor.StanValue = item.StanValue
                    //标液用量
                    formtaskfactor.UseAmount = item.ettbyyl.text.toString()
                    //测试值
                    formtaskfactor.PollutantValue = item.ettctz.text.toString()
                    //测试加标值
                    formtaskfactor.PollutantValueAdd = item.ettkhz.text.toString()
                    //加标量
                    formtaskfactor.AddValue = ""  //先给空，后续改
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

                var a = false//表示表单因子是否保存成功
                var b = false//表示表单是否保存成功
                a = try {
                    db!!.save(formtaskfactorlist)
                    Log.i("scj", "加标回收因子保存成功")
                    true
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.i("scj", "加标回收因子保存失败")
                    false
                }

                b = try {
                    db!!.save(formtask)
                    Log.i("scj", "加标回收表单保存成功")
                    true
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.i("scj", "加标回收表单保存失败")
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
                /***
                 *
                 *   NotDown	01	未下发
                 *  AlreadyDown	02	已下发
                 *  Uncompleted	03	未完成
                 *  Completed	04	已完成
                 *  SysClose	05	系统自动关闭
                 *
                 * **/
                if (ll_time.text.toString() == "") {//采样时间
                    formtask.endTime = date.getToday("yyyy/MM/dd HH:mm:ss")
                } else {
                    formtask.endTime = ll_time.text.toString()
                }
                formtask.taskStatusName = "未上传"
                formtask.formtype = "下发任务"
                formtask.taskStatus = "03"
                formtask.pointId=formtask.pointId//站点id
                for ((index, item) in listdview.withIndex()) {
                    //标液编号
                    formtaskfactorlist[index].StanLiquidCode = item.StanLiquidCode
                    //稀释方式
                    formtaskfactorlist[index].DilutionMethod = item.DilutionMethod
                    //标样值
                    formtaskfactorlist[index].StanValue = item.StanValue
                    //标样值单位
                    formtaskfactorlist[index].StanvalueUnit = item.StanvalueUnit
                    //标液用量
                    formtaskfactorlist[index].UseAmount = item.ettbyyl.text.toString()
                    //测试值
                    formtaskfactorlist[index].PollutantValue = item.ettctz.text.toString()
                    //测试加标值
                    formtaskfactorlist[index].PollutantValueAdd = item.ettkhz.text.toString()
                    //加标量
                    formtaskfactorlist[index].AddValue = ""  //先给空，后续改
                    formtaskfactorlist[index].Capacity = item.Capacity  //定容体积
                    formtaskfactorlist[index].CapacityUnit = item.CapacityUnit  //定容体积单位
                    formtaskfactorlist[index].surplusAmount = item.SurplusAmount//剩余量
                    formtaskfactorlist[index].RemainderUnit = item.RemainderUnit//剩余量单位
                    formtaskfactorlist[index].staLiquidGuid = item.StanLiquid//标液GUID
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
        //站点名称显示
        tv_station_name.text = formtask.pointName
        ll_time.text = formtask.endTime
        name.text = formtask.username
        //因子显示情况[动态生成]
        createview(formtaskfactorlist)
    }

    //根据rowGuid取数据库值
    private fun getdata4squlite() {
        formtask = getdataforsqlite(db, rowGuid)
        formtaskfactorlist = formtask.getFormTaskFactor(db!!)
    }

    //发送请求获取站点
    private fun requestpointinfo() {
        showdialog("loadshow")
        var ntrequest = Networkrequestmodel()
        ntrequest.setMethod(Networkrequestmodel.GETREQUEST)
                .settag("getPointInfo")
                .seturl(Networkrequestaddress.URL_PointInfo)
                .addparam("userGuid", SharedPreferencesFactory.getdata(this, "RowGuid"))
                .start(this)
    }

    //发送请求获取因子
    private fun requsetFactorInfo(pointId: String) {
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


    //存放当前界面的因子名称及其因子控件的
    var listdview: MutableList<BYFactorview> = java.util.ArrayList()
    var titlelist = arrayListOf(
            "测试因子",
            "因子单位",
//            "标液编号",
            "稀释方式",
//            "标样值",
            "定容体积",
            "定容体积单位",
            "标液用量",
            "测试值",
            "测试加标值")

    //因子显示情况[动态生成布局]
    data class BYFactorview(var PollutantName: String,//因子名称
                            var PollutantCode: String,//因子编号
                            var unit: String,//因子单位
                            var StanLiquidCode: String,//标液编号
                            var DilutionMethod: String,//稀释方式
                            var StanValue: String,//标样值
                            var StanvalueUnit: String,//标样值单位
                            var Capacity: String,//定容体积
                            var CapacityUnit: String,//定容体积单位
                            var ettbyyl: EditText,//标液用量
                            var ettctz: EditText,//测试值
                            var ettkhz: EditText,//测试加标值
                            var SurplusAmount: String,//剩余量
                            var RemainderUnit: String,//剩余量单位
                            var StanLiquid: String//标液GUID

    )

    //搭建因子界面
    @SuppressLint("NewApi")
    private fun <T : IFormTaskFactor> createview(viewlist: List<T>) {
        ll_title.removeAllViews()
        ll_create.removeAllViews()
        listdview = ArrayList()

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

        for ((index, item) in viewlist.withIndex()) {
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

            //搭建一个TextView  因子单位
            val tvb = TextView(act)
            tvb.text = item.unit
            tvb.setTextColor(Color.BLACK)
            tvb.textSize = 15f
            tvb.gravity = Gravity.CENTER
            //将tv装入aa中
            aa.addView(tvb, paramsonlyone)

            //搭建一个TextView  标液编号   隐藏
            val tvbyno = TextView(act)
            tvbyno.text = item.StanLiquidCode
            tvbyno.setTextColor(Color.BLACK)
            tvbyno.textSize = 15f
            tvbyno.gravity = Gravity.CENTER
            tvbyno.visibility = View.GONE
            //将tv装入aa中
            aa.addView(tvbyno, paramsonlyone)

            //搭建一个TextView  稀释方式
            val tvsffs = TextView(act)
            tvsffs.text = item.DilutionMethod
            tvsffs.setTextColor(Color.BLACK)
            tvsffs.textSize = 15f
            tvsffs.gravity = Gravity.CENTER
            //将tv装入aa中
            aa.addView(tvsffs, paramsonlyone)

            //搭建一个TextView  标样值   隐藏
            val tvbbyz = TextView(act)
            tvbbyz.text = item.StanValue
            tvbbyz.setTextColor(Color.BLACK)
            tvbbyz.textSize = 15f
            tvbbyz.gravity = Gravity.CENTER
            tvbbyz.visibility = View.GONE
            //将tv装入aa中
            aa.addView(tvbbyz, paramsonlyone)

            //扫描   隐藏
            val im = ImageView(act)
            im.setImageResource(R.drawable.title_photo)
            im.padding = 5
            im.onClick {
                toast("扫描")
            }
            im.visibility = View.GONE
            aa.addView(im, paramsonlyone)

            //定容体积
            val tvdrtj = TextView(act)
            tvdrtj.text = item.Capacity
            tvdrtj.setTextColor(Color.BLACK)
            tvdrtj.textSize = 15f
            tvdrtj.gravity = Gravity.CENTER
            //将tv装入aa中
            aa.addView(tvdrtj, paramsonlyone)

            //定容体积单位
            val tvdrtjdw = TextView(act)
            tvdrtjdw.text = item.CapacityUnit
            tvdrtjdw.setTextColor(Color.BLACK)
            tvdrtjdw.textSize = 15f
            tvdrtjdw.gravity = Gravity.CENTER
            //将tv装入aa中
            aa.addView(tvdrtjdw, paramsonlyone)

            //搭建一个Editview  标液用量
            val ettbyyl = EditText(act)
            ettbyyl.id = index + 200
            ettbyyl.setSingleLine(true)
            ettbyyl.gravity = Gravity.CENTER
            ettbyyl.background = act.baseContext.resources.getDrawable(R.drawable.shape_ed_bold)
            ettbyyl.setText(item.PollutantValue)
            ettbyyl.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            aa.addView(ettbyyl, paramsonlyone)

            //搭建一个Editview  测试值
            val ettsyz = EditText(act)
            ettsyz.id = index + 200
            ettsyz.setSingleLine(true)
            ettsyz.gravity = Gravity.CENTER
            ettsyz.background = act.baseContext.resources.getDrawable(R.drawable.shape_ed_bold)
            ettsyz.setText(item.PollutantValue)
            ettsyz.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            aa.addView(ettsyz, paramsonlyone)

            //搭建一个Editview  测试加标值
            val ettkhz = EditText(act)
            ettkhz.id = index + 200
            ettkhz.setSingleLine(true)
            ettkhz.gravity = Gravity.CENTER
            ettkhz.background = act.baseContext.resources.getDrawable(R.drawable.shape_ed_bold)
            ettkhz.setText(item.PollutantValue)
            ettkhz.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            aa.addView(ettkhz, paramsonlyone)


            listdview.add(BYFactorview(
                    item.PollutantName,//因子名称
                    item.PollutantCode,//因子code
                    item.unit,//因子单位
                    item.StanLiquidCode,//标液编号
                    tvsffs.text.toString(),//稀释方式
                    item.StanValue,//标样值
                    item.StanvalueUnit,//标样值单位
                    item.Capacity,//定容体积
                    item.CapacityUnit,//定容体积单位
                    ettbyyl,//标液用量
                    ettsyz,//测试值
                    ettkhz,//测试加标值
                    item.SurplusAmount,//剩余量
                    item.RemainderUnit,//剩余量单位
                    item.StaLiquidGuid//标液GUID

            ))
            val params = RelativeLayout.LayoutParams(rl_creatview_with, ViewGroup.LayoutParams.WRAP_CONTENT)
            aa.layoutParams = params

            //构造好的布局一一加入xml上去
            ll_create.addView(aa)
        }


    }
}
