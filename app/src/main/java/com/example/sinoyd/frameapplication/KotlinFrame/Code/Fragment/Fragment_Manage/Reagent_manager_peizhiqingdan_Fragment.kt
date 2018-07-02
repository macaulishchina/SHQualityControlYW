package com.example.sinoyd.frameapplication.KotlinFrame.Code.Fragment.Fragment_Manage

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Until.Calculation
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Performance_assessment.BiaoZhunData
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Performance_assessment.JianChuXianData
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dataclass.Idnamevalue
import com.example.sinoyd.frameapplication.R
import com.example.sinoyd.frameapplication.R.id.*
import com.example.sinoyd.jiaxingywapplication.Myapplication
import com.sinoyd.Code.Until.DisplayorhideSoftkeyboard
import kotlinx.android.synthetic.main.fragment_peizhiqingdan_layout.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast
import org.xutils.DbManager
import org.xutils.x
import java.util.ArrayList


@SuppressLint("ValidFragment")
//标准曲线考核
class Reagent_manager_peizhiqingdan_Fragment(var rowGuid: String) : Fragment() {
    var list: ArrayList<Idnamevalue> = ArrayList()
    var instrumentName: String = ""
    var pollutantName: String = ""
    var myapplication: Myapplication = Myapplication()
    var db: DbManager? = null
    var biaozhunlsit: ArrayList<BiaoZhunData> = ArrayList()
    var currbiaozhun = BiaoZhunData()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val conview = inflater.inflate(R.layout.fragment_peizhiqingdan_layout, null)
        DisplayorhideSoftkeyboard.hideSoftkeyboard(activity)
        db = x.getDb(myapplication.getDaoConfig())
        return conview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //设置监听事件
        setlisteners()
        //获取全部检出限数据
        try{
        biaozhunlsit = db!!.selector(BiaoZhunData::class.java).where("RowGuid", "=", rowGuid).findAll() as ArrayList<BiaoZhunData>
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (biaozhunlsit.size > 0) {
            currbiaozhun = biaozhunlsit[0]
            tv_curremt_yiqi!!.text = currbiaozhun.instrumentName
            tv_curremt_yinzi!!.text = currbiaozhun.pollutantName
            refreshview(currbiaozhun)
        } else {
            activity!!.toast("没有对应仪器")
        }
    }

    private fun refreshview(currbiaozhun: BiaoZhunData) {
        var listbiaoz = currbiaozhun.standardValue.split("&")
        var listceding = currbiaozhun.actualValue.split("&")
        et_StandardValue1.setText("")
        et_StandardValue2.setText("")
        et_StandardValue3.setText("")
        et_StandardValue4.setText("")
        et_StandardValue5.setText("")

        try {
            et_StandardValue1.setText(listbiaoz[1])
            et_StandardValue2.setText(listbiaoz[2])
            et_StandardValue3.setText(listbiaoz[3])
            et_StandardValue4.setText(listbiaoz[4])
            et_StandardValue5.setText(listbiaoz[5])
        } catch (e: Exception) {
            e.printStackTrace()
        }

        et_ActualValue1.setText("")
        et_ActualValue2.setText("")
        et_ActualValue3.setText("")
        et_ActualValue4.setText("")
        et_ActualValue5.setText("")
        try {
            et_ActualValue1.setText(listceding[1])
            et_ActualValue2.setText(listceding[2])
            et_ActualValue3.setText(listceding[3])
            et_ActualValue4.setText(listceding[4])
            et_ActualValue5.setText(listceding[5])
        } catch (e: Exception) {
            e.printStackTrace()
        }

        et_CurvilinearEquation.setText(currbiaozhun.curvilinearEquation)
        et_CorrelationCoefficient.setText(currbiaozhun.correlationCoefficient)

    }

    private fun setlisteners() {

        //计算
        tv_jisuan.onClick {
            var call = Calculation(arrayListOf("1"), currbiaozhun.decimalDigit.toString(), "")
            et_CurvilinearEquation.setText(call.get相关系数r())
            et_CorrelationCoefficient.setText(call.get线性方程())
        }

        //保存
        tv_upload.onClick {
            currbiaozhun.actualValue = "&${et_StandardValue1.text}&${et_StandardValue2.text}&${et_StandardValue3.text}&${et_StandardValue4.text}&${et_StandardValue5.text}"
            currbiaozhun.standardValue = "&${et_ActualValue1.text}&${et_ActualValue2.text}&${et_ActualValue3.text}&${et_ActualValue4.text}&${et_ActualValue5.text}"
            currbiaozhun.curvilinearEquation = et_CurvilinearEquation.text.toString()
            currbiaozhun.correlationCoefficient = et_CorrelationCoefficient.text.toString()

            try {
                db!!.update(currbiaozhun)
                activity!!.toast("保存成功")
                Log.i("scj", "保存检标准曲线表成功")
            } catch (e: Exception) {
                activity!!.toast("保存失败")
                Log.i("scj", "保存检标准曲线表失败")
                e.printStackTrace()
            }

        }

    }


}