package com.example.sinoyd.frameapplication.KotlinFrame.Code.Fragment.Fragment_Manage

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Until.Calculation
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Performance_assessment.JianChuXianData
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dataclass.Idnamevalue
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dialog.DateTimePickerControl
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.View.CommonSelectorJianChuXianyq
import com.example.sinoyd.frameapplication.R
import com.example.sinoyd.jiaxingywapplication.Myapplication
import com.sinoyd.Code.Until.DisplayorhideSoftkeyboard
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast
import org.xutils.DbManager
import org.xutils.x
import java.util.*

@SuppressLint("ValidFragment")
//检出限考核
class Reagent_manager_shijipeizhi_Fragment(var rowGuid: String) : Fragment() {
    var instrumentName: String = ""
    var pollutantName: String = ""


    var list: ArrayList<Idnamevalue> = ArrayList()
    var currjianchuxian = JianChuXianData()
    var myapplication: Myapplication = Myapplication()
    var db: DbManager? = null
    var eet_rongliang: EditText? = null
    var eet_ceshizhi1: EditText? = null
    var eet_ceshizhi2: EditText? = null
    var eet_ceshizhi3: EditText? = null
    var eet_ceshizhi4: EditText? = null
    var eet_ceshizhi5: EditText? = null
    var eet_ceshizhi6: EditText? = null
    var eet_pingjunzhi: EditText? = null
    var eet_biaozhunpiancha: EditText? = null
    var eet_zuidijianchuxian: EditText? = null
    var ttv_upload: TextView? = null
    var ttv_jisuan: TextView? = null
    var ttv_chishishijian1: TextView? = null
    var ttv_chishishijian2: TextView? = null
    var ttv_chishishijian3: TextView? = null
    var ttv_chishishijian4: TextView? = null
    var ttv_chishishijian5: TextView? = null
    var ttv_chishishijian6: TextView? = null
    var lll_yiqi: LinearLayout? = null
    var conview: View? = null
    var ianchuxianlsit: ArrayList<JianChuXianData> = ArrayList()
    var ttv_curremt_yiqi: TextView? = null
    var ttv_curremt_yinzi: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val conview = inflater.inflate(R.layout.fragment_shijipeizhi_layout, null)
        DisplayorhideSoftkeyboard.hideSoftkeyboard(activity)
        db = x.getDb(myapplication.getDaoConfig())
        this.conview = conview
        return conview
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findview()
        setlisteners()
        //获取全部检出限数据
        try {
            ianchuxianlsit = db!!.selector(JianChuXianData::class.java).where("RowGuid", "=", rowGuid).findAll() as ArrayList<JianChuXianData>
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (ianchuxianlsit.size > 0) {
            currjianchuxian = ianchuxianlsit[0]
            ttv_curremt_yiqi!!.text = currjianchuxian.instrumentName
            ttv_curremt_yinzi!!.text = currjianchuxian.pollutantName
            refreshview(currjianchuxian)
        } else {
            activity.toast("没有对应仪器")
        }
    }

    fun findview() {
        eet_rongliang = conview!!.findViewById<EditText>(R.id.et_rongliang)
        eet_ceshizhi1 = conview!!.findViewById<EditText>(R.id.et_ceshizhi1)
        eet_ceshizhi2 = conview!!.findViewById<EditText>(R.id.et_ceshizhi2)
        eet_ceshizhi3 = conview!!.findViewById<EditText>(R.id.et_ceshizhi3)
        eet_ceshizhi4 = conview!!.findViewById<EditText>(R.id.et_ceshizhi4)
        eet_ceshizhi5 = conview!!.findViewById<EditText>(R.id.et_ceshizhi5)
        eet_ceshizhi6 = conview!!.findViewById<EditText>(R.id.et_ceshizhi6)
        eet_pingjunzhi = conview!!.findViewById<EditText>(R.id.et_pingjunzhi)
        eet_biaozhunpiancha = conview!!.findViewById<EditText>(R.id.et_biaozhunpiancha)
        eet_zuidijianchuxian = conview!!.findViewById<EditText>(R.id.et_zuidijianchuxian)
        ttv_upload = conview!!.findViewById<TextView>(R.id.tv_upload)
        ttv_jisuan = conview!!.findViewById<TextView>(R.id.tv_jisuan)
        ttv_chishishijian1 = conview!!.findViewById<TextView>(R.id.tv_chishishijian1)
        ttv_chishishijian2 = conview!!.findViewById<TextView>(R.id.tv_chishishijian2)
        ttv_chishishijian3 = conview!!.findViewById<TextView>(R.id.tv_chishishijian3)
        ttv_chishishijian4 = conview!!.findViewById<TextView>(R.id.tv_chishishijian4)
        ttv_chishishijian5 = conview!!.findViewById<TextView>(R.id.tv_chishishijian5)
        ttv_chishishijian6 = conview!!.findViewById<TextView>(R.id.tv_chishishijian6)
        lll_yiqi = conview!!.findViewById<LinearLayout>(R.id.ll_yiqi)
        ttv_curremt_yiqi = conview!!.findViewById<TextView>(R.id.tv_curremt_yiqi)
        ttv_curremt_yinzi = conview!!.findViewById<TextView>(R.id.tv_curremt_yinzi)
    }

