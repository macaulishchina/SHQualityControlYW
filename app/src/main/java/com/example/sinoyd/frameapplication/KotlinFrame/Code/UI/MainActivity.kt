package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass.Task
import com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass.Version
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTask
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTaskFactor
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dataclass.gson
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dialog.DownloadDialog
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.Networkrequestmodel
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.SystemUtil
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.ToastUtil
import com.example.sinoyd.frameapplication.KotlinFrame.UI.BaseActivity
import com.example.sinoyd.frameapplication.KotlinFrame.Uitl.OpenFiles
import com.example.sinoyd.frameapplication.R
import com.example.sinoyd.frameapplication.R.id.*
import com.example.sinoyd.jiaxingywapplication.Myapplication
import com.sinoyd.Code.Until.Networkrequestaddress
import com.sinoyd.Code.Until.SharedPreferencesFactory
import com.sinoyd.Code.Until.showdialog
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Response
import org.jetbrains.anko.act
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.xutils.DbManager
import org.xutils.x


class MainActivity : BaseActivity() {
    var task: Task = Task()
    var version: Version = Version()
    //更新类
    var myapplication: Myapplication = Myapplication()
    var db: DbManager? = null
    var mHandler: Handler = object : Handler() {
        /**
         * handleMessage接收消息后进行相应的处理
         * @param msg
         */
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                1 -> {
                    //检查版本并进行更新版本
                    Comparisonversion(version.version[0].verCode, version.version[0].verName, version.version[0].verDesc, version.version[0].verPath)
                }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = x.getDb(myapplication.getDaoConfig())
        //监听事件
        setlisteners()
    }

    private fun setlisteners() {
        //版本升级
        uploadversion.onClick {
            getversion()
        }
        //更新
        upload.onClick {
            toast("更新")
        }
        //注销
        cancellation.onClick {
            //注销用户名密码
            SharedPreferencesFactory.cleardata(this, "LoginId")
            SharedPreferencesFactory.cleardata(this, "Password")
            SharedPreferencesFactory.cleardata(this, "RowGuid")
            startActivity<LoginActivity>()
            finish()
        }
        //签到
        singin.onClick {
            startActivity<Sign_in_Activity>()
        }
        //任务管理
        taskmanagement.onClick {
            if(checkUserState()) startActivity<Task_management_Activity>()
        }
        //站点定位
        sitelocation.onClick {
            startActivity<Site_location_Activity>()
        }
        //监测数据
        monitoringdata.onClick {
            startActivity<Monitoring_data_Activity>()
        }
        //下载任务
        downloadtask.onClick {
            downtask()
        }
        //待完成任务
        To_be_completedtask.onClick {
            //toast("待完成任务")
            //临时测试查看数据库数据
//            toast("数据库中表单因子有" + MyApplication.db.findAll(FormTaskFactor::class.java).size + "条数据")
        }

        //报警信息
        alarminformation.onClick {
            startActivity<Alarm_information_Activity>()
        }
        //统计分析
        statisticalanalysis.onClick {
            startActivity<Atatistical_analysis_Activity>()
        }
        //试剂标液配置
        reagentstandardliquidconfiguration.onClick {
            startActivity<Reagent_reducatant_manager_Activity>()
        }
        //新建临时任务
        newtemporarytasks.onClick {
            if(checkUserState()) startActivity<New_Temporary_Tasks_Activity>()
        }
    }

    private fun checkUserState():Boolean {
          if(SharedPreferencesFactory.getdata(this, "SignState") == "True"){
              return true
          }else{
              ToastUtil.showShort(this,"请您先签到")
              return false
          }
    }



