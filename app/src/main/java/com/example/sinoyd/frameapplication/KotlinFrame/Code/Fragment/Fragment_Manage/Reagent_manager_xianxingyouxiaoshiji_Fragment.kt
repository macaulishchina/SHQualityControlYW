package com.example.sinoyd.frameapplication.KotlinFrame.Code.Fragment.Fragment_Manage

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Until.Calculation
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Performance_assessment.JianChuXianData
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Performance_assessment.LingDianData
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dataclass.Idnamevalue
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dialog.DateTimePickerControl
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.View.CommonSelectorJianChuXianyq
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.View.CommonSelectorLingDian
import com.example.sinoyd.frameapplication.R
import com.example.sinoyd.jiaxingywapplication.Myapplication
import kotlinx.android.synthetic.main.fragment_xianxingyouxiaoshiji_layout.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast
import org.xutils.DbManager
import org.xutils.x
import java.util.ArrayList

@SuppressLint("ValidFragment")
//零点、量程漂移考核
class Reagent_manager_xianxingyouxiaoshiji_Fragment(var rowGuid: String) : Fragment() {
    var list: ArrayList<Idnamevalue> = ArrayList()

    var instrumentName: String = ""
    var pollutantName: String = ""
    var myapplication: Myapplication = Myapplication()
    var db: DbManager? = null
    var LingDianDatalsit: ArrayList<LingDianData> = ArrayList()
    var currLingDiandata = LingDianData()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val conview = inflater.inflate(R.layout.fragment_xianxingyouxiaoshiji_layout, null)
        db = x.getDb(myapplication.getDaoConfig())
        return conview
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //设置监听
        setlisteners()

        try {
            LingDianDatalsit = db!!.selector(LingDianData::class.java).where("RowGuid", "=", rowGuid).findAll() as ArrayList<LingDianData>
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (LingDianDatalsit.size > 0) {
            currLingDiandata = LingDianDatalsit[0]
            tv_curremt_yiqi.text = currLingDiandata.instrumentName
            tv_curremt_yinzi!!.text = currLingDiandata.pollutantName
            //显示界面
            refreshview(currLingDiandata)
        } else {
            activity.toast("没有对应仪器")
        }
    }

