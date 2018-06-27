package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Control.getdataforsqlite
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Until.Calculation
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTask
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTaskFactor
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.IFormTaskFactor
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dialog.DateTimePickerControl
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.View.CommonSelectorString
import com.example.sinoyd.frameapplication.R
import com.example.sinoyd.jiaxingywapplication.Myapplication
import com.sinoyd.Code.Until.DisplayorhideSoftkeyboard
import com.sinoyd.Code.Until.SharedPreferencesFactory
import com.sinoyd.environmentsz.Kotlin.getToday
import kotlinx.android.synthetic.main.activity_accuracy_and_precision.*
import org.jetbrains.anko.act
import org.jetbrains.anko.enabled
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast
import org.xutils.DbManager
import org.xutils.x
import java.util.*

/**准确度和精密度**/
class New_Accuracy_and_precision_Activity : AppCompatActivity() {
    var rowGuid = ""
    var tasktypename = ""
    var myapplication: Myapplication = Myapplication()
    var db: DbManager? = null
    var formtask = FormTask()
    var formtaskfactorlist: MutableList<FormTaskFactor> = ArrayList()
    //整理后的数据
    var Instrumentlist: ArrayList<String> = ArrayList()
    var date = Date()
    //根据list创建因子
    var creattaskfactorlist: MutableList<FormTaskFactor> = ArrayList()

