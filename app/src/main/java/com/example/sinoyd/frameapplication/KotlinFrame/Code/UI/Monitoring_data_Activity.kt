package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.sinoyd.frameapplication.R
import kotlinx.android.synthetic.main.activity_monitoring_data_.*
import org.jetbrains.anko.onClick

/**监测数据**/
class Monitoring_data_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monitoring_data_)
        //设置监听
        setlisteners()
    }

    private fun setlisteners() {
        //返回主页
        iv_home.onClick{
            finish()
        }
    }
}
