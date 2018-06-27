package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.sinoyd.frameapplication.KotlinFrame.UI.BaseActivity
import com.example.sinoyd.frameapplication.R
import kotlinx.android.synthetic.main.activity_alarm_information_.*
import org.jetbrains.anko.onClick

/**报警信息**/
class Alarm_information_Activity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_information_)
        //设置监听
        setlisteners()
    }

    private fun setlisteners() {
        //返回主页
        iv_home.onClick {
            finish()
        }
    }
}