    var rl_creatview_with = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accuracy_and_precision)
        rl_creatview_with = SharedPreferencesFactory.getdata(act, "width").toInt() - 40
        Log.i("scj", "总宽度" + rl_creatview_with)
        db = x.getDb(myapplication.getDaoConfig())
        //强制隐藏软键盘
        DisplayorhideSoftkeyboard.hideSoftkeyboard(this)
        //先获取携带值   当前任务id
        rowGuid = intent.getStringExtra("rowGuid")
        tasktypename = intent.getStringExtra("TaskTypeName")
        if (rowGuid == "新建临时任务") {
            //TODO 新建任务
            tv_station_name.enabled = true
            iv_arrow.visibility = View.VISIBLE
            name.text = SharedPreferencesFactory.getdata(this, "DisplayName")
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
    private fun setview() {
        //站点名称显示
        tv_station_name.text = formtask.pointName
        ll_time.text = formtask.endTime
        name.text = formtask.username
    }

    private fun getdata4squlite() {
        formtask = getdataforsqlite(db, rowGuid)
        formtaskfactorlist = formtask.getFormTaskFactor(db!!)
        //整理数据
        arrangementdata(formtaskfactorlist)
    }

    //整理数据
    fun arrangementdata(formtaskfactorlist: MutableList<FormTaskFactor>) {
        //仪器的list
        Instrumentlist = ArrayList()
        for (item in formtaskfactorlist) {
            if (!Instrumentlist.contains(item.instrumentName)) {
                Instrumentlist.add(item.instrumentName)
            }
        }
        //拿到仪器的list
        if (Instrumentlist.isNotEmpty()) {
            instrument.text = Instrumentlist[0]
            //构建界面
            //获取对应仪器的因子
            creattaskfactorlist = ArrayList()
            for (item in formtaskfactorlist) {
                if (Instrumentlist[0] == item.instrumentName) {
                    creattaskfactorlist.add(item)
                }
            }
            //构建界面
            createview(creattaskfactorlist)

        } else {
            toast("无仪器数据")
        }


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
        //选择仪器
        ll_instrument.onClick {
            //界面内容首先要保存
            tv_save.performClick()
            var comm = CommonSelectorString(act, Instrumentlist, object : CommonSelectorString.OnSelectClickListener {
                override fun onCommonItemSelect(postions: Int) {
                    getdata4squlite()
                    instrument.text = Instrumentlist[postions]
                    //构建界面  获取对应仪器的因子
                    creattaskfactorlist = ArrayList()
                    for (item in formtaskfactorlist) {
                        if (Instrumentlist[postions] == item.instrumentName) {
                            creattaskfactorlist.add(item)
                        }
                    }
                    //构建界面
                    createview(creattaskfactorlist)
                }

            })
            comm.showPop()
        }
        //退出
        tv_exit.onClick {
            tv_save.performClick()
            finish()
        }
        //保存
        tv_save.onClick {
            if (rowGuid == "新建临时任务") {

            } else {
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
                formtask.pointId = formtask.pointId//站点id
                var Current_instrument_name = instrument.text
                //找到对应的TASKDATA进行赋值
                for (item in formtask.getFormTaskFactor(db)) {
                    for ((index, it) in listdview.withIndex()) {
                        //仪器名字等于当前名字，因子名称
                        if (item.instrumentName == Current_instrument_name && item.pollutantName == it.pollutantName) {
                            item.pollutantValue = it.ettt1.text.toString()
                            item.pollutantValue2 = it.ettt2.text.toString()
                            item.pollutantValue3 = it.ettt3.text.toString()
                            item.pollutantValue4 = it.ettt4.text.toString()
                            item.pollutantValue5 = it.ettt5.text.toString()
                            item.pollutantValue6 = it.ettt6.text.toString()
                            item.stanValue = it.etStandardValue.text.toString()//标准值
//                            Log.i("scj", "输入框1的值:" + item.pollutantValue)
//                            Log.i("scj", "输入框2的值:" + item.pollutantValue2)
//                            Log.i("scj", "输入框3的值:" + item.pollutantValue3)
//                            Log.i("scj", "输入框4的值:" + item.pollutantValue4)
//                            Log.i("scj", "输入框5的值:" + item.pollutantValue5)
//                            Log.i("scj", "输入框6的值:" + item.pollutantValue6)
                            var calculation = Calculation(
                                    arrayListOf(
                                            item.pollutantValue,
                                            item.pollutantValue2,
                                            item.pollutantValue3,
                                            item.pollutantValue4,
                                            item.pollutantValue5,
                                            item.pollutantValue6),
                                    item.decimalDigit,
                                    item.stanValue
                            )
                            item.averageValue = calculation.get平均值()
                            item.standardDeviation = calculation.get标准偏差()
                            item.accuracyValue = calculation.get准确度()
                            item.precisionValue = calculation.get精密度()

                            formtaskfactorlist.add(item)
                        }
                    }
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
                    toast("当前界面信息更新成功")
                } else if (a) {
                    toast("该表单更新失败")
                } else {
                    toast("该表单因子更新失败")
                }


            }


        }


    }

    //因子显示情况[动态生成布局]
    data class Factorview(var pollutantCode: String,//测试因子code
                          var pollutantName: String,//测试因子名称
                          var DilutionMethod: String,//稀释方式
                          var ettt1: EditText,//测试值1
                          var ettt2: EditText,//测试值2
                          var ettt3: EditText,//测试值3
                          var ettt4: EditText,//测试值4
                          var ettt5: EditText,//测试值5
                          var ettt6: EditText,//测试值6
                          var etStandardValue: EditText,//标准值
                          var DecimalDigit: String//小数位数

    )

    //存放当前界面的因子名称及其因子控件的
    var listdview: MutableList<Factorview> = java.util.ArrayList()

    var titlelist = arrayListOf("测试因子", "稀释方式", "标准值", "测试值1", "测试值2", "测试值3", "测试值4", "测试值5", "测试值6", "  ")

    //创建界面
    @SuppressLint("NewApi")
    private fun <T : IFormTaskFactor> createview(formtaskfactorlist: MutableList<T>) {

        if (tasktypename == "飞行检查") {
            titlelist = arrayListOf("测试因子", "稀释方式", "测试值1", "测试值2", "测试值3", "测试值4", "测试值5", "测试值6")
        } else {
            titlelist = arrayListOf("测试因子", "标准值(cu/mg)", "测试值1", "测试值2", "测试值3", "测试值4", "测试值5", "测试值6", "  ")
        }


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

        for ((index, item) in formtaskfactorlist.withIndex()) {
            //每一行LinearLayout  一个linearlayout
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

            //搭建一个TextView  稀释方式
            val tvb = TextView(act)
            tvb.text = item.DilutionMethod
            tvb.setTextColor(Color.BLACK)
            tvb.textSize = 15f
            tvb.gravity = Gravity.CENTER
            if (tasktypename == "飞行检查") {
                tvb.visibility = View.VISIBLE
            } else {
                tvb.visibility = View.GONE
            }
            //将tv装入aa中
            aa.addView(tvb, paramsonlyone)

            //搭建一个Editview   标准值
            val ettbjz = EditText(act)
            ettbjz.id = index + 100
            ettbjz.setSingleLine(true)
            ettbjz.gravity = Gravity.CENTER
            ettbjz.background = act.baseContext.resources.getDrawable(R.drawable.shape_ed_bold)
            ettbjz.setText(item.StanValue)
            ettbjz.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            if (tasktypename == "飞行检查") {
                ettbjz.visibility = View.GONE
            } else {
                ettbjz.visibility = View.VISIBLE
            }
            aa.addView(ettbjz, paramsonlyone)

            //搭建一个Editview   测试值1
            val ett1 = EditText(act)
            ett1.id = index + 100
            ett1.setSingleLine(true)
            ett1.gravity = Gravity.CENTER
            ett1.background = act.baseContext.resources.getDrawable(R.drawable.shape_ed_bold)
            ett1.setText(item.PollutantValue)
            ett1.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            aa.addView(ett1, paramsonlyone)
            //搭建一个Editview   测试值2
            val ett2 = EditText(act)
            ett2.id = index + 100
            ett2.setSingleLine(true)
            ett2.gravity = Gravity.CENTER
            ett2.background = act.baseContext.resources.getDrawable(R.drawable.shape_ed_bold)
            ett2.setText(item.PollutantValue2)
            ett2.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            aa.addView(ett2, paramsonlyone)
            //搭建一个Editview   测试值3
            val ett3 = EditText(act)
            ett3.id = index + 100
            ett3.setSingleLine(true)
            ett3.gravity = Gravity.CENTER
            ett3.background = act.baseContext.resources.getDrawable(R.drawable.shape_ed_bold)
            ett3.setText(item.PollutantValue3)
            ett3.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            aa.addView(ett3, paramsonlyone)
            //搭建一个Editview   测试值4
            val ett4 = EditText(act)
            ett4.id = index + 100
            ett4.setSingleLine(true)
            ett4.gravity = Gravity.CENTER
            ett4.background = act.baseContext.resources.getDrawable(R.drawable.shape_ed_bold)
            ett4.setText(item.PollutantValue4)
            ett4.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            aa.addView(ett4, paramsonlyone)
            //搭建一个Editview   测试值5
            val ett5 = EditText(act)
            ett5.id = index + 100
            ett5.setSingleLine(true)
            ett5.gravity = Gravity.CENTER
            ett5.background = act.baseContext.resources.getDrawable(R.drawable.shape_ed_bold)
            ett5.setText(item.PollutantValue5)
            ett5.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            aa.addView(ett5, paramsonlyone)
            //搭建一个Editview   测试值6
            val ett6 = EditText(act)
            ett6.id = index + 100
            ett6.setSingleLine(true)
            ett6.gravity = Gravity.CENTER
            ett6.background = act.baseContext.resources.getDrawable(R.drawable.shape_ed_bold)
            ett6.setText(item.PollutantValue6)
            ett6.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            aa.addView(ett6, paramsonlyone)


            if (tasktypename == "周期任务") {
                //搭建一个TextView  计算
                val tvjs = TextView(act)
                tvjs.text = "计算"
                tvjs.setTextColor(Color.WHITE)
                tvjs.background = act.baseContext.resources.getDrawable(R.drawable.frame_shape_colum_gv)
                tvjs.textSize = 15f
                tvjs.gravity = Gravity.CENTER
                //将tv装入aa中
                aa.addView(tvjs, paramsonlyone)

                //计算  精确值等等。
                tvjs.onClick {
                    var calculation = Calculation(
                            arrayListOf(
                                    ett1.text.toString(),
                                    ett2.text.toString(),
                                    ett3.text.toString(),
                                    ett4.text.toString(),
                                    ett5.text.toString(),
                                    ett6.text.toString()
                            ),
                            item.DecimalDigit,
                            ettbjz.text.toString()
                    )
                    val resultDialog = AlertDialog.Builder(act)
                    resultDialog.setTitle("计算结果：")
                    resultDialog.setMessage(
                            "平均值：" + calculation.get平均值() +
                                    "\n\n准确度：" + calculation.get准确度() +
                                    "\n\n标准偏差：" + calculation.get标准偏差() +
                                    "\n\n准确度:" + calculation.get准确度() +
                                    "\n\n精密度:" + calculation.get精密度())
                    resultDialog.setPositiveButton("确定") { paramDialogInterface, paramInt -> paramDialogInterface.dismiss() }
                    resultDialog.setNegativeButton("取消") { paramDialogInterface, paramInt -> paramDialogInterface.dismiss() }
                    resultDialog.show()
                }
            }
            listdview.add(Factorview(
                    item.PollutantCode,//测试因子code
                    item.PollutantName,//测试因子名称
                    tvb.text.toString(),//稀释方式
                    ett1,//测试值1
                    ett2,//测试值2
                    ett3,//测试值3
                    ett4,//测试值4
                    ett5,//测试值5
                    ett6,//测试值6
                    ettbjz,//标准值
                    item.DecimalDigit//小数位数
            ))

            val params = RelativeLayout.LayoutParams(rl_creatview_with, ViewGroup.LayoutParams.WRAP_CONTENT)
            aa.layoutParams = params
            //构造好的布局一一加入xml上去
            ll_create.addView(aa)
        }


    }

}