    override fun requestSuccess(response: Response, TAG: String) {
        super.requestSuccess(response, TAG)
        when (TAG) {
            "getTask" -> {
                try {
                    if (response.isSuccessful) {
                        task = gson.fromJson(responsestr, Task::class.java)

                        if (task.result == "True") {
                            //保存任务到本地
                            saveformtask(task)
                            //保存任务因子到本地
                            saveformtaskfactor(task)
                            Looper.prepare()
                            toast("下载任务成功")
                            Looper.loop()
                        } else {
                            Looper.prepare()
                            toast(task.message)
                            Looper.loop()
                        }


                    } else {
                        Looper.prepare()
                        toast("下载任务失败")
                        Looper.loop()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            "getVersion" -> {
                if (response.isSuccessful) {
                    version = gson.fromJson(responsestr, Version::class.java)
                    if (version.result == "True") {

                        val message = Message.obtain()
                        message.what = 1
                        mHandler.sendMessage(message)

                    } else {
                        Looper.prepare()
                        toast(task.message)
                        Looper.loop()
                    }

                } else {
                    Looper.prepare()
                    toast("请求失败")
                    Looper.loop()
                }

            }
        }
    }

    /**
     * 保存任务到本地【FormTask】
     */
    private fun saveformtask(task: Task) {
        var tasklist: ArrayList<FormTask> = ArrayList()
        for (item in task.task) {
            var tt = FormTask()
            tt.taskCode = item.taskCode//任务编号
            tt.pointName = item.pointName//站点名称
            tt.taskName = item.taskName//任务名称
            tt.formName = item.formName//表单名称
            tt.endTime = item.endTime//完成时间
            tt.taskStatus = item.taskStatus//任务状态
            tt.taskStatusName = "未完成"//任务状态
            tt.taskType = item.taskType//任务类型
            tt.taskTypeName = item.taskTypeName//任务类型
            tt.operationCompany = item.operationCompany//运营公司
            tt.startDate = item.startDate//开始时间
            tt.endDate = item.endDate//计划完成时间
            tt.rowGuid = item.rowGuid//id
            tt.formCode = item.formCode
            tt.upload = false
            //任务和用户名绑定
            tt.userid = SharedPreferencesFactory.getdata(this, "RowGuid")//用户名guid
            tt.username = item.userName//用户名
            tt.pointId = item.pointId//站点id
            tasklist.add(tt)
        }
        try {
            db!!.save(tasklist)
            Log.i("scj", "任务保存成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("scj", "任务保存失败")
        }
    }

    /**
     * 任务因子存储【FormTaskFactor】saveformtask
     */
    private fun saveformtaskfactor(task: Task) {
        var taskfacotrlist: ArrayList<FormTaskFactor> = ArrayList()
        for (item in task.task) {
            for (it in item.taskData) {
                var ii = FormTaskFactor()
                ii.taskGuid = it.taskGuid//任务Guid
                ii.pollutantCode = it.pollutantCode//测试因子
                ii.pollutantName = it.pollutantName//测试因子名称
                ii.stanLiquidCode = it.stanLiquidCode//标液编号
                ii.Capacity = it.capacity//定容体积
                ii.CapacityUnit = it.capacityUnit//定容体积单位
                ii.stanvalueUnit = it.stanvalueUnit//标准值单位
                ii.dilutionMethod = it.dilutionMethod//稀释方式
                ii.unit = it.unit//因子单位
                ii.remainderUnit = it.remainderUnit//使用量单位
                ii.staLiquidGuid = it.staLiquidGuid//标液编号的GUID
                ii.stanValue = it.stanValue//标准值
                ii.stanvalueUnit = it.stanvalueUnit//标准值单位
                ii.surplusAmount = it.surplusAmount//剩余量
                ii.instrumentName = it.instrumentName//仪器
                ii.InstrumentGuid = it.instrumentGuid//仪器GUID
                ii.decimalDigit = it.decimalDigit//小数位数
                taskfacotrlist.add(ii)
            }
        }
        try {
            db!!.save(taskfacotrlist)
            Log.i("scj", "任务因子保存成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("scj", "任务因子保存失败")
        }
    }


    /**
     * 下载任务
     */
    fun downtask() {
        showdialog("loadshow")
        var ntrequest = Networkrequestmodel()
        ntrequest.setMethod(Networkrequestmodel.GETREQUEST)
                .settag("getTask")
                .seturl(Networkrequestaddress.URL_Task)
                .addparam("userGuid", SharedPreferencesFactory.getdata(this, "RowGuid"))
                .start(this)
    }

    /**
     * 获取版本进行更新
     * **/
    fun getversion() {
        showdialog("loadshow")
        var ntrequest = Networkrequestmodel()
        ntrequest.setMethod(Networkrequestmodel.GETREQUEST)
                .settag("getVersion")
                .seturl(Networkrequestaddress.URL_Version)
                .start(this)
    }


    private fun Comparisonversion(verCode: String, verName: String, verDesc: String, verPath: String) {
        //比较版本，是否进行更新
        try {
            if (SystemUtil.versionCode < verCode.toInt()) {


                val newVersionDialog = AlertDialog.Builder(act)
                newVersionDialog.setTitle("版本更新")
                newVersionDialog.setMessage("当前版本：" + SystemUtil.versionName + ",发现新版本：" + verName + "。\n更新内容：" + verDesc + "。\n是否更新版本？")
                newVersionDialog.setPositiveButton("立即更新") { paramDialogInterface, paramInt ->
                    paramDialogInterface.dismiss()
                    val downloadDialog = DownloadDialog(act, verPath)
                    downloadDialog.setDownloadListener(object : DownloadDialog.DownloadListener {
                        override fun startDownload() {

                        }

                        override fun endDownload(filePath: String) {
//                            OpenFiles.getApkFileIntent(filePath)
                        }

                    })
                    downloadDialog.show()
                }
                newVersionDialog.setNegativeButton("暂不更新") { paramDialogInterface, paramInt -> paramDialogInterface.dismiss() }
                newVersionDialog.show()


            } else {
                Looper.prepare()
                toast("目前版本已经是最新版本了")
                Looper.loop()

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Looper.prepare()
            toast("获取版本信息失败")
            Looper.loop()
        }
    }
}
