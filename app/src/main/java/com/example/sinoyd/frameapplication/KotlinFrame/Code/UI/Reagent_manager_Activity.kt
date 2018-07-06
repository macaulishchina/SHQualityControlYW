package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Control.getdataforsqlite
import com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass.PerforDown
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Fragment.Fragment_Manage.Reagent_manager_peizhiqingdan_Fragment
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Fragment.Fragment_Manage.Reagent_manager_shijipeizhi_Fragment
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Fragment.Fragment_Manage.Reagent_manager_xianxingyouxiaoshiji_Fragment
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTask
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Performance_assessment.BiaoZhunData
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Performance_assessment.JianChuXianData
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Performance_assessment.LingDianData
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dataclass.gson
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.Networkrequestmodel
import com.example.sinoyd.frameapplication.KotlinFrame.UI.BaseActivity
import com.example.sinoyd.frameapplication.R
import com.example.sinoyd.frameapplication.R.id.*
import com.example.sinoyd.jiaxingywapplication.Myapplication
import com.sinoyd.Code.Until.Networkrequestaddress
import com.sinoyd.Code.Until.showdialog
import kotlinx.android.synthetic.main.activity_reagent_manager_.*
import okhttp3.Response
import org.xutils.DbManager
import org.xutils.x

/**
 *
 * 年考核   性能考核
 *
 * **/

class Reagent_manager_Activity : BaseActivity() {

    var rowGuid = ""
    var pointid = ""
    var myapplication: Myapplication = Myapplication()
    var db: DbManager? = null
    var formtask = FormTask()

    var currmentjianchuxianData = JianChuXianData()
    var currmentbiaozhunData = BiaoZhunData()
    var currmentLingDianData = LingDianData()