    private fun refreshview(currLingDiandata: LingDianData) {
        //响应时间
        et_ResponseTime.setText(currLingDiandata.responseTime.toString())
        //响应时间是否合格
        when (currLingDiandata.qualification) {
            "" -> rb_Qualification1.isChecked = true
            "合格" -> rb_Qualification1.isChecked = true
            "不合格" -> rb_Qualification2.isChecked = true
            else -> rb_Qualification1.isChecked = true
        }
        //标准液浓度
        et_StandardValue.setText(currLingDiandata.standardValue)
        //量程值
        et_RangeValues.setText(currLingDiandata.rangeValues)
        //测定值
        var list = currLingDiandata.actualValue.split("&")
        var listtime = currLingDiandata.dateTime.split("&")

        et_ceshizhi1!!.setText("")
        et_ceshizhi2!!.setText("")
        et_ceshizhi3!!.setText("")
        et_ceshizhi4!!.setText("")
        et_ceshizhi5!!.setText("")
        et_ceshizhi6!!.setText("")
        et_ceshizhi7!!.setText("")
        et_ceshizhi8!!.setText("")
        et_ceshizhi9!!.setText("")
        et_ceshizhi10!!.setText("")
        et_ceshizhi11!!.setText("")
        et_ceshizhi12!!.setText("")
        et_ceshizhi13!!.setText("")
        et_ceshizhi14!!.setText("")
        et_ceshizhi15!!.setText("")
        et_ceshizhi16!!.setText("")
        et_ceshizhi17!!.setText("")
        et_ceshizhi18!!.setText("")
        et_ceshizhi19!!.setText("")
        et_ceshizhi20!!.setText("")
        et_ceshizhi21!!.setText("")
        et_ceshizhi22!!.setText("")
        et_ceshizhi23!!.setText("")
        et_ceshizhi24!!.setText("")

        tv_chishishijian1!!.text = "选择时间"
        tv_chishishijian2!!.text = "选择时间"
        tv_chishishijian3!!.text = "选择时间"
        tv_chishishijian4!!.text = "选择时间"
        tv_chishishijian5!!.text = "选择时间"
        tv_chishishijian6!!.text = "选择时间"
        tv_chishishijian7!!.text = "选择时间"
        tv_chishishijian8!!.text = "选择时间"
        tv_chishishijian9!!.text = "选择时间"
        tv_chishishijian10!!.text = "选择时间"
        tv_chishishijian11!!.text = "选择时间"
        tv_chishishijian12!!.text = "选择时间"
        tv_chishishijian13!!.text = "选择时间"
        tv_chishishijian14!!.text = "选择时间"
        tv_chishishijian15!!.text = "选择时间"
        tv_chishishijian16!!.text = "选择时间"
        tv_chishishijian17!!.text = "选择时间"
        tv_chishishijian18!!.text = "选择时间"
        tv_chishishijian19!!.text = "选择时间"
        tv_chishishijian20!!.text = "选择时间"
        tv_chishishijian21!!.text = "选择时间"
        tv_chishishijian22!!.text = "选择时间"
        tv_chishishijian23!!.text = "选择时间"
        tv_chishishijian24!!.text = "选择时间"

        try {


            et_ceshizhi1!!.setText(list[1])
            et_ceshizhi2!!.setText(list[2])
            et_ceshizhi3!!.setText(list[3])
            et_ceshizhi4!!.setText(list[4])
            et_ceshizhi5!!.setText(list[5])
            et_ceshizhi6!!.setText(list[6])
            et_ceshizhi7!!.setText(list[7])
            et_ceshizhi8!!.setText(list[8])
            et_ceshizhi9!!.setText(list[9])
            et_ceshizhi10!!.setText(list[10])
            et_ceshizhi11!!.setText(list[11])
            et_ceshizhi12!!.setText(list[12])
            et_ceshizhi13!!.setText(list[13])
            et_ceshizhi14!!.setText(list[14])
            et_ceshizhi15!!.setText(list[15])
            et_ceshizhi16!!.setText(list[16])
            et_ceshizhi17!!.setText(list[17])
            et_ceshizhi18!!.setText(list[18])
            et_ceshizhi19!!.setText(list[19])
            et_ceshizhi20!!.setText(list[20])
            et_ceshizhi21!!.setText(list[21])
            et_ceshizhi22!!.setText(list[22])
            et_ceshizhi23!!.setText(list[23])
            et_ceshizhi24!!.setText(list[24])

        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            tv_chishishijian1!!.text = listtime[1]
            tv_chishishijian2!!.text = listtime[2]
            tv_chishishijian3!!.text = listtime[3]
            tv_chishishijian4!!.text = listtime[4]
            tv_chishishijian5!!.text = listtime[5]
            tv_chishishijian6!!.text = listtime[6]
            tv_chishishijian7!!.text = listtime[7]
            tv_chishishijian8!!.text = listtime[8]
            tv_chishishijian9!!.text = listtime[9]
            tv_chishishijian10!!.text = listtime[10]
            tv_chishishijian11!!.text = listtime[11]
            tv_chishishijian12!!.text = listtime[12]
            tv_chishishijian13!!.text = listtime[13]
            tv_chishishijian14!!.text = listtime[14]
            tv_chishishijian15!!.text = listtime[15]
            tv_chishishijian16!!.text = listtime[16]
            tv_chishishijian17!!.text = listtime[17]
            tv_chishishijian18!!.text = listtime[18]
            tv_chishishijian19!!.text = listtime[19]
            tv_chishishijian20!!.text = listtime[20]
            tv_chishishijian21!!.text = listtime[21]
            tv_chishishijian22!!.text = listtime[22]
            tv_chishishijian23!!.text = listtime[23]
            tv_chishishijian24!!.text = listtime[24]
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //初始值
        et_InitialValue.setText(currLingDiandata.initialValue)
        //最小值
        et_MinValue.setText(currLingDiandata.minValue)
        //最大值
        et_MaxValue.setText(currLingDiandata.maxValue)
        //零点漂移
        et_ZeroDrift.setText(currLingDiandata.zeroDrift)
        //响应时间
        et_DateTimeXY.setText(currLingDiandata.dateTimeXY)
        //标准浓度
        et_StandardValueL.setText(currLingDiandata.standardValueL)
        //量程值
        et_RangeValuesL.setText(currLingDiandata.rangeValuesL)
        //零点校准
        var listqian = currLingDiandata.actualValueL.split("&")
        var listtimeqian = currLingDiandata.dateTimeL.split("&")

        et_lingdianceshizhi1.setText("")
        et_lingdianceshizhi2.setText("")
        et_lingdianceshizhi3.setText("")
        et_lingdianceshizhi4.setText("")
        et_lingdianceshizhi5.setText("")
        et_lingdianceshizhi6.setText("")

        tv_lingdianchishishijian1.text = "选择时间"
        tv_lingdianchishishijian2.text = "选择时间"
        tv_lingdianchishishijian3.text = "选择时间"
        tv_lingdianchishishijian4.text = "选择时间"
        tv_lingdianchishishijian5.text = "选择时间"
        tv_lingdianchishishijian6.text = "选择时间"

        try {


            et_lingdianceshizhi1!!.setText(listqian[1])
            et_lingdianceshizhi2!!.setText(listqian[2])
            et_lingdianceshizhi3!!.setText(listqian[3])
            et_lingdianceshizhi4!!.setText(listqian[4])
            et_lingdianceshizhi5!!.setText(listqian[5])
            et_lingdianceshizhi6!!.setText(listqian[6])


        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            tv_lingdianchishishijian1!!.text = listtimeqian[1]
            tv_lingdianchishishijian2!!.text = listtimeqian[2]
            tv_lingdianchishishijian3!!.text = listtimeqian[3]
            tv_lingdianchishishijian4!!.text = listtimeqian[4]
            tv_lingdianchishishijian5!!.text = listtimeqian[5]
            tv_lingdianchishishijian6!!.text = listtimeqian[6]

        } catch (e: Exception) {
            e.printStackTrace()
        }

        //平均值
        et_AverageValue.setText(currLingDiandata.averageValue)
        //零点值
        et_ZeroValue.setText(currLingDiandata.zeroValue)
        //量程漂移
        et_SpanDrift.setText(currLingDiandata.spanDrift)

    }

    private fun setlisteners() {
        //仪器选择
        ll_yiqi.onClick {
            var comm = CommonSelectorLingDian(activity, LingDianDatalsit, object : CommonSelectorLingDian.OnSelectClickListener {
                override fun onCommonItemSelect(postions: Int) {
                    currLingDiandata = LingDianDatalsit[postions]
                    tv_curremt_yiqi!!.text = LingDianDatalsit[postions].instrumentName
                    tv_curremt_yinzi!!.text = LingDianDatalsit[postions].pollutantName
                    refreshview(LingDianDatalsit[postions])
                }
            })
            comm.showPop()
        }
        //保存
        tv_upload.onClick {
            //响应时间1
            currLingDiandata.responseTime = et_ResponseTime.text.toString()
            //是否合格
            if (rb_Qualification1.isChecked) {
                currLingDiandata.qualification = "合格"
            } else {
                currLingDiandata.qualification = "不合格"
            }
            //标准液浓度
            currLingDiandata.standardValue = et_StandardValue.text.toString()
            //量程值
            currLingDiandata.rangeValues = et_RangeValues.text.toString()
            //测定值 时间
            currLingDiandata.dateTime =
                    "&${tv_chishishijian1!!.text}" +
                    "&${tv_chishishijian2!!.text}" +
                    "&${tv_chishishijian3!!.text}" +
                    "&${tv_chishishijian4!!.text}" +
                    "&${tv_chishishijian5!!.text}" +
                    "&${tv_chishishijian6!!.text}" +
                    "&${tv_chishishijian7!!.text}" +
                    "&${tv_chishishijian8!!.text}" +
                    "&${tv_chishishijian9!!.text}" +
                    "&${tv_chishishijian10!!.text}" +
                    "&${tv_chishishijian11!!.text}" +
                    "&${tv_chishishijian12!!.text}" +
                    "&${tv_chishishijian13!!.text}" +
                    "&${tv_chishishijian14!!.text}" +
                    "&${tv_chishishijian15!!.text}" +
                    "&${tv_chishishijian16!!.text}" +
                    "&${tv_chishishijian17!!.text}" +
                    "&${tv_chishishijian18!!.text}" +
                    "&${tv_chishishijian19!!.text}" +
                    "&${tv_chishishijian20!!.text}" +
                    "&${tv_chishishijian21!!.text}" +
                    "&${tv_chishishijian22!!.text}" +
                    "&${tv_chishishijian23!!.text}" +
                    "&${tv_chishishijian24!!.text}"

            //测定值
            currLingDiandata.actualValue =
                    "&${et_ceshizhi1!!.text}" +
                    "&${et_ceshizhi2!!.text}" +
                    "&${et_ceshizhi3!!.text}" +
                    "&${et_ceshizhi4!!.text}" +
                    "&${et_ceshizhi5!!.text}" +
                    "&${et_ceshizhi6!!.text}" +
                    "&${et_ceshizhi7!!.text}" +
                    "&${et_ceshizhi8!!.text}" +
                    "&${et_ceshizhi9!!.text}" +
                    "&${et_ceshizhi10!!.text}" +
                    "&${et_ceshizhi11!!.text}" +
                    "&${et_ceshizhi12!!.text}" +
                    "&${et_ceshizhi13!!.text}" +
                    "&${et_ceshizhi14!!.text}" +
                    "&${et_ceshizhi15!!.text}" +
                    "&${et_ceshizhi16!!.text}" +
                    "&${et_ceshizhi17!!.text}" +
                    "&${et_ceshizhi18!!.text}" +
                    "&${et_ceshizhi19!!.text}" +
                    "&${et_ceshizhi20!!.text}" +
                    "&${et_ceshizhi21!!.text}" +
                    "&${et_ceshizhi22!!.text}" +
                    "&${et_ceshizhi23!!.text}" +
                    "&${et_ceshizhi24!!.text}"

            var call = Calculation(arrayListOf(
                    et_ceshizhi1.text.toString(),
                    et_ceshizhi2.text.toString(),
                    et_ceshizhi3.text.toString(),
                    et_ceshizhi4.text.toString(),
                    et_ceshizhi5.text.toString(),
                    et_ceshizhi6.text.toString(),
                    et_ceshizhi7.text.toString(),
                    et_ceshizhi8.text.toString(),
                    et_ceshizhi9.text.toString(),
                    et_ceshizhi10.text.toString(),
                    et_ceshizhi11.text.toString(),
                    et_ceshizhi12.text.toString(),
                    et_ceshizhi13.text.toString(),
                    et_ceshizhi14.text.toString(),
                    et_ceshizhi15.text.toString(),
                    et_ceshizhi16.text.toString(),
                    et_ceshizhi17.text.toString(),
                    et_ceshizhi18.text.toString(),
                    et_ceshizhi19.text.toString(),
                    et_ceshizhi20.text.toString(),
                    et_ceshizhi21.text.toString(),
                    et_ceshizhi22.text.toString(),
                    et_ceshizhi23.text.toString(),
                    et_ceshizhi24.text.toString()
            ), currLingDiandata.decimalDigit.toString(), "")

            //零点监测次数
            currLingDiandata.frequency = call.conunt.toString()

            //初始值
            currLingDiandata.initialValue = et_InitialValue.text.toString()
            //最小值
            currLingDiandata.minValue = et_MinValue.text.toString()
            //最大值
            currLingDiandata.maxValue = et_MaxValue.text.toString()
            //零点漂移
            currLingDiandata.zeroDrift = et_ZeroDrift.text.toString()
            //标准液体浓度
            currLingDiandata.standardValueL = et_StandardValueL.text.toString()
            //量程值
            currLingDiandata.rangeValuesL = et_RangeValuesL.text.toString()
            //响应时间
            currLingDiandata.dateTimeXY = et_DateTimeXY.text.toString()
            //测试值前后
            currLingDiandata.actualValueL =
                    "&${et_lingdianceshizhi1!!.text}" +
                    "&${et_lingdianceshizhi2!!.text}" +
                    "&${et_lingdianceshizhi3!!.text}" +
                    "&${et_lingdianceshizhi4!!.text}" +
                    "&${et_lingdianceshizhi5!!.text}" +
                    "&${et_lingdianceshizhi6!!.text}"
            currLingDiandata.dateTimeL =
                    "&${tv_lingdianchishishijian1!!.text}" +
                    "&${tv_lingdianchishishijian2!!.text}" +
                    "&${tv_lingdianchishishijian3!!.text}" +
                    "&${tv_lingdianchishishijian4!!.text}" +
                    "&${tv_lingdianchishishijian5!!.text}" +
                    "&${tv_lingdianchishishijian6!!.text}"
            var call2 = Calculation(arrayListOf(
                    et_lingdianceshizhi1.text.toString(),
                    et_lingdianceshizhi2.text.toString(),
                    et_lingdianceshizhi3.text.toString(),
                    et_lingdianceshizhi4.text.toString(),
                    et_lingdianceshizhi5.text.toString(),
                    et_lingdianceshizhi6.text.toString()
            ), currLingDiandata.decimalDigit.toString(), "")

            //量程监测次数
            currLingDiandata.frequencyL = call2.conunt.toString()
            //测定平均值
            currLingDiandata.averageValue = et_AverageValue.text.toString()
            //零点至
            currLingDiandata.zeroValue = et_ZeroValue.text.toString()
            //量程漂移
            currLingDiandata.spanDrift = et_SpanDrift.text.toString()



            try {
                db!!.update(currLingDiandata)
                activity.toast("保存成功")
                Log.i("scj", "保存零点表成功")
            } catch (e: Exception) {
                activity.toast("保存失败")
                Log.i("scj", "保存零点表失败")
                e.printStackTrace()
            }


        }
        //计算1
        tv_jisuan1.onClick {
            var call = Calculation(arrayListOf(
                    et_ceshizhi1.text.toString(),
                    et_ceshizhi2.text.toString(),
                    et_ceshizhi3.text.toString(),
                    et_ceshizhi4.text.toString(),
                    et_ceshizhi5.text.toString(),
                    et_ceshizhi6.text.toString(),
                    et_ceshizhi7.text.toString(),
                    et_ceshizhi8.text.toString(),
                    et_ceshizhi9.text.toString(),
                    et_ceshizhi10.text.toString(),
                    et_ceshizhi11.text.toString(),
                    et_ceshizhi12.text.toString(),
                    et_ceshizhi13.text.toString(),
                    et_ceshizhi14.text.toString(),
                    et_ceshizhi15.text.toString(),
                    et_ceshizhi16.text.toString(),
                    et_ceshizhi17.text.toString(),
                    et_ceshizhi18.text.toString(),
                    et_ceshizhi19.text.toString(),
                    et_ceshizhi20.text.toString(),
                    et_ceshizhi21.text.toString(),
                    et_ceshizhi22.text.toString(),
                    et_ceshizhi23.text.toString(),
                    et_ceshizhi24.text.toString()
            ), currLingDiandata.decimalDigit.toString(), "")
            et_InitialValue.setText(call.get初始值())
            et_MinValue.setText(call.get最小值())
            et_MaxValue.setText(call.get最大值())
            et_ZeroDrift.setText(call.get零点漂移(et_RangeValues.text.toString()))
        }
        //计算2
        tv_jisuan2.onClick {
            var call = Calculation(arrayListOf(
                    et_lingdianceshizhi1.text.toString(),
                    et_lingdianceshizhi2.text.toString(),
                    et_lingdianceshizhi3.text.toString(),
                    et_lingdianceshizhi4.text.toString(),
                    et_lingdianceshizhi5.text.toString(),
                    et_lingdianceshizhi6.text.toString()
            ), currLingDiandata.decimalDigit.toString(), "")
            et_AverageValue.setText(call.get平均值())
            et_ZeroValue.setText(et_InitialValue.text.toString())
            et_SpanDrift.setText(call.get量程漂移(et_StandardValueL.text.toString(), et_InitialValue.text.toString(), et_RangeValuesL.text.toString()))
        }
        //响应时间1
        et_ResponseTime.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                et_ResponseTime!!.text = it
            }
            datadialog.show()
        }
        //响应时间2
        et_DateTimeXY.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                et_DateTimeXY!!.text = it
            }
            datadialog.show()
        }
        //时间
        tv_chishishijian1.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian1!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian2.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian2!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian3.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian3!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian4.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian4!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian5.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian5!!.text = it
            }
            datadialog.show()
        }

        tv_chishishijian6.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian6!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian7.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian7!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian8.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian8!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian9.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian9!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian10.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian10!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian11.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian11!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian12.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian12!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian13.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian13!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian14.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian14!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian15.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian15!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian16.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian16!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian17.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian17!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian18.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian18!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian19.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian19!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian20.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian20!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian21.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian21!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian22.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian22!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian23.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian23!!.text = it
            }
            datadialog.show()
        }
        tv_chishishijian24.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_chishishijian24!!.text = it
            }
            datadialog.show()
        }

        tv_lingdianchishishijian1.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_lingdianchishishijian1!!.text = it
            }
            datadialog.show()
        }
        tv_lingdianchishishijian2.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_lingdianchishishijian2!!.text = it
            }
            datadialog.show()
        }
        tv_lingdianchishishijian3.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_lingdianchishishijian3!!.text = it
            }
            datadialog.show()
        }
        tv_lingdianchishishijian4.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_lingdianchishishijian4!!.text = it
            }
            datadialog.show()
        }
        tv_lingdianchishishijian5.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_lingdianchishishijian5!!.text = it
            }
            datadialog.show()
        }
        tv_lingdianchishishijian6.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                tv_lingdianchishishijian6!!.text = it
            }
            datadialog.show()
        }

    }


}