    fun refreshview(currjianchuxian: JianChuXianData) {

        eet_rongliang!!.setText(currjianchuxian.standardValue.toString())
        var list = currjianchuxian.actualValue.split("&")
        var listtime = currjianchuxian.dateTime.split("&")
        eet_ceshizhi1!!.setText("")
        eet_ceshizhi2!!.setText("")
        eet_ceshizhi3!!.setText("")
        eet_ceshizhi4!!.setText("")
        eet_ceshizhi5!!.setText("")
        eet_ceshizhi6!!.setText("")

        ttv_chishishijian1!!.text = "选择时间"
        ttv_chishishijian2!!.text = "选择时间"
        ttv_chishishijian3!!.text = "选择时间"
        ttv_chishishijian4!!.text = "选择时间"
        ttv_chishishijian5!!.text = "选择时间"
        ttv_chishishijian6!!.text = "选择时间"

        try {
            eet_ceshizhi1!!.setText(list[1])
            eet_ceshizhi2!!.setText(list[2])
            eet_ceshizhi3!!.setText(list[3])
            eet_ceshizhi4!!.setText(list[4])
            eet_ceshizhi5!!.setText(list[5])
            eet_ceshizhi6!!.setText(list[6])

        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            ttv_chishishijian1!!.text = listtime[1]
            ttv_chishishijian2!!.text = listtime[2]
            ttv_chishishijian3!!.text = listtime[3]
            ttv_chishishijian4!!.text = listtime[4]
            ttv_chishishijian5!!.text = listtime[5]
            ttv_chishishijian6!!.text = listtime[6]

        } catch (e: Exception) {
            e.printStackTrace()
        }
        eet_pingjunzhi!!.setText(currjianchuxian.averageValue)
        eet_biaozhunpiancha!!.setText(currjianchuxian.standardDeviation)
        eet_zuidijianchuxian!!.setText(currjianchuxian.detectionLimit)
    }

    private fun setlisteners() {
        //仪器选项
        lll_yiqi!!.onClick {
            var comm = CommonSelectorJianChuXianyq(activity, ianchuxianlsit, object : CommonSelectorJianChuXianyq.OnSelectClickListener {
                override fun onCommonItemSelect(postions: Int) {
                    currjianchuxian = ianchuxianlsit[postions]
                    ttv_curremt_yiqi!!.text = ianchuxianlsit[postions].instrumentName
                    ttv_curremt_yinzi!!.text = ianchuxianlsit[postions].pollutantName
                    refreshview(ianchuxianlsit[postions])
                }
            })
            comm.showPop()
        }

        //保存
        ttv_upload!!.onClick {

            currjianchuxian.standardValue = eet_rongliang!!.text.toString()
            currjianchuxian.actualValue = "&${eet_ceshizhi1!!.text}&${eet_ceshizhi2!!.text}&${eet_ceshizhi3!!.text}&${eet_ceshizhi4!!.text}&${eet_ceshizhi5!!.text}&${eet_ceshizhi6!!.text}"
            currjianchuxian.dateTime = "&${ttv_chishishijian1!!.text}&${ttv_chishishijian2!!.text}&${ttv_chishishijian3!!.text}&${ttv_chishishijian4!!.text}&${ttv_chishishijian5!!.text}&${ttv_chishishijian6!!.text}"
            currjianchuxian.averageValue = eet_pingjunzhi!!.text.toString()
            currjianchuxian.standardDeviation = eet_biaozhunpiancha!!.text.toString()
            currjianchuxian.detectionLimit = eet_zuidijianchuxian!!.text.toString()
            var Cal = Calculation(arrayListOf(eet_ceshizhi1!!.text.toString(),
                    eet_ceshizhi2!!.text.toString(),
                    eet_ceshizhi3!!.text.toString(),
                    eet_ceshizhi4!!.text.toString(),
                    eet_ceshizhi5!!.text.toString(),
                    eet_ceshizhi6!!.text.toString()), currjianchuxian.decimalDigit.toString(), "")
            currjianchuxian.frequency = Cal.conunt.toString()
            try {
                db!!.update(currjianchuxian)
                activity.toast("保存成功")
                Log.i("scj", "保存检出限表成功")
            } catch (e: Exception) {
                activity.toast("保存失败")
                Log.i("scj", "保存检出限表失败")
                e.printStackTrace()
            }
        }
        //计算
        ttv_jisuan!!.onClick {
            var Cal = Calculation(arrayListOf(eet_ceshizhi1!!.text.toString(),
                    eet_ceshizhi2!!.text.toString(),
                    eet_ceshizhi3!!.text.toString(),

                    eet_ceshizhi4!!.text.toString(),
                    eet_ceshizhi5!!.text.toString(),
                    eet_ceshizhi6!!.text.toString()), currjianchuxian.decimalDigit.toString(), "")
            eet_pingjunzhi!!.setText(Cal.get平均值())
            eet_biaozhunpiancha!!.setText(Cal.get标准偏差())
            eet_zuidijianchuxian!!.setText(Cal.get最低检出限())
        }
        //时间
        ttv_chishishijian1!!.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                ttv_chishishijian1!!.text = it
            }
            datadialog.show()
        }
        ttv_chishishijian2!!.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                ttv_chishishijian2!!.text = it
            }
            datadialog.show()
        }
        ttv_chishishijian3!!.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                ttv_chishishijian3!!.text = it
            }
            datadialog.show()
        }
        ttv_chishishijian4!!.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                ttv_chishishijian4!!.text = it
            }
            datadialog.show()
        }
        ttv_chishishijian5!!.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                ttv_chishishijian5!!.text = it
            }
            datadialog.show()
        }
        ttv_chishishijian6!!.onClick {
            var datadialog = DateTimePickerControl(activity, AlertDialog.THEME_HOLO_LIGHT, "datetime", "#0f82ff")
            datadialog.SetDateSelectListener {
                ttv_chishishijian6!!.text = it
            }
            datadialog.show()
        }
    }

}