    var jianchuxianFragment: Reagent_manager_shijipeizhi_Fragment? = null
    var biaozhunquxianFragment: Reagent_manager_peizhiqingdan_Fragment? = null
    var lindianFragment: Reagent_manager_xianxingyouxiaoshiji_Fragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reagent_manager_)
        db = x.getDb(myapplication.getDaoConfig())
        rb1.isChecked = true
        //设置默认的Fragment   默认第一个界面"0"


        setDefaultFragment()
        //先获取携带值
        rowGuid = intent.getStringExtra("rowGuid")
        pointid = intent.getStringExtra("pointId")
        jianchuxianFragment = Reagent_manager_shijipeizhi_Fragment(rowGuid)
        biaozhunquxianFragment = Reagent_manager_peizhiqingdan_Fragment(rowGuid)
        lindianFragment = Reagent_manager_xianxingyouxiaoshiji_Fragment(rowGuid)
        if (rowGuid == "新建临时任务") {


        } else {
            //增加监听事件
            setlisteners()
            //根据rowGuid取数据库值 获取表单
            getdata4squlite()
            //去检查数据库有没有对应表格的配置信息
            var list = db!!.selector(JianChuXianData::class.java).findAll()
            if (list == null) {
                //发请求  获取配置
                getPerforDown()
            } else if (db!!.selector(JianChuXianData::class.java)
                            .where("RowGuid", "=", rowGuid)
                            .findAll().size > 0) {

                listjianchu = ArrayList()
                listbiaozhun = ArrayList()
                listLingDian = ArrayList()

                try {
                    listjianchu = db!!.selector(JianChuXianData::class.java)
                            .where("RowGuid", "=", rowGuid)
                            .findAll() as ArrayList<JianChuXianData>
                    Log.i("scj", "本地获取检出限数据成功")

                } catch (e: Exception) {
                    Log.i("scj", "本地获取检出限数据失败")
                    e.printStackTrace()
                }
                try {
                    listbiaozhun = db!!.selector(BiaoZhunData::class.java)
                            .where("RowGuid", "=", rowGuid)
                            .findAll() as ArrayList<BiaoZhunData>
                    Log.i("scj", "本地获取检出限标准曲线成功")
                } catch (e: Exception) {
                    Log.i("scj", "本地获取检出限标准曲线失败")
                    e.printStackTrace()
                }
                try {

                    listLingDian = db!!.selector(LingDianData::class.java)
                            .where("RowGuid", "=", rowGuid)
                            .findAll() as ArrayList<LingDianData>
                    Log.i("scj", "本地获取检出零点成功")
                } catch (e: Exception) {
                    Log.i("scj", "本地获取检出零点失败")
                    e.printStackTrace()
                }
//                currmentjianchuxianData = listjianchu[0]
//                currmentbiaozhunData = listbiaozhun[0]
//                currmentLingDianData = listLingDian[0]
//
//                tv_curremt_yiqi.text = currmentjianchuxianData.instrumentName
//                tv_curremt_yinzi.text = currmentjianchuxianData.pollutantName
                //刷界面

            } else {
                //发请求  获取配置
                getPerforDown()
            }
        }
    }

    private fun getdata4squlite() {
        formtask = getdataforsqlite(db, rowGuid)
    }


    //设置默认的Fragment   默认第一个界面"0"
    private fun setDefaultFragment() {
        vp.currentItem = 0
        vp.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {


                return when (position) {
                    0 -> jianchuxianFragment!!//检出限考核
                    1 -> biaozhunquxianFragment!!//标砖曲线考核
                    2 -> lindianFragment!!//零点、量程漂移考核
                    else -> jianchuxianFragment!!
                }
            }

            override fun getCount(): Int {
                return 3
            }

        }
    }

    //设置监听
    private fun setlisteners() {
        //rg监听事件
        rg.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.rb1 -> {
                    vp.currentItem = 0
                }
                R.id.rb2 -> {
                    vp.currentItem = 1
                }
                R.id.rb3 -> {
                    vp.currentItem = 2
                }
            }
        }
        //viewpager
        vp.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        rb1.isChecked = true
                        rb2.isChecked = false
                        rb3.isChecked = false
                    }
                    1 -> {
                        rb1.isChecked = false
                        rb2.isChecked = true
                        rb3.isChecked = false
                    }
                    2 -> {
                        rb1.isChecked = false
                        rb2.isChecked = false
                        rb3.isChecked = true
                    }
                }
            }

        })


    }

    //发请求获取数据
    fun getPerforDown() {
        showdialog("loadshow")
        var ntrequest = Networkrequestmodel()
        ntrequest.setMethod(Networkrequestmodel.GETREQUEST)
                .settag("getPerforDown")
                .seturl(Networkrequestaddress.URL_PerforDown)
                .addparam("pointid", pointid)
                .addparam("RowGuid", rowGuid)
                .start(this)
    }

    var perforDown = com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass.PerforDown()
    override fun requestSuccess(response: Response, TAG: String) {
        super.requestSuccess(response, TAG)
        when (TAG) {
            "getPerforDown" -> {
                perforDown = gson.fromJson(responsestr, PerforDown::class.java)

                //存数据去本地  所有【状况】 存入
                dbsave(perforDown)

//                currmentjianchuxianData = listjianchu[0]
//                currmentbiaozhunData = listbiaozhun[0]
//                currmentLingDianData = listLingDian[0]
//
//                tv_curremt_yiqi.text = currmentjianchuxianData.instrumentName
//                tv_curremt_yinzi.text = currmentjianchuxianData.pollutantName

                //刷界面

            }
        }
    }

    var listjianchu: ArrayList<JianChuXianData> = ArrayList()
    var listbiaozhun: ArrayList<BiaoZhunData> = ArrayList()
    var listLingDian: ArrayList<LingDianData> = ArrayList()

    fun dbsave(perforDown: PerforDown) {
        //bug避免 之后调试
        if(perforDown == null || perforDown.jianChuXianData == null) return
        listjianchu = ArrayList()
        listbiaozhun = ArrayList()
        listLingDian = ArrayList()
        //检出限出
        for (item in perforDown.jianChuXianData) {
            var jc = JianChuXianData()
            jc.actualValue = item.actualValue
            jc.rowGuid = item.rowGuid
            jc.pollutantCode = item.pollutantCode
            jc.pollutantName = item.pollutantName
            jc.unit = item.unit
            jc.decimalDigit = item.decimalDigit
            jc.instrumentGuid = item.instrumentGuid
            jc.instrumentName = item.instrumentName
            jc.standardValue = item.standardValue
            jc.frequency = item.frequency
            jc.actualValue = item.actualValue
            jc.dateTime = item.dateTime
            jc.averageValue = item.averageValue
            jc.standardDeviation = item.standardDeviation
            jc.detectionLimit = item.detectionLimit
            listjianchu.add(jc)
        }

        //标准
        for (item in perforDown.biaoZhunData) {
            var bz = BiaoZhunData()
            bz.actualValue = item.actualValue
            bz.rowGuid = item.rowGuid
            bz.pollutantCode = item.pollutantCode
            bz.pollutantName = item.pollutantName
            bz.unit = item.unit
            bz.decimalDigit = item.decimalDigit
            bz.instrumentGuid = item.instrumentGuid
            bz.instrumentName = item.instrumentName
            bz.dateTime = item.dateTime
            bz.frequency = item.frequency
            bz.actualValue = item.actualValue
            bz.standardValue = item.standardValue
            bz.curvilinearEquation = item.curvilinearEquation
            bz.curvilinearEquation = item.curvilinearEquation
            listbiaozhun.add(bz)
        }

        //零点
        for (item in perforDown.lingDianData) {
            var ld = LingDianData()
            ld.actualValue = item.actualValue
            ld.rowGuid = item.rowGuid
            ld.pollutantCode = item.pollutantCode
            ld.pollutantName = item.pollutantName
            ld.unit = item.unit
            ld.decimalDigit = item.decimalDigit
            ld.instrumentGuid = item.instrumentGuid
            ld.instrumentName = item.instrumentName
            ld.responseTime = item.responseTime
            ld.qualification = item.qualification
            ld.standardValue = item.standardValue
            ld.rangeValues = item.rangeValues
            ld.frequency = item.frequency
            ld.actualValue = item.actualValue
            ld.dateTime = item.dateTime
            ld.initialValue = item.initialValue
            ld.maxValue = item.maxValue
            ld.minValue = item.minValue
            ld.zeroDrift = item.zeroDrift
            ld.standardValueL = item.standardValueL
            ld.rangeValuesL = item.rangeValuesL
            ld.frequencyL = item.frequencyL
            ld.actualValueL = item.actualValueL
            ld.averageValue = item.averageValue
            ld.zeroValue = item.zeroValue
            ld.spanDrift = item.spanDrift
            ld.dateTimeL = item.dateTimeL
            ld.dateTimeXY = item.dateTimeXY
            listLingDian.add(ld)
        }


        //保存
        try {
            db!!.save(listjianchu)
            Log.i("scj", "保存检出限出表格成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("scj", "保存检出限出表格失败")
        }
        //保存
        try {
            db!!.save(listbiaozhun)
            Log.i("scj", "保存标准表格成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("scj", "保存标准表格失败")
        }
        //保存
        try {
            db!!.save(listLingDian)
            Log.i("scj", "保存零点表格成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("scj", "保存零点表格失败")
        }

    }